package com.harambase.pioneer.server.pojo.view;

import javax.persistence.*;

@Entity
@Table(name = "courseview")
public class CourseView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "crn")
    private String crn;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "precrn")
    private String precrn;

    @Column(name = "level")
    private String level;

    @Column(name = "section")
    private String section;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "day")
    private String day;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "info")
    private String info;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "course_info")
    private String courseInfo;

    //Extensions:
    @Column(name = "remain")
    private Integer remain;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "profile")
    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn == null ? null : crn.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getPrecrn() {
        return precrn;
    }

    public void setPrecrn(String precrn) {
        this.precrn = precrn == null ? null : precrn.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom == null ? null : classroom.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId == null ? null : facultyId.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }
}
