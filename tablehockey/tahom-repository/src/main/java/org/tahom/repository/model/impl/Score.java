package org.tahom.repository.model.impl;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int leftSide;
    protected int rightSide;

    public Score(int leftSide, int rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public int getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(int leftSide) {
        this.leftSide = leftSide;
    }

    public int getRightSide() {
        return rightSide;
    }

    public void setRightSide(int rightSide) {
        this.rightSide = rightSide;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + leftSide;
        result = prime * result + rightSide;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Score other = (Score) obj;
        if (leftSide != other.leftSide)
            return false;
        if (rightSide != other.rightSide)
            return false;
        return true;
    }

    public String toString() {
        return leftSide + ":" + rightSide;
    }

}
