package org.tahom.repository.type;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlInternalType;
import org.tahom.repository.model.Surname;

public class SurnameType extends SqlInternalType {

	@Override
	public Class<?>[] getClassTypes() {
		return new Class[] { Surname.class };
	}

	@Override
	public String[] getMetaTypes() {
		return new String[] { "surname" };
	}

	@Override
	public void addScalar(SqlQuery query, String dbName, Class<?> attributeType) {
		query.addScalar(dbName, Types.VARCHAR);
	}

	@Override
	public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName,
	        Object resultValue, boolean ingoreError) throws SqlRuntimeException {
		if (resultValue == null) {
			if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, null, Surname.class))
				return;
			if (ingoreError) {
				logger.error("There's no setter for " + attributeName + " in " + resultInstance
				        + ", META type is SurnameType");
				return;
			} else {
				throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
				        + ", META type is SurnameType");
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

		String surname = (String) resultValue;
		Surname value;
		if (surname.contains(" ")) {
			value = new Surname(surname);
		} else {
			value = new Surname(surname.split(" ")[0], surname.split(" ")[1]);
		}

		if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, value, Surname.class))
			return;
		if (ingoreError) {
			logger.error("There's no setter for " + attributeName + " in " + resultInstance
			        + ", META type is SurnameType");
			return;
		} else {
			throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
			        + ", META type is SurnameType");
		}
	}

	@Override
	public void setParameter(SqlRuntimeContext runtimeCtxCtx, SqlQuery query, String paramName, Object inputValue,
	        Class<?> inputType, boolean ingoreError) throws SqlRuntimeException {
		if (inputValue == null) {
			query.setParameter(paramName, inputValue, Types.VARCHAR);
		} else {
			if (inputValue instanceof Collection) {
				List<String> surnames = new ArrayList<String>();
				for (Iterator iter = ((Collection) inputValue).iterator(); iter.hasNext();) {
					Object o = iter.next();
					if (o != null) {
						if (!(o instanceof Surname)) {
							if (ingoreError) {
								logger.error("Incorrect input value type " + o + ", it should be a Surname");
								continue;
							} else {
								throw new SqlRuntimeException("Incorrect input value type " + o
								        + ", it should be a sSurname");
							}
						}
					}
				}
				query.setParameterList(paramName, surnames.toArray(), Types.VARCHAR);
			} else {
				if (!(inputValue instanceof Surname)) {
					if (ingoreError) {
						logger.error("Incorrect input value type " + inputValue + ", it should be a sSurname");
						return;
					} else {
						throw new SqlRuntimeException("Incorrect input value type " + inputValue
						        + ", it should be a sSurname");
					}
				}
				String surname = ((Surname) inputValue).toString();
				query.setParameter(paramName, surname, Types.VARCHAR);
			}
		}
	}

}
