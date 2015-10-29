package org.toursys.repository.type;

import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlInternalType;
import org.toursys.repository.model.Results;
import org.toursys.repository.model.Score;

public class ResultType extends SqlInternalType {

    static Pattern pattern = Pattern.compile("");

    @Override
    public Class<?>[] getClassTypes() {
        return new Class[] { Results.class };
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
    public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName,
            Object resultValue, boolean ingoreError) throws SqlRuntimeException {
        if (resultValue == null) {
			if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, null, Results.class))
				return;             	
            if (ingoreError) {
                logger.error("There's no getter for " + attributeName + " in " + resultInstance
                        + ", META type is ResultType");
                return;
            } else {
                throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
                        + ", META type is ResultType");
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

        String result = (String) resultValue;
		if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, new Results(result),
				Results.class))
			return;
		if (ingoreError) {
			logger.error("There's no getter for " + attributeName + " in " + resultInstance
					+ ", META type is ResultType");
			return;
		} else {
			throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
					+ ", META type is ResultType");
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