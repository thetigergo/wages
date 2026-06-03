package gov.dbase;

import com.zaxxer.hikari.*;
import java.sql.Connection;
import java.util.*;

public class MsSQLink {

//    private static final Map<String, HikariDataSource> pools = new HashMap<>();
//    private static final String SERVER = "172.16.0.13";  
//
//    // 1. PLACE THE STATIC BLOCK HERE
//    static {
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("Cleaning up database pools...");
//            shutdown();
//        }));
//    }
//    
//    public static synchronized Connection dbLink(String dbName) throws Exception {
//        if (!pools.containsKey(dbName) || pools.get(dbName).isClosed()) {
//            HikariConfig config = new HikariConfig();
//            
//            // 1. Connection Settings
//            config.setJdbcUrl("jdbc:sqlserver://" + SERVER + ":1433;databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;");
//            config.setUsername(Gate.MSSQL.salt());
//            config.setPassword(Gate.MSPWD.salt());
//            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//            // 2. Pool Size & Timeouts
//            config.setMaximumPoolSize(10);
//            config.setMinimumIdle(2);
//            config.setIdleTimeout(300000);    // 5 minutes
//            config.setConnectionTimeout(20000); // 20 seconds
//            config.setMaxLifetime(1800000);   // 30 minutes
//
//            // 3. SQL Server Specific Optimizations
//            config.addDataSourceProperty("cachePrepStmts", "true");
//            config.addDataSourceProperty("prepStmtCacheSize", "250");
//            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//
//            pools.put(dbName, new HikariDataSource(config));
//        }
//        return pools.get(dbName).getConnection();
//    }
//
//    /**
//     * Call this to get a connection from the pool.
//     * @return 
//     * @throws java.sql.SQLException
//     */
//    // Default connection
//    public static Connection dbLink() throws Exception {
//        return dbLink("cheque");
//    }
//
//    /**
//     * Call this when the application is shutting down (e.g., in a shutdown hook).
//     */
//    public static synchronized void shutdown() {
//        // Iterate through all open pools in the map
//        for (String dbName : pools.keySet()) {
//            HikariDataSource ds = pools.get(dbName);
//            if (ds != null && !ds.isClosed()) ds.close(); // Closes all connections for this specific database pool
//        }
//        pools.clear(); // Empty the map for safety
//    }
//
//    // Prevent instantiation
//    private MsSQLink() {}
}

