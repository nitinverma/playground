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
import org.testng.annotations.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.testng.AssertJUnit;

/**
 *
 * @author nitinv
 */
public class TestDataSourceTransactionManager extends AbstractDatabaseTest {

    private static final Log LOGGER = LogFactory.getLog(TestDataSourceTransactionManager.class);

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
            final Map<String, Object> resultMap = JDBCHelper.executeQuery("select name from test", dataSource);
            AssertJUnit.assertTrue(resultMap.isEmpty());
        }
        AssertJUnit.assertNotNull(got);
        LOGGER.info("Method test end");
    }

    public void tf0() {
        LOGGER.info("Method tf0 start");
        final PlatformTransactionManager platformTransactionManager = PlatformTransactionManager.class.cast(context.getBean("transactionManager"));
        final TransactionStatus transactionStatus = platformTransactionManager.getTransaction(
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            LOGGER.info("transactionStatus.isNewTransaction:- " + transactionStatus.isNewTransaction());
            AssertJUnit.assertTrue(transactionStatus.isNewTransaction());
            LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
            tf1();
            tf1();
        } catch (final Throwable throwable) {
            LOGGER.info("tf0 Exception ", throwable);
            LOGGER.info("transactionStatus.isRollbackOnly:- " + transactionStatus.isRollbackOnly());
            LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
            if (transactionStatus.isNewTransaction()) {
                platformTransactionManager.rollback(transactionStatus);
            }
            throw new RuntimeException(throwable);
        } finally {
            LOGGER.info("transactionStatus.isRollbackOnly:- " + transactionStatus.isRollbackOnly());
            LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
        }
        platformTransactionManager.commit(transactionStatus);
        LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
        LOGGER.info("Method tf0 end");
    }

    public void tf1() {
        LOGGER.info("Method tf1 start");
        final PlatformTransactionManager platformTransactionManager = PlatformTransactionManager.class.cast(context.getBean("transactionManager"));
        final TransactionStatus transactionStatus = platformTransactionManager.getTransaction(
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        final DataSource dataSource = DataSource.class.cast(context.getBean("dataSource"));
        try {
            LOGGER.info("transactionStatus.isNewTransaction:- " + transactionStatus.isNewTransaction());
            AssertJUnit.assertTrue(!transactionStatus.isNewTransaction());

            JDBCHelper.execute("insert into test (name) values ('nitin')", dataSource);
            JDBCHelper.executeQuery("select name from test", dataSource);

        } catch (final Throwable throwable) {
            LOGGER.info("tf1 Exception ", throwable);
            // DO NOT WANT TO MARK FOR ROLLBACK
            if (transactionStatus.isNewTransaction()) {
                platformTransactionManager.rollback(transactionStatus);
            }
            LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
            throw new RuntimeException(throwable);
        }
        platformTransactionManager.commit(transactionStatus);
        LOGGER.info("transactionStatus.isCompleted:- " + transactionStatus.isCompleted());
        LOGGER.info("Method tf1 end");
    }

    @Override
    protected Resource[] getResource() {
        return new Resource[]{new ClassPathResource("database.xml")};
    }
}
