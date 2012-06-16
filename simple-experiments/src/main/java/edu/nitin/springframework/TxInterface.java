/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework;

import java.sql.SQLException;

/**
 *
 * @author nitinv
 */
public interface TxInterface {

    public void transactionA() throws SQLException;

    public void transactionB(final String name) throws SQLException;
}
