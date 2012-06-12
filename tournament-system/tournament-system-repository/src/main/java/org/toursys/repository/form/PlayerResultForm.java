package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Table;

@Pojo
public class PlayerResultForm {

	private Table table;

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public PlayerResultForm(Table table) {
		this.table = table;
	}
}
