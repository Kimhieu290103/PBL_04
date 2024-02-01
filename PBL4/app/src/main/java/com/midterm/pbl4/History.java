package com.midterm.pbl4;

public class History {
    private Integer count;
    private String timeStart;
    private String timeEnd;
    private String totalTime;

    public History() {
    }

    public History(Integer count, String timeStart, String timeEnd, String totalTime) {
        this.count = count;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.totalTime = totalTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
