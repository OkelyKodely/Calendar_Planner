/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dhcho
 */
public class JDataBase implements DB {
    private Connection connection;
    private Statement stmt;
    public JDataBase(){}
    public Statement getStmt() {return this. stmt;}
    public Connection getConnection() {return this.connection;}
    public void createConnectionToDataBase() {
        try {

            String hostName = "localhost";
            String dbName = "calendar";
            String userName = "root";
            String passWord = "";

            String url = "jdbc:mysql://" + hostName + ":3320/" + dbName + "?user=" + userName + "&password=" + passWord;
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();

        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
