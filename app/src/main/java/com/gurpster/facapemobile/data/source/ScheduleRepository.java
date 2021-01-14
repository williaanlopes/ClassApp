package com.gurpster.facapemobile.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gurpster.facapemobile.data.entity.Schedule;
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
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class ScheduleRepository implements ScheduleDataSource {

    private final ScheduleDataSource scheduleRemoteDataSource;
    private final ScheduleDataSource scheduleLocalDataSource;
    private final PreferencesHelper preferences;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Schedule> mCachedSchedules;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    @Inject
    ScheduleRepository(@Remote ScheduleDataSource scheduleRemoteDataSource,
                       @Local ScheduleDataSource scheduleLocalDataSource,
                       PreferencesHelper preferences){
        this.scheduleRemoteDataSource = scheduleRemoteDataSource;
        this.scheduleLocalDataSource = scheduleLocalDataSource;
        this.preferences = preferences;
    }

    @Override
    public void getSchedules(@NonNull final LoadScheduleCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedSchedules != null && !mCacheIsDirty) {
            List<Schedule> schedules = new ArrayList<>(mCachedSchedules.values());
            callback.onScheduleLoaded(schedules);
            Log.d("ScheduleRepository", "Load from cache size: " + schedules.size());
//            return;
        }

//        if (mCacheIsDirty) {
//            // If the cache is dirty we need to fetch new data from the network.
//            getSchedulesFromRemoteDataSource(callback);
//        }
        else {
            getSchedulesFromRemoteDataSource(callback);
            // Query the local storage if available. If not, query the network.
//            scheduleLocalDataSource.getSchedules(new ScheduleDataSource.LoadScheduleCallback() {
//                @Override
//                public void onScheduleLoaded(List<Schedule> schedules) {
//                    refreshCache(schedules);
//                    Gson gson = new Gson();
//                    String eTag = md5(gson.toJson(schedules));
//                    preferences.setPrefScheduleETag(eTag);
//                    callback.onScheduleLoaded(new ArrayList<>(mCachedSchedules.values()));
//                    Log.d("ScheduleRepository", "Load from local size: " + schedules.size());
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    getSchedulesFromRemoteDataSource(callback);
//                }
//            });
        }
    }

    @Override
    public void getSchedule(@NonNull final Long scheduleId, @NonNull final GetScheduleCallback callback) {
        checkNotNull(scheduleId);
        checkNotNull(callback);

        Schedule cachedSchedule = getScheduleWithId(scheduleId);

        // Respond immediately with cache if available
        if (cachedSchedule != null) {
            callback.onScheduleLoaded(cachedSchedule);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        scheduleLocalDataSource.getSchedule(scheduleId, new ScheduleDataSource.GetScheduleCallback() {
            @Override
            public void onScheduleLoaded(Schedule schedule) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedSchedules == null) {
                    mCachedSchedules = new LinkedHashMap<>();
                }
                mCachedSchedules.put(""+schedule.getId(), schedule);
                callback.onScheduleLoaded(schedule);
            }

            @Override
            public void onDataNotAvailable() {
                scheduleRemoteDataSource.getSchedule(scheduleId, new ScheduleDataSource.GetScheduleCallback() {
                    @Override
                    public void onScheduleLoaded(Schedule schedule) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedSchedules == null) {
                            mCachedSchedules = new LinkedHashMap<>();
                        }
                        mCachedSchedules.put(""+schedule.getId(), schedule);
                        callback.onScheduleLoaded(schedule);
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
    public void getSchedule(@NonNull final String subjectName, @NonNull final GetScheduleCallback callback) {
        checkNotNull(subjectName);
        checkNotNull(callback);

        Schedule cachedSchedule = getScheduleWithSubjectName(subjectName);

        // Respond immediately with cache if available
        if (cachedSchedule != null) {
            callback.onScheduleLoaded(cachedSchedule);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        scheduleLocalDataSource.getSchedule(subjectName, new ScheduleDataSource.GetScheduleCallback() {
            @Override
            public void onScheduleLoaded(Schedule schedule) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedSchedules == null) {
                    mCachedSchedules = new LinkedHashMap<>();
                }
                mCachedSchedules.put(schedule.getSubjectName(), schedule);
                callback.onScheduleLoaded(schedule);
            }

            @Override
            public void onDataNotAvailable() {
                scheduleRemoteDataSource.getSchedule(subjectName, new ScheduleDataSource.GetScheduleCallback() {
                    @Override
                    public void onScheduleLoaded(Schedule grade) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedSchedules == null) {
                            mCachedSchedules = new LinkedHashMap<>();
                        }
                        mCachedSchedules.put(""+grade.getId(), grade);
                        callback.onScheduleLoaded(grade);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

//    @Override
//    public void getScheduleByDay(@NonNull final String day, @NonNull final GetScheduleCallback callback) {
//        checkNotNull(callback);
//
//        Schedule cachedSchedule = getScheduleWithSubjectName(day);
//
//        // Respond immediately with cache if available
//        if (cachedSchedule != null) {
//            callback.onScheduleLoaded(cachedSchedule);
//            return;
//        }
//
//        // Load from server/persisted if needed.
//
//        // Is the task in the local data source? If not, query the network.
//        scheduleLocalDataSource.getSchedule(day, new ScheduleDataSource.GetScheduleCallback() {
//            @Override
//            public void onScheduleLoaded(Schedule schedule) {
//                // Do in memory cache update to keep the app UI up to date
//                if (mCachedSchedules == null) {
//                    mCachedSchedules = new LinkedHashMap<>();
//                }
//                mCachedSchedules.put(schedule.getSubjectName(), schedule);
//                callback.onScheduleLoaded(schedule);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
////                scheduleRemoteDataSource.getSchedule(subjectName, new ScheduleDataSource.GetScheduleCallback() {
////                    @Override
////                    public void onScheduleLoaded(Schedule grade) {
////                        // Do in memory cache update to keep the app UI up to date
////                        if (mCachedSchedules == null) {
////                            mCachedSchedules = new LinkedHashMap<>();
////                        }
////                        mCachedSchedules.put(""+grade.getId(), grade);
////                        callback.onScheduleLoaded(grade);
////                    }
////
////                    @Override
////                    public void onDataNotAvailable() {
////                        callback.onDataNotAvailable();
////                    }
////                });
//            }
//        });
//    }

    @Override
    public void save(@NonNull Schedule schedule) {
        checkNotNull(schedule);
        scheduleLocalDataSource.save(schedule);

//      Do in memory cache update to keep the app UI up to date
        if (mCachedSchedules == null) {
            mCachedSchedules = new LinkedHashMap<>();
        }
        mCachedSchedules.put(schedule.getId()+"", schedule);
    }

    private void getSchedulesFromRemoteDataSource(@NonNull final ScheduleDataSource.LoadScheduleCallback callback) {

        scheduleRemoteDataSource.getSchedules(new ScheduleDataSource.LoadScheduleCallback() {
            @Override
            public void onScheduleLoaded(List<Schedule> schedules) {
//                if(schedules.size() > 1) {
                    refreshCache(schedules);
//                    refreshLocalDataSource(schedules);
//                } else {
//                    getGradesFromLocalDataSource(callback);
//                }
                callback.onScheduleLoaded(new ArrayList<>(mCachedSchedules.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Schedule> schedules) {
        if (mCachedSchedules == null) {
            mCachedSchedules = new LinkedHashMap<>();
        }
        mCachedSchedules.clear();
        int index = 0;
        for (Schedule schedule : schedules) {
            mCachedSchedules.put(index + "", schedule);
            index++;
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Schedule> schedules) {
        scheduleLocalDataSource.deleteAll();
        for (Schedule schedule : schedules) {
            scheduleLocalDataSource.save(schedule);
        }
    }

    @Override
    public void refresh() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAll() {
        scheduleLocalDataSource.deleteAll();

        if (mCachedSchedules == null) {
            mCachedSchedules = new LinkedHashMap<>();
        }
        mCachedSchedules.clear();
    }

    @Nullable
    private Schedule getScheduleWithId(@NonNull Long id) {
        checkNotNull(id);
        if (mCachedSchedules == null || mCachedSchedules.isEmpty()) {
            return null;
        } else {
            return mCachedSchedules.get(id);
        }
    }

    @Nullable
    private Schedule getScheduleWithSubjectName(@NonNull String subjectName) {
        checkNotNull(subjectName);
        if (mCachedSchedules == null || mCachedSchedules.isEmpty()) {
            return null;
        } else {
            return mCachedSchedules.get(subjectName);
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
