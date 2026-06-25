package com.englishacademy.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class JdbcHelper {

    private JdbcHelper() {
    }

    public static void setIntOrNull(PreparedStatement ps, int index, int value) throws SQLException {
        if (value == 0) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, value);
        }
    }
}
