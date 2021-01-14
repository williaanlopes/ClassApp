package com.gurpster.facapemobile.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gurpster.facapemobile.model.Subject;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 20/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Dao
public interface SubjectRepository {

    @Insert
    void insert(Subject subject);

    @Query("SELECT * FROM subject")
    List<Subject> all();

    @Query("SELECT * FROM subject")
    LiveData<List<Subject>> findAll();

    @Query("SELECT * FROM subject WHERE name LIKE :name")
    List<Subject> findByName(String name);
}
