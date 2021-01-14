package com.gurpster.facapemobile.data.source.local.sqlite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;
import com.gurpster.facapemobile.data.source.GradeDataSource;
import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
public class GradeLocalDataSource implements GradeDataSource {

    private static final String TAG = "GradeLocalDataSource";


    private final GradeDao gradeDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public GradeLocalDataSource(@NonNull AppExecutors executors, @NonNull GradeDao gradeDao) {
        this.gradeDao = gradeDao;
        mAppExecutors = executors;
    }

    /**
     * Note: {@link LoadGradesCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getGrades(@NonNull final LoadGradesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Grades> grades = gradeDao.getGrades();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (grades.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onGradeLoaded(grades);
                        }
                    }
                });
            }
        };
        Log.d(TAG, "load grades: ");
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getOldGrades(int year, int semester, @NonNull LoadGradesCallback callback) {
        // nothing to do
    }

    /**
     * Note: {@link GetGradeCallback#onDataNotAvailable()} is fired if the {@link Grade} isn't
     * found.
     */
    @Override
    public void getGrade(@NonNull final Long gradeId, @NonNull final GetGradeCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Grades grade = gradeDao.getGradeById(gradeId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (grade != null) {
                            callback.onGradeLoaded(grade);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        Log.d(TAG, "load grade: "+gradeId);
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveGrade(@NonNull final Grades grade) {
        checkNotNull(grade);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                gradeDao.insert(grade);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
        Log.d(TAG, "save grade: "+grade.getSubject());
    }

    @Override
    public void saveGrades(@NonNull Grades grade) {
        // TODO
    }

    @Override
    public void refreshGrades() {
        // Not required because the {@link GradeRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllGrades() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                gradeDao.deleteGrades();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteGrade(@NonNull final Long gradeId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                gradeDao.deleteGradeById(gradeId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
