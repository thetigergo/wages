package gov.dbase;

import com.zaxxer.hikari.*;

import java.sql.Connection;
//import javax.annotation.ManagedBean;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Named;

//@Named("pgDBlink")   // this is the bean name you’ll reference in EL --> CDI Style (preferred in modern Jakarta EE)
//@ManagedBean("pgDBlink")   // bean name for EL -> older but valid
//@ApplicationScoped
public class PgDBbind implements java.io.Serializable {
    // Map to keep track of a separate pool for each database
    private static final java.util.Map<String, HikariDataSource> pools = new java.util.HashMap<>();
    private static final String SERVER = "172.16.0.14"; 
//    private static final String SERVER = "27.110.189.51";

    public synchronized Connection dbLink(String dbName) throws Exception {
        if (!pools.containsKey(dbName) || pools.get(dbName).isClosed()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:edb://" + SERVER + ":5432/" + dbName);  // 54321
            config.setDriverClassName("com.edb.Driver"); // Changed class name
            config.setUsername(Gate.PGSQL.salt());
            config.setPassword(Gate.PGPWD.salt());

            // Allow at least 2 connections if you plan to open them at once
            config.setMaximumPoolSize(99);

            pools.put(dbName, new HikariDataSource(config));
        }
        return pools.get(dbName).getConnection();
    }

    // Default connection
    public Connection dbLink() throws Exception {
        return dbLink("joborders");
    }
}