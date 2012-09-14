package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private long resultId;

    private Integer leftSide;

    private Integer rightSide;

    private String rightSideOvertime;

    private Boolean overtime;

    public Result() {
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public Integer getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Integer leftSide) {
        this.leftSide = leftSide;
    }

    public Integer getRightSide() {
        return rightSide;
    }

    public void setRightSide(Integer rightSide) {
        this.rightSide = rightSide;
        this.rightSideOvertime = String.valueOf(rightSide);
    }

    public Boolean getOvertime() {
        return overtime;
    }

    public void setOvertime(Boolean overtime) {
        this.overtime = overtime;
        if (overtime != null && overtime)
            this.rightSideOvertime += "P";
    }

    public String getRightSideOvertime() {
        return rightSideOvertime;
    }

    public void setRightSideOvertime(String rightSideOvertime) {
        this.rightSideOvertime = rightSideOvertime;
        try {
            this.rightSide = Integer.parseInt(rightSideOvertime);
            this.overtime = false;
        } catch (NumberFormatException e) {
            if (rightSideOvertime.toUpperCase().indexOf("P") != -1) {
                this.overtime = true;
                try {
                    this.rightSide = Integer
                            .parseInt(rightSideOvertime.substring(0, rightSideOvertime.indexOf("P") + 1));
                } catch (NumberFormatException e1) {

                }
            }
        }
    }

    @Override
    public String toString() {
        return "Result [leftSide=" + leftSide + ", rightSide=" + rightSide + "]" + rightSideOvertime;
    }

}
