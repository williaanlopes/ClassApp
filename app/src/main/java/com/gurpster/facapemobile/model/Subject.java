package com.gurpster.facapemobile.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
147963 * Created by willi on 03/11/2017.
 */

@Entity
public class Subject implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String name;

    private String day;

    private String startTime;

    private String endTime;

    private String location;

    private String professorName;

    private int color;

    @Ignore
    public Subject() {
    }

    @Ignore
    public Subject(String name, String day, String startTime, String endTime, String professorName) {
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.professorName = professorName;
    }

    public Subject(String name, String day, String startTime, String endTime, String professorName, int color) {
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.professorName = professorName;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "_id=" + id +
                '}';
    }

    public List<Subject> generateScheduleItens() {
        List<Subject> list = new ArrayList<>();

        list.add(new Subject("FUND COMPUT", "Monday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FISICA COMP", "Monday", "09:10", "10:50", "Fabrizio Costa"));

        list.add(new Subject("FUND COMPUT", "Tuesday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("METOD PQ CT", "Tuesday", "09:10", "10:50", "Dinani"));

        list.add(new Subject("FISICA COMP", "Wednesday", "07:30", "09:10", "Fabrizio Costa"));
        list.add(new Subject("MAT BASICA", "Wednesday", "09:10", "10:50", "Geovanilde"));

        list.add(new Subject("ALGORITMO", "Thursday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("METOD PQ CT", "Thursday", "09:10", "10:50", "Dinani"));

        list.add(new Subject("ALGORITMOS", "Friday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("MAT BASICA", "Friday", "09:10", "10:50", "Geovanilde"));


        list.add(new Subject("APENAS UM TESTE!", "Saturday", "16:50", "18:50", "Teste"));
        list.add(new Subject("APENAS UM TESTE!", "Saturday", "13:30", "15:10", "Teste"));
        list.add(new Subject("APENAS UM TESTE!", "Saturday", "20:30", "22:10", "Teste"));

        return list;
    }

    public static List<Subject> generateItens() {
        List<Subject> list = new ArrayList<>();

        list.add(new Subject("FUND COMPUT", "Monday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FISICA COMP", "Monday", "09:10", "10:50", "Fabrizio Costa"));

        list.add(new Subject("FUND COMPUT", "Tuesday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("METOD PQ CT", "Tuesday", "09:10", "10:50", "Dinani"));

        list.add(new Subject("FISICA COMP", "Wednesday", "07:30", "09:10", "Fabrizio Costa"));
        list.add(new Subject("MAT BASICA", "Wednesday", "09:10", "10:50", "Geovanilde"));

        list.add(new Subject("ALGORITMO", "Thursday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("METOD PQ CT", "Thursday", "09:10", "10:50", "Dinani"));

        list.add(new Subject("ALGORITMOS", "Friday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("MAT BASICA", "Friday", "09:10", "10:50", "Geovanilde"));

        return list;
    }

}
