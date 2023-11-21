package com.asset.ccat.notification_service.database.dao;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

class DBObject implements SQLData {

    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    private String sql_type = "DB_OBJ";
    private String key;
    private String value;

    public DBObject(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;
        key = stream.readString();
        value = stream.readString();
    }

    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(key);
        stream.writeString(value);
    }
}