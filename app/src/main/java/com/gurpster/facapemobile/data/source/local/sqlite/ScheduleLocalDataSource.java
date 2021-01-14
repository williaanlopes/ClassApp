package com.gurpster.facapemobile.data.source.local.sqlite;

import android.support.annotation.NonNull;

import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.model.Subject;
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
public class ScheduleLocalDataSource implements ScheduleDataSource {


    private final ScheduleDao scheduleDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public ScheduleLocalDataSource(@NonNull AppExecutors executors, @NonNull ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
        mAppExecutors = executors;
    }

    /**
     * Note: {@link LoadScheduleCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getSchedules(@NonNull final LoadScheduleCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Schedule> schedules = scheduleDao.getSchedules();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (schedules.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onScheduleLoaded(schedules);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Note: {@link GetScheduleCallback#onDataNotAvailable()} is fired if the {@link Subject} isn't
     * found.
     */
    @Override
    public void getSchedule(@NonNull final Long taskId, @NonNull final GetScheduleCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Schedule schedule = scheduleDao.getScheduleById(taskId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (schedule != null) {
                            callback.onScheduleLoaded(schedule);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSchedule(@NonNull final String subjectName, @NonNull final GetScheduleCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Schedule schedule = scheduleDao.getScheduleBySubjectName(subjectName);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (schedule != null) {
                            callback.onScheduleLoaded(schedule);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void save(@NonNull final Schedule schedule) {
        checkNotNull(schedule);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                scheduleDao.insert(schedule);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refresh() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAll() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                scheduleDao.deleteSchedules();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
