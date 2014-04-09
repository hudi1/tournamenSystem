package org.toursys.repository.type;

import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.impl.BeanUtils;
import org.sqlproc.engine.type.SqlInternalType;
import org.toursys.repository.model.Results;

public class ResultType extends SqlInternalType {

    static Pattern pattern = Pattern.compile("");

    @Override
    public Class<?>[] getClassTypes() {
        return new Class[] { ResultType.class };
    }

    @Override
    public String[] getMetaTypes() {
        return new String[] { "result" };
    }

    @Override
    public void addScalar(SqlQuery query, String dbName, Class<?> attributeType) {
        query.addScalar(dbName, Types.VARCHAR);
    }

    @Override
    public void setResult(Object resultInstance, String attributeName, Object resultValue, boolean ingoreError)
            throws SqlRuntimeException {
        Method m = BeanUtils.getSetter(resultInstance, attributeName, Results.class);
        if (m == null) {
            if (ingoreError) {
                logger.error("There's no getter for " + attributeName + " in " + resultInstance
                        + ", META type is ResultType");
                return;
            } else {
                throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
                        + ", META type is ResultType");
            }
        }

        if (resultValue == null) {
            BeanUtils.simpleInvokeMethod(m, resultInstance, new Results());
            return;
        }

        if (!(resultValue instanceof String)) {
            if (ingoreError) {
                logger.error("Incorrect result value type " + resultValue + ", it should be a string");
                return;
            } else {
                throw new SqlRuntimeException("Incorrect result value type " + resultValue + ", it should be a string");
            }
        }

        String result = (String) resultValue;
        /*
         * Matcher matcher = pattern.matcher(score); if (!matcher.matches()) { if (ingoreError) {
         * logger.error("Incorrect result score format '" + score + "'"); return; } else { throw new
         * SqlRuntimeException("Incorrect result score format '" + score + "'"); } }
         * 
         * int leftSide = Integer.parseInt(matcher.group(1)); int rightSide = Integer.parseInt(matcher.group(2));
         */
        BeanUtils.simpleInvokeMethod(m, resultInstance, new Results(result));

    }

    @Override
    public void setParameter(SqlQuery query, String paramName, Object inputValue, Class<?> inputType,
            boolean ingoreError) throws SqlRuntimeException {
        if (inputValue == null) {
            query.setParameter(paramName, inputValue, Types.VARCHAR);
        } else {
            if (inputValue instanceof Collection) {
                List<String> scores = new ArrayList<String>();
                for (Iterator iter = ((Collection) inputValue).iterator(); iter.hasNext();) {
                    Object o = iter.next();
                    if (o != null) {
                        if (!(o instanceof Results)) {
                            if (ingoreError) {
                                logger.error("Incorrect input value type " + o + ", it should be a Results");
                                continue;
                            } else {
                                throw new SqlRuntimeException("Incorrect input value type " + o
                                        + ", it should be a Results");
                            }
                        }
                    }
                }
                query.setParameterList(paramName, scores.toArray(), Types.VARCHAR);
            } else {
                if (!(inputValue instanceof Results)) {
                    if (ingoreError) {
                        logger.error("Incorrect input value type " + inputValue + ", it should be a Results");
                        return;
                    } else {
                        throw new SqlRuntimeException("Incorrect input value type " + inputValue
                                + ", it should be a Results");
                    }
                }
                String result = ((Results) inputValue).toString();
                query.setParameter(paramName, result, Types.VARCHAR);
            }
        }
    }
}