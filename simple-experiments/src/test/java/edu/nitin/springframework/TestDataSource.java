/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework;

import edu.nitin.springframework.database.JDBCHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 *
 * @author nitinv
 */
public class TestDataSource extends AbstractDatabaseTest {

    private static final Log LOGGER = LogFactory.getLog(TestDataSource.class);
    
    @Test
    public void test() throws SQLException {

        LOGGER.info("Method test start");
        Throwable got = null;
        try {
            tf0();
        } catch (final Throwable throwable) {
            got = throwable;
        } finally {
            final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));
            final Connection connection = dataSource.getConnection();
            final Map<String, Object> resultMap = new HashMap<String, Object>();
            try {
                JDBCHelper.executeQuery("select name from test", connection, resultMap);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
            AssertJUnit.assertTrue(resultMap.isEmpty());
        }
        AssertJUnit.assertNotNull(got);
        LOGGER.info("Method test end");

    }

    public void tf0() throws SQLException {
        LOGGER.info("Method tf0 start");
        final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));
        final Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        try {
            tf1(connection);
            tf1(connection);
        } catch (final Throwable throwable) {
            LOGGER.info("tf0 Exception ", throwable);
            if (connection != null) {
                connection.rollback();
            }
            throw new RuntimeException(throwable);
        }
        connection.commit();
        LOGGER.info("Method tf0 end");
    }

    public void tf1(final Connection connection) throws SQLException {
        LOGGER.info("Method tf1 start");
        JDBCHelper.execute("insert into test (name) values ('nitin')", connection);
        JDBCHelper.executeQuery("select name from test", connection, new HashMap<String, Object>());
        LOGGER.info("Method tf1 end");
    }

    @Override
    protected Resource[] getResource() {
        return new Resource[] {new ClassPathResource("database.xml")};
    }
}
