/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework;

import edu.nitin.springframework.database.JDBCHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 *
 * @author nitinv
 */
public abstract class AbstractDatabaseTest {

    private static final Log LOGGER = LogFactory.getLog(AbstractDatabaseTest.class);
    protected final GenericApplicationContext context = new GenericApplicationContext();

    protected abstract Resource[] getResource();

    @BeforeClass
    public void setup() throws SQLException {
        LOGGER.info("Method setup start");
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        xmlBeanDefinitionReader.loadBeanDefinitions(getResource());
        context.refresh();

        
        final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));

        Throwable got = null;
        try {
            JDBCHelper.executeQuery("select count(1) mycount from test", dataSource);
        } catch (final Throwable th) {
            got = th;
        }

        if (got == null) {
            LOGGER.warn("cleaning needed");
            destroy0(dataSource);
        }

        setup0(dataSource);
        LOGGER.info("Method setup end");
    }

    protected void setup0(final DataSource dataSource) throws SQLException {
        final StringBuilder createString = new StringBuilder();
        createString.append("CREATE TABLE TEST");
        createString.append("(");
        createString.append("NAME VARCHAR(32) NOT NULL CONSTRAINT NAME_PK PRIMARY KEY");
        createString.append(")");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(createString.toString());
            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @AfterClass
    public void destroy() throws SQLException {
        LOGGER.info("Method destroy start");
        final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));
        destroy0(dataSource);
        LOGGER.info("Method destroy end");
    }

    protected void destroy0(final DataSource dataSource) throws SQLException {
        final StringBuilder createString = new StringBuilder();
        createString.append("DROP TABLE TEST");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(createString.toString());
            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
