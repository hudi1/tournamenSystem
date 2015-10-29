package org.toursys.repository.type;

import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlInternalType;
import org.toursys.repository.model.Score;

public class ScoreType extends SqlInternalType {

    static Pattern pattern = Pattern.compile("^\\(?(\\d{1,5})\\)?[:]?(\\d{1,5})$");

    @Override
    public Class<?>[] getClassTypes() {
        return new Class[] { Score.class };
    }

    @Override
    public String[] getMetaTypes() {
        return new String[] { "score" };
    }

    @Override
    public void addScalar(SqlQuery query, String dbName, Class<?> attributeType) {
        query.addScalar(dbName, Types.VARCHAR);
    }

    @Override
    public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName,
            Object resultValue, boolean ingoreError) throws SqlRuntimeException {
        if (resultValue == null) {
			if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, null, Score.class))
				return;        	
            if (ingoreError) {
                logger.error("There's no setter for " + attributeName + " in " + resultInstance
                        + ", META type is ScoreType");
                return;
            } else {
                throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
                        + ", META type is ScoreType");
            }
        }

        if (!(resultValue instanceof String)) {
            if (ingoreError) {
                logger.error("Incorrect result value type " + resultValue + ", it should be a string");
                return;
            } else {
                throw new SqlRuntimeException("Incorrect result value type " + resultValue + ", it should be a string");
            }
        }

        String score = (String) resultValue;
        Matcher matcher = pattern.matcher(score);
        if (!matcher.matches()) {
            if (ingoreError) {
                logger.error("Incorrect result score format '" + score + "'");
                return;
            } else {
                throw new SqlRuntimeException("Incorrect result score format '" + score + "'");
            }
        }

        int leftSide = Integer.parseInt(matcher.group(1));
        int rightSide = Integer.parseInt(matcher.group(2));
		if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, new Score(leftSide,rightSide),
				Score.class))
			return;
		if (ingoreError) {
			logger.error("There's no setter for " + attributeName + " in " + resultInstance
					+ ", META type is ScoreType");
			return;
		} else {
			throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
					+ ", META type is ScoreType");
		}
    }

    @Override
    public void setParameter(SqlRuntimeContext runtimeCtxCtx, SqlQuery query, String paramName, Object inputValue,
            Class<?> inputType, boolean ingoreError) throws SqlRuntimeException {
        if (inputValue == null) {
            query.setParameter(paramName, inputValue, Types.VARCHAR);
        } else {
            if (inputValue instanceof Collection) {
                List<String> scores = new ArrayList<String>();
                for (Iterator iter = ((Collection) inputValue).iterator(); iter.hasNext();) {
                    Object o = iter.next();
                    if (o != null) {
                        if (!(o instanceof Score)) {
                            if (ingoreError) {
                                logger.error("Incorrect input value type " + o + ", it should be a Score");
                                continue;
                            } else {
                                throw new SqlRuntimeException("Incorrect input value type " + o
                                        + ", it should be a sScore");
                            }
                        }
                    }
                }
                query.setParameterList(paramName, scores.toArray(), Types.VARCHAR);
            } else {
                if (!(inputValue instanceof Score)) {
                    if (ingoreError) {
                        logger.error("Incorrect input value type " + inputValue + ", it should be a sScore");
                        return;
                    } else {
                        throw new SqlRuntimeException("Incorrect input value type " + inputValue
                                + ", it should be a sScore");
                    }
                }
                String score = ((Score) inputValue).toString();
                query.setParameter(paramName, score, Types.VARCHAR);
            }
        }
    }

}
