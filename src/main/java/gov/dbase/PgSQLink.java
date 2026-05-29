package gov.dbase;

import com.zaxxer.hikari.*;

import java.sql.Connection;

public class PgSQLink {
    // Map to keep track of a separate pool for each database
    private static final java.util.Map<String, HikariDataSource> pools = new java.util.HashMap<>();
    private static final String SERVER = "172.16.0.14"; 
//    private static final String SERVER = "27.110.189.51";

    // 1. PLACE THE STATIC BLOCK HERE
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Cleaning up database pools...");
            shutdown();
        }));
    }
    
    public static synchronized Connection dbLink(String dbName) throws Exception {
        if (!pools.containsKey(dbName) || pools.get(dbName).isClosed()) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:edb://" + SERVER + ":5432/" + dbName);  // 54321
            config.setDriverClassName("com.edb.Driver"); // Changed class name
            config.setUsername(Gate.PGSQL.salt());
            config.setPassword(Gate.PGPWD.salt());

            // Allow at least 2 connections if you plan to open them at once
            config.setMaximumPoolSize(2);

            pools.put(dbName, new HikariDataSource(config));
        }
        return pools.get(dbName).getConnection();
    }

    // Default connection
    public static Connection dbLink() throws Exception {
        return dbLink("joborders");
    }
    
    public static synchronized void shutdown() {
        // Iterate through all open pools in the map
        for (String dbName : pools.keySet()) {
            HikariDataSource ds = pools.get(dbName);
            if (ds != null && !ds.isClosed()) ds.close(); // Closes all connections for this specific database pool
        }
        pools.clear(); // Empty the map for safety
    }

    private PgSQLink(){}
}