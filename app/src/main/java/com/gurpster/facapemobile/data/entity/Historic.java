package com.gurpster.facapemobile.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Historic implements Parcelable {

    @Expose(deserialize = false, serialize = false)
	@PrimaryKey(autoGenerate = true)
	private Long id;

    @Expose
	private String year;

    @Expose
    private String semester;

    @Expose
    private String subject;

    @Expose
    private String grade1;

    @Expose
    private String grade2;

    @Expose
    private String grade3;

    @Expose
    private String grade4;

    @Expose
    private String score;

    @Expose
    private String absences;

    @Expose
	@SerializedName("max_absences")
	private String maxAbsences;

    @Expose
	@SerializedName("status")
	private String status;

	public Historic(String year, String semester, String subject, String grade1, String grade2, String grade3, String grade4, String score, String absences, String maxAbsences, String status) {
		this.year = year;
		this.semester = semester;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
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
		dest.writeValue(this.id);
		dest.writeString(this.year);
		dest.writeString(this.semester);
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

	protected Historic(Parcel in) {
		this.id = (Long) in.readValue(Long.class.getClassLoader());
		this.year = in.readString();
		this.semester = in.readString();
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

	public static final Creator<Historic> CREATOR = new Creator<Historic>() {
		@Override
		public Historic createFromParcel(Parcel source) {
			return new Historic(source);
		}

		@Override
		public Historic[] newArray(int size) {
			return new Historic[size];
		}
	};
}
