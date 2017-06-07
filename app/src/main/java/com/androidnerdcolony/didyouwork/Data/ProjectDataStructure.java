package com.androidnerdcolony.didyouwork.Data;

/**
 * Created by tiger on 6/7/2017.
 */

public class ProjectDataStructure {

    private long id;
    private String project_name;
    private String create_date;
    private String wage;
    private String location;
    private String tags;
    private long dead_line;
    private String work_time;
    private int time_rounding;
    private int project_type;
    private long last_activity;
    private String description;

    ProjectDataStructure() {

    }

    public ProjectDataStructure(long id, String project_name, String create_date, String wage, String location, String tags, long dead_line, String work_time, int time_rounding, int project_type, long last_activity, String description) {
        this.id = id;

        this.project_name = project_name;
        this.create_date = create_date;
        this.wage = wage;
        this.location = location;
        this.tags = tags;
        this.dead_line = dead_line;
        this.work_time = work_time;
        this.time_rounding = time_rounding;
        this.project_type = project_type;
        this.last_activity = last_activity;
        this.description = description;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getDead_line() {
        return dead_line;
    }

    public void setDead_line(long dead_line) {
        this.dead_line = dead_line;
    }

    public int getTime_rounding() {
        return time_rounding;
    }

    public void setTime_rounding(int time_rounding) {
        this.time_rounding = time_rounding;
    }

    public int getProject_type() {
        return project_type;
    }

    public void setProject_type(int project_type) {
        this.project_type = project_type;
    }

    public long getLast_activity() {
        return last_activity;
    }

    public void setLast_activity(long last_activity) {
        this.last_activity = last_activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }
}
