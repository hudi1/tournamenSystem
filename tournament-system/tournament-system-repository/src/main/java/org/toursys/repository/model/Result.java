package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private long resultId;

    private Integer leftSide;

    private Integer rightSide;

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
    }

    public Boolean getOvertime() {
        return overtime;
    }

    public void setOvertime(Boolean overtime) {
        this.overtime = overtime;
    }

    @Override
    public String toString() {
        return "Result [leftSide=" + leftSide + ", rightSide=" + rightSide + "]";
    }

}
