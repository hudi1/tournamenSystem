package org.tahom.processor;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.ext.mysql.MySqlMetadataHandler;

public class MySqlCustomMetadataHandler extends MySqlMetadataHandler {

    @Override
    public ResultSet getColumns(DatabaseMetaData databaseMetaData, String schemaName, String tableName)
            throws SQLException {
        return super.getColumns(databaseMetaData, schemaName, tableName.toUpperCase());
    }

    @Override
    public boolean matches(ResultSet resultSet, String schema, String table, boolean caseSensitive) throws SQLException {
        return super.matches(resultSet, schema, table, caseSensitive);
    }

    @Override
    public boolean matches(ResultSet columnsResultSet, String catalog, String schema, String table, String column,
            boolean caseSensitive) throws SQLException {
        return super.matches(columnsResultSet, catalog, schema, table, column, caseSensitive);
    }

    @Override
    public String getSchema(ResultSet resultSet) throws SQLException {
        return super.getSchema(resultSet);
    }

    @Override
    public boolean tableExists(DatabaseMetaData metaData, String schema, String tableName) throws SQLException {
        return super.tableExists(metaData, schema, tableName.toUpperCase());
    }

    @Override
    public ResultSet getTables(DatabaseMetaData metaData, String schemaName, String[] tableType) throws SQLException {
        return super.getTables(metaData, schemaName, tableType);
    }

    @Override
    public ResultSet getPrimaryKeys(DatabaseMetaData metaData, String schemaName, String tableName) throws SQLException {
        return super.getPrimaryKeys(metaData, schemaName, tableName.toUpperCase());
    }

}
