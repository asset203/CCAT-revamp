package com.asset.ccat.data_migration_service.database.dao;

import java.util.List;

/**
 * @author Assem.Hassan
 */
public interface BaseMigrationWriteDao {

    public void insertData(String tableName, List<Object[]> rows);
}
