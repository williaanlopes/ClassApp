package com.gurpster.facapemobile.data.source.local.sqlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.model.Grade;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Dao
public interface ScheduleDao {

    /**
     * Select all schedules from the schedules table.
     *
     * @return all schedule.
     */
    @Query("SELECT * FROM Schedule")
    List<Schedule> getSchedules();

    /**
     * Select a schedule by id.
     *
     * @param id the schedule id.
     * @return the schedule with scheduleId.
     */
    @Query("SELECT * FROM Schedule WHERE id = :id")
    Schedule getScheduleById(Long id);

    /**
     * Select a schedule by subjectName.
     *
     * @param subjectName the name of subject in schedule.
     * @return the schedule with scheduleId.
     */
    @Query("SELECT * FROM Schedule WHERE subjectName = :subjectName")
    Schedule getScheduleBySubjectName(String subjectName);

    /**
     * Select a schedule by subjectName.
     *
     * @param day the name of subject in schedule.
     * @return the schedule with scheduleId.
     */
//    @Query("SELECT * FROM Schedule WHERE day = :day")
//    Schedule getScheduleByDay(String day);

    /**
     * Insert a schedule in the database. If the schedule already exists, replace it.
     *
     * @param schedule the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Schedule schedule);

    /**
     * Update a schedule.
     *
     * @param schedule schedule to be updated
     * @return the number of schedules updated. This should always be 1.
     */
//    @Update
//    int updateGrade(Grade schedule);

//    @Query("UPDATE Grade SET completed = :completed WHERE id = :id")
//    void updateGradeById(String id);

    /**
     * Delete a schedule by id.
     *
     * @return the number of schedules deleted. This should always be 1.
     */
//    @Query("DELETE FROM Grade WHERE id = :scheduleId")
//    int deleteGradeById(String taskId);

    /**
     * Delete all schedule.
     */
    @Query("DELETE FROM Schedule")
    void deleteSchedules();

}
