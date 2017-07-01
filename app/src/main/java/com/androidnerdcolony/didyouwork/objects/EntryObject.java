package com.androidnerdcolony.didyouwork.objects;


public class EntryObject {

    private long _id;
    private long project_id;
    private long start_date;
    private long end_date;
    private String tags;
    private long over_time;
    private double bonus;
    private int active;
    private String description;


    public EntryObject() {
    }

    public EntryObject(long id, long project_id, long start_date, long end_date, String tags, long over_time, double bonus, int active, String description) {
        _id = id;
        this.project_id = project_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.tags = tags;
        this.over_time = over_time;
        this.bonus = bonus;
        this.active = active;
        this.description = description;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getOver_time() {
        return over_time;
    }

    public void setOver_time(long over_time) {
        this.over_time = over_time;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
