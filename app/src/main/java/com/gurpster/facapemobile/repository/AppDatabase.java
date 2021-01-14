package com.gurpster.facapemobile.repository;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.model.Subject;

/**
 * Created by Williaan Lopes (d3x773r) on 20/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Database(entities = {Grade.class, Subject.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "facapemobile.db";

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract GradeRepository gradeRepository();
    public abstract SubjectRepository subjectRepository();

}
