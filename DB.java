package db;

import java.sql.Connection;
import java.sql.Statement;

public interface DB {
    
    public void createConnectionToDataBase();
    
    public Connection getConnection();
    
    public Statement getStmt();
}