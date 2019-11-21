package se.rampoon.example5.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;


@Component
public class JDBCConnectionPool {

    //@Qualifier("dataSource") //*  <-- This can be used to filter out duplicate implementations e.g. Hikari, alternatively solve it in the pom.xml
    @Autowired
    DataSource dataSource;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;

    /**
     * Get connection from pool
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /** return connection to pool
     *
     * @param con
     */
    public void returnConnection(Connection con) throws SQLException {
        con.close();
    }
}
