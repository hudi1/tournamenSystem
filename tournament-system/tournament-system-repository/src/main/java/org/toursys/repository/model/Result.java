package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Result extends Score implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean overtime;
    private boolean contumacy;

    private static final String RESULT_DELIMETER = ":";
    private static final String OVERTIME_FLAG = "P";
    private static final String CONTUMACY_FLAG = "K";

    public Result(String result) {
        this(0, 0);
        if (result.contains(OVERTIME_FLAG)) {
            overtime = true;
            result = result.replace(OVERTIME_FLAG, "").trim();
        }
        if (result.contains(CONTUMACY_FLAG)) {
            contumacy = true;
            result = result.replace(CONTUMACY_FLAG, "").trim();

        }
        String[] split = result.split(RESULT_DELIMETER);
        this.leftSide = Integer.parseInt(split[0].trim());
        this.rightSide = Integer.parseInt(split[1].trim());
    }

    public Result(int leftSide, int rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(leftSide);
        builder.append(RESULT_DELIMETER);
        builder.append(rightSide);
        if (overtime) {
            builder.append(" " + OVERTIME_FLAG);
        }
        if (contumacy) {
            builder.append(" " + CONTUMACY_FLAG);
        }
        return builder.toString();
    }

    public String revert() {
        StringBuilder builder = new StringBuilder();
        builder.append(rightSide);
        builder.append(RESULT_DELIMETER);
        builder.append(leftSide);
        if (overtime) {
            builder.append(" " + OVERTIME_FLAG);
        }
        if (contumacy) {
            builder.append(" " + CONTUMACY_FLAG);
        }
        return builder.toString();
    }

}
