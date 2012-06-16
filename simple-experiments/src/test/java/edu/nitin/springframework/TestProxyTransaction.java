/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.springframework;

import edu.nitin.springframework.database.JDBCHelper;
import java.sql.SQLException;
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
public class TestProxyTransaction extends AbstractDatabaseTest {

    private static final Log LOGGER = LogFactory.getLog(TestProxyTransaction.class);

    @Test
    public void test() throws SQLException {
        LOGGER.info("Method test start");
        LOGGER.info( "getAutowireCapableBeanFactory [" +  context.getAutowireCapableBeanFactory()  + "]");
        final TxInterface txInterface = TxInterface.class.cast(context.getBean(TxInterface.class.getName()));
        Throwable got = null;
        try {
            txInterface.transactionA();
        } catch (final Throwable throwable) {
            got = throwable;
            LOGGER.error("FAILED", throwable);
        } finally {
            final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));
            final Map<String, Object> resultMap = JDBCHelper.executeQuery("select name from test", dataSource);
            AssertJUnit.assertTrue(resultMap.isEmpty());
        }
        AssertJUnit.assertNotNull(got);
        LOGGER.info("Method test end");
    }

    @Override
    protected Resource[] getResource() {
        return new Resource[] {new ClassPathResource("database-proxy.xml")};
    }

}
