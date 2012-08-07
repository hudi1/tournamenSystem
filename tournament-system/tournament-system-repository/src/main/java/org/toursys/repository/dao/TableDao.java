package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.TableForm;
import org.toursys.repository.model.Table;

public interface TableDao {

    public void createTable(Table table);

    public void updateTable(Table table);

    public void deleteTable(Table table);

    public List<Table> findTable(TableForm tableForm);
}
