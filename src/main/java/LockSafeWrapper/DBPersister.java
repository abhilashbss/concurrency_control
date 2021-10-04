package LockSafeWrapper;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by abhilashbss on 2/10/21.
 */
public class DBPersister {


    private final static Logger logger = Logger.getLogger("concurrency_control");
    public String jdbcUrl;
    public String tableName;
    public String user;
    public String pass;

    public void updateDB(String query) throws Exception{
        Connection conn = DriverManager.getConnection(this.getJdbcUrl(), this.getUser(), this.getPass());
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }

    public boolean ifSelectReturns(String query) throws Exception{
        Connection conn = DriverManager.getConnection(this.getJdbcUrl(), this.getUser(), this.getPass());
        Statement stmt = conn.createStatement();
        ResultSet rs =stmt.executeQuery(query);
        return rs.next();
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
