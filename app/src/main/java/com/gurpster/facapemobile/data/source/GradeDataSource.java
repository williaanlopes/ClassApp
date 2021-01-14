package com.gurpster.facapemobile.data.source;

import android.support.annotation.NonNull;

//import com.gurpster.facapemobile.model.Grade;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface GradeDataSource {

    interface LoadGradesCallback {

        void onGradeLoaded(List<Grades> grades);

        void onDataNotAvailable();
    }

    interface GetGradeCallback {

        void onGradeLoaded(Grades grade);

        void onDataNotAvailable();
    }

//    void getGrade(@NonNull LoadGradesCallback callback);

    void getGrades(@NonNull LoadGradesCallback callback);

//    void getOldGrades(@NonNull Semester semester, @NonNull LoadGradesCallback callback);
    void getOldGrades(@NonNull int year, @NonNull int semester, @NonNull final LoadGradesCallback callback);

    void getGrade(@NonNull Long gradeId, @NonNull GetGradeCallback callback);

    void saveGrade(@NonNull Grades grade);

    void saveGrades(@NonNull Grades grade);

//    void completeTask(@NonNull Grade grade);

//    void completeTask(@NonNull String gradeId);

//    void activateTask(@NonNull Grade task);

//    void activateTask(@NonNull String gradeId);

//    void clearCompletedGrades();

    void refreshGrades();

    void deleteAllGrades();

    void deleteGrade(@NonNull Long gradeId);
}
