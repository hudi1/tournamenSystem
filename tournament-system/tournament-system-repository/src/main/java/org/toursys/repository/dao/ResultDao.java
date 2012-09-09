package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Result;

public interface ResultDao {

    public Result createResult(Result result);

    public void updateResult(Result result);

    public void deleteResult(Result result);

    public List<Result> getAllResult();
}
