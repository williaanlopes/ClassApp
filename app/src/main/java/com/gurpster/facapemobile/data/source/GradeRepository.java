package com.gurpster.facapemobile.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class GradeRepository implements GradeDataSource {

    private final GradeDataSource gradeRemoteDataSource;
    private final GradeDataSource gradeLocalDataSource;
    private final PreferencesHelper preferences;
    private final Gson gson;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
//    Map<String, Grade> mCachedGrades;
    Map<String, Grades> mCachedGrades;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

//    private List<Grades> localGrades = new ArrayList<>();
//    private List<Grades> remoteGrades = new ArrayList<>();

    @Inject
    GradeRepository(@Remote GradeDataSource gradeRemoteDataSource,
                    @Local GradeDataSource gradeLocalDataSource, PreferencesHelper preferences, Gson gson) {
        this.gradeRemoteDataSource = gradeRemoteDataSource;
        this.gradeLocalDataSource = gradeLocalDataSource;
        this.preferences = preferences;
        this.gson = gson;
    }

    /**
     * Gets grades from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadGradesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getGrades(@NonNull final LoadGradesCallback callback) {

        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedGrades != null && !mCacheIsDirty) {
            List<Grades> grades = new ArrayList<>(mCachedGrades.values());
            callback.onGradeLoaded(grades);
            Log.d("GradeRepository", "Load from cache size: " + grades.size());
            for (Grades grade: grades) {
                Log.d("Repository cache loaded", "grade: id " + grade.getId() + " name " + grade.getSubject());
            }
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getGradesFromRemoteDataSource(callback);
//            Log.d("GradeRepository", "Load from remote");
        } else {
            // Query the local storage if available.
            gradeLocalDataSource.getGrades(new LoadGradesCallback() {
                @Override
                public void onGradeLoaded(List<Grades> grades) {
                    refreshCache(grades);
                    String eTag = md5(gson.toJson(grades));
                    preferences.setPrefGradesETag(eTag);
                    callback.onGradeLoaded(new ArrayList<>(mCachedGrades.values()));
                    Log.d("GradeRepository", "Load from local size: " + grades.size());
                }

                @Override
                public void onDataNotAvailable() {
                    getGradesFromRemoteDataSource(callback);
                }
            });
        }
    }

    /**
     * Gets old grades remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadGradesCallback#onDataNotAvailable()} is fired if data source fail to
     * get the data.
     */
    @Override
    public void getOldGrades(int year, int semester, @NonNull final LoadGradesCallback callback) {

        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
//        if (mCachedGrades != null && !mCacheIsDirty) {
//            List<Grades> grades = new ArrayList<>(mCachedGrades.values());
//            callback.onGradeLoaded(grades);
//            Log.d("GradeRepository", "Load old grades from cache size: " + grades.size());
//            return;
//        } else {
            gradeRemoteDataSource.getOldGrades(year, semester, new LoadGradesCallback() {
                @Override
                public void onGradeLoaded(List<Grades> grades) {
                    refreshCache(grades);
                    callback.onGradeLoaded(new ArrayList<>(mCachedGrades.values()));
                    Log.d("GradeRepository", "Load old grades from remote size: " + grades.size());
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
//        }
    }

    @Override
    public void saveGrade(@NonNull Grades grade) {
        checkNotNull(grade);
        gradeLocalDataSource.saveGrade(grade);

//      Do in memory cache update to keep the app UI up to date
        if (mCachedGrades == null) {
            mCachedGrades = new LinkedHashMap<>();
        }
        mCachedGrades.put(grade.getSubject(), grade);
//        mCachedGrades.put(grade.getId()+"", grade);
    }

    @Override
    public void saveGrades(@NonNull Grades grade) {
        // TODO
    }

    /**
     * Gets grades from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     * <p>
     * Note: {@link GetGradeCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getGrade(@NonNull final Long gradeId, @NonNull final GetGradeCallback callback) {
        checkNotNull(gradeId);
        checkNotNull(callback);

        Grades cachedGrade = getGradeWithId(gradeId);

        // Respond immediately with cache if available
        if (cachedGrade != null) {
            callback.onGradeLoaded(cachedGrade);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        gradeLocalDataSource.getGrade(gradeId, new GetGradeCallback() {
            @Override
            public void onGradeLoaded(Grades grade) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedGrades == null) {
                    mCachedGrades = new LinkedHashMap<>();
                }
                mCachedGrades.put(""+grade.getId(), grade);
                callback.onGradeLoaded(grade);
            }

            @Override
            public void onDataNotAvailable() {
                gradeRemoteDataSource.getGrade(gradeId, new GetGradeCallback() {
                    @Override
                    public void onGradeLoaded(Grades grade) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedGrades == null) {
                            mCachedGrades = new LinkedHashMap<>();
                        }
                        mCachedGrades.put(""+grade.getId(), grade);
                        callback.onGradeLoaded(grade);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void refreshGrades() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllGrades() {
        gradeLocalDataSource.deleteAllGrades();

        if (mCachedGrades == null) {
            mCachedGrades = new LinkedHashMap<>();
        }
        mCachedGrades.clear();
    }

    @Override
    public void deleteGrade(@NonNull Long gradeId) {
        gradeRemoteDataSource.deleteGrade(checkNotNull(gradeId));
        gradeLocalDataSource.deleteGrade(checkNotNull(gradeId));
        mCachedGrades.remove(gradeId);
    }

    private void getGradesFromRemoteDataSource(@NonNull final LoadGradesCallback callback) {

        gradeRemoteDataSource.getGrades(new LoadGradesCallback() {
            @Override
            public void onGradeLoaded(List<Grades> grades) {
//                Log.d("GradeRepository", "Load from remote size: " + grades.size());
                if(grades.size() > 1) {
                    refreshCache(grades);
                    refreshLocalDataSource(grades);
                } else {
                    getGradesFromLocalDataSource(callback);
                }
//                for (Grades grade: grades) {
//                    Log.d("Repositor remote loaded", "grade: " + grade.getId() + " name " + grade.getSubject());
//                }
                callback.onGradeLoaded(new ArrayList<>(mCachedGrades.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Grades> grades) {
        if (mCachedGrades == null) {
            mCachedGrades = new LinkedHashMap<>();
        }
        mCachedGrades.clear();
//        int index = 0;
        for (Grades grade : grades) {
            mCachedGrades.put(grade.getSubject(), grade);
//            mCachedGrades.put(index + "", grade);
//            index++;
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Grades> grades) {
        gradeLocalDataSource.deleteAllGrades();
        for (Grades grade : grades) {
            gradeLocalDataSource.saveGrade(grade);
        }
    }

    private void getGradesFromLocalDataSource(@NonNull final LoadGradesCallback callback) {

        gradeLocalDataSource.getGrades(new LoadGradesCallback() {
            @Override
            public void onGradeLoaded(List<Grades> grades) {
                Log.d("GradeRepository", "Load from Local 2, size: " + grades.size());
                refreshCache(grades);
//                for (Grades grade: grades) {
//                    Log.d("Repositor local2 loaded", "grade: " + grade.getId() + " name " + grade.getSubject());
//                }
                callback.onGradeLoaded(new ArrayList<>(mCachedGrades.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Nullable
    private Grades getGradeWithId(@NonNull Long id) {
        checkNotNull(id);
        if (mCachedGrades == null || mCachedGrades.isEmpty()) {
            return null;
        } else {
            return mCachedGrades.get(id);
        }
    }

    @Nullable
    private Grades getGradeWithSubject(@NonNull String name) {
        checkNotNull(name);
        if (mCachedGrades == null || mCachedGrades.isEmpty()) {
            return null;
        } else {
            return mCachedGrades.get(name);
        }
    }

    public String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
