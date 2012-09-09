package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.ResultDao;
import org.toursys.repository.model.Result;

public class ResultDaoImpl extends BaseDaoImpl implements ResultDao {

    @Override
    public Result createResult(Result result) {
        SqlSession session = getSqlSession();
        getCrudEngine("INSERT_RESULT").insert(session, result);
        return result;
    }

    @Override
    public void updateResult(Result result) {
        SqlSession session = getSqlSession();
        getCrudEngine("UPDATE_RESULT").update(session, result);
    }

    @Override
    public void deleteResult(Result result) {
        SqlSession session = getSqlSession();
        getCrudEngine("DELETE_RESULT").delete(session, result);
    }

    @Override
    public List<Result> getAllResult() {
        SqlSession session = getSqlSession();
        return getQueryEngine("GET_ALL_RESULT").query(session, Result.class);
    }

}
