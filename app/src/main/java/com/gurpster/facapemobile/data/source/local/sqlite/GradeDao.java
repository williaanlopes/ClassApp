package com.gurpster.facapemobile.data.source.local.sqlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.model.Grade;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Dao
public interface GradeDao {

    /**
     * Select all grades from the grade table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Grades")
    List<Grades> getGrades();

    /**
     * Select a grade by id.
     *
     * @param id the grade id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Grades WHERE id = :id")
    Grades getGradeById(Long id);

    /**
     * Insert a task in the database. If the grade already exists, replace it.
     *
     * @param grade the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Grades grade);

    /**
     * Update a task.
     *
     * @param grade task to be updated
     * @return the number of grades updated. This should always be 1.
     */
//    @Update
//    int updateGrade(Grades grade);

//    @Query("UPDATE Grade SET completed = :completed WHERE id = :id")
//    void updateGradeById(String id);

    /**
     * Delete a grade by id.
     *
     * @return the number of grades deleted. This should always be 1.
     */
    @Query("DELETE FROM Grades WHERE id = :id")
    int deleteGradeById(Long id);

    /**
     * Delete a grade by name.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM Grades WHERE subject = :subject")
    int deleteGradeBySubject(String subject);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Grades")
    void deleteGrades();

}
