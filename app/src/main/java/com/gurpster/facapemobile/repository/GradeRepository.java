package com.gurpster.facapemobile.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gurpster.facapemobile.model.Grade;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 20/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Dao
public interface GradeRepository {

    @Insert
    void insert(Grade grade);

    @Update
    int update (Grade grade);

    @Query("SELECT * FROM grade")
    List<Grade> all();
}
