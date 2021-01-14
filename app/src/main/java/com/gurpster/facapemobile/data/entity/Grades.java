package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Grades implements Parcelable {

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
	private int id;

    @Expose
    @SerializedName("subject")
	private String subject; // nome da disciplina {Teoria dos grafos, Redes, Programacao 3, ...}

    @Expose
    @SerializedName("grade1")
    private String grade1; // primeira nota

    @Expose
    @SerializedName("grade2")
    private String grade2; // segunda nota

    @Expose
    @SerializedName("grade3")
    private String grade3; // terceita nota

    @Expose
    @SerializedName("grade4")
    private String grade4; // final

    @Expose
    @SerializedName("score")
    private String score; // media

    @Expose
    @SerializedName("absences")
    private String absences; // faltas

    @Expose
    @SerializedName("max_absences")
    private String maxAbsences; // quantidade maxima de faltas { 16 default }

    @Expose
    @SerializedName("status")
    private String status; // status {Aprovado, Reprovado, Matriculado, ...}

	public Grades(String subject, String grade1, String grade2, String grade3, String grade4, String score, String absences, String maxAbsences, String status) {
		this.subject = subject;
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.grade3 = grade3;
		this.grade4 = grade4;
		this.score = score;
		this.absences = absences;
		this.maxAbsences = maxAbsences;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade1() {
		return grade1;
	}

	public void setGrade1(String grade1) {
		this.grade1 = grade1;
	}

	public String getGrade2() {
		return grade2;
	}

	public void setGrade2(String grade2) {
		this.grade2 = grade2;
	}

	public String getGrade3() {
		return grade3;
	}

	public void setGrade3(String grade3) {
		this.grade3 = grade3;
	}

	public String getGrade4() {
		return grade4;
	}

	public void setGrade4(String grade4) {
		this.grade4 = grade4;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAbsences() {
		return absences;
	}

	public void setAbsences(String absences) {
		this.absences = absences;
	}

	public String getMaxAbsences() {
		return maxAbsences;
	}

	public void setMaxAbsences(String maxAbsences) {
		this.maxAbsences = maxAbsences;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.subject);
		dest.writeString(this.grade1);
		dest.writeString(this.grade2);
		dest.writeString(this.grade3);
		dest.writeString(this.grade4);
		dest.writeString(this.score);
		dest.writeString(this.absences);
		dest.writeString(this.maxAbsences);
		dest.writeString(this.status);
	}

	protected Grades(Parcel in) {
		this.id = in.readInt();
		this.subject = in.readString();
		this.grade1 = in.readString();
		this.grade2 = in.readString();
		this.grade3 = in.readString();
		this.grade4 = in.readString();
		this.score = in.readString();
		this.absences = in.readString();
		this.maxAbsences = in.readString();
		this.status = in.readString();
	}

	public static final Creator<Grades> CREATOR = new Creator<Grades>() {
		@Override
		public Grades createFromParcel(Parcel source) {
			return new Grades(source);
		}

		@Override
		public Grades[] newArray(int size) {
			return new Grades[size];
		}
	};
}
