package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Schedule implements Parcelable {

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
	private int id;

//    @Expose
//    @SerializedName("monday")
//    private List<Day> monday; // segunda-feira
//
//    @Expose
//    @SerializedName("tuesday")
//    private List<Day> tuesday; // terca-feira
//
//    @Expose
//    @SerializedName("wednesday")
//    private List<Day> wednesday; // quarta-feira
//
//    @Expose
//    @SerializedName("thursday")
//    private List<Day> thursday; // quinta-feira
//
//    @Expose
//    @SerializedName("friday")
//    private List<Day> friday; // sexta-feira
//
//    @Expose
//    @SerializedName("saturday")
//    private List<Day> saturday; // sabado

    @Expose
    @SerializedName("day_of_week")
    private String dayOfWeek; // dia da semna {segunda, terca, quarta, ...}

    @Expose
    @SerializedName("subject_name")
    private String subjectName; // nome da disciplina {Teoria dos grafos, Redes, Programacao 3, ...}

    @Expose
    @SerializedName("professor_name")
    private String professorName; // nome do professor da disciplina

    @Expose
    @SerializedName("period")
    private String period; // periodo pertinente a ESSA disciplina {1 periodo, 2 periodo, ...}

    @Expose
    @SerializedName("time")
    private String time; // horario da aula {7:30, 8:30, ..., 19:00, 20:30, ...}

    @Expose
    @SerializedName("course")
    private String course; // curso pertinente a ESSA disciplina {direito, administracao, contabeis, computacao, ...}

    @Expose
    @SerializedName("color")
    private String color;

    public Schedule(){
    }

    @Ignore
	public Schedule(String dayOfWeek, String subjectName, String professorName, String period, String time, String course) {
		this.dayOfWeek = dayOfWeek;
		this.subjectName = subjectName;
		this.professorName = professorName;
		this.period = period;
		this.time = time;
		this.course = course;
	}

    @Ignore
    public Schedule(String dayOfWeek, String subjectName, String professorName, String period, String time, String course, String color) {
        this.dayOfWeek = dayOfWeek;
        this.subjectName = subjectName;
        this.professorName = professorName;
        this.period = period;
        this.time = time;
        this.course = course;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Schedule> generateScheduleItens() {
        List<Schedule> list = new ArrayList<>();

        list.add(new Schedule("Monday", "FUND COMPUT", "Gondim", "5°", "07:30", "Computacao"));
        list.add(new Schedule("Monday", "FISICA COMP", "Fabrizio Costa", "5°", "09:10", "Computacao"));

        list.add(new Schedule("Tuesday", "FUND COMPUT", "Gondim","5°",  "07:30","Computacao"));
        list.add(new Schedule("Tuesday", "METOD PQ CT", "Dinani","5°", "09:10", "Computacao"));

        list.add(new Schedule("Wednesday", "FISICA COMP", "Fabrizio Costa","5°","07:30", "Computacao"));
        list.add(new Schedule("Wednesday", "MAT BASICA", "Geovanilde","5°","09:10",  "Computacao"));

        list.add(new Schedule("Thursday","ALGORITMO",  "Alexandre Braga","5°","07:30",  "Computacao"));
        list.add(new Schedule("Thursday","METOD PQ CT",  "Dinani","5°","09:10",  "Computacao"));

        list.add(new Schedule("Friday", "ALGORITMOS", "Alexandre Braga","5°","07:30",  "Computacao"));
        list.add(new Schedule("Friday", "MAT BASICA","Geovanilde","5°", "09:10",  "Computacao"));


        list.add(new Schedule("Saturday","APENAS UM TESTE!",  "Teste","5°","16:50",  "Computacao"));
        list.add(new Schedule("Saturday","APENAS UM TESTE!",  "Teste","5°","13:30",  "Computacao"));
        list.add(new Schedule("Saturday","APENAS UM TESTE!",  "Teste","5°","20:30",  "Computacao"));

        return list;
    }

//    @Override
//    public int compareTo(@NonNull Schedule s) {
//        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        if (Integer.parseInt(s.dayOfWeek) < currentDay) {
//            return -1;
//        } else if (Integer.parseInt(s.dayOfWeek) > currentDay) {
//            return 1;
//        }
//        return 0;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.dayOfWeek);
        dest.writeString(this.subjectName);
        dest.writeString(this.professorName);
        dest.writeString(this.period);
        dest.writeString(this.time);
        dest.writeString(this.course);
        dest.writeString(this.color);
    }

    protected Schedule(Parcel in) {
        this.id = in.readInt();
        this.dayOfWeek = in.readString();
        this.subjectName = in.readString();
        this.professorName = in.readString();
        this.period = in.readString();
        this.time = in.readString();
        this.course = in.readString();
        this.color = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
