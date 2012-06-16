/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.springframework;

import edu.nitin.springframework.database.JDBCHelper;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author nitinv
 */
public class TxClass2 implements TxInterface {

    private DataSource dataSource;

    public void transactionA() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void transactionB(final String name) throws SQLException {
        JDBCHelper.execute("insert into test (name) values ('"+name+"')", getDataSource());
        JDBCHelper.executeQuery("select name from test", getDataSource());
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
