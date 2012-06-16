/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 *
 * @author nitinv
 */
public class JDBCHelper {

    private static final Log LOGGER = LogFactory.getLog(JDBCHelper.class);

    public static Map<String, Object> executeQuery(final String sql, final DataSource dataSource) throws SQLException {
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        Connection connection = DataSourceUtils.getConnection(dataSource);

        LOGGER.info("connection [" + sql + "] " + connection);

        try {
            executeQuery(sql, connection, resultMap);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }


        return resultMap;
    }

    public static void executeQuery(final String sql, final Connection connection, final Map<String, Object> resultMap) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            final ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
            LOGGER.info("ResultSetMetaData  [" + resultSetMetaData + "]");
            final int columnCount = resultSetMetaData.getColumnCount();
            LOGGER.info("columnCount [" + columnCount + "]");
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    LOGGER.info(resultSetMetaData.getColumnLabel(i) + " -- " + resultSet.getObject(i));
                    resultMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                }
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static void execute(final String sql, final DataSource dataSource) throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);

        LOGGER.info("connection [" + sql + "] [" + connection + "]");
        LOGGER.info("connection AutoCommit [" + connection.getAutoCommit() + "]");

        try {
            execute(sql, connection);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }

    }

    public static void execute(final String sql, final Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
