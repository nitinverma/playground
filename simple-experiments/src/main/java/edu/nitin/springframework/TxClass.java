/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework;

import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author nitinv
 */
public class TxClass implements TxInterface {

    private DataSource dataSource;
    private TxInterface tx2;

    public void transactionA() throws SQLException {
        getTx2().transactionB("nitin");
        getTx2().transactionB("nitin");
    }

    public void transactionB(final String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TxInterface getTx2() {
        return tx2;
    }
    
    public void setTx2(TxInterface tx2) {
        this.tx2 = tx2;
    }
}
