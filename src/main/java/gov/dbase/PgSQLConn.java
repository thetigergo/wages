package gov.dbase;

/**
 *
 * @author felix
 */
public class PgSQLConn implements org.postgresql.core.BaseConnection {

    private final org.postgresql.core.BaseConnection mJDBC;
    private final String[] SERVER = {"172.16.0.14"};
//    private final String[] SERVER = {"localhost"};

    static {
        System.out.println("initializing...");
    }

    public PgSQLConn() throws java.sql.SQLException /*, ClassNotFoundException, InstantiationException, IllegalAccessException*/ {
        org.postgresql.ds.common.BaseDataSource pg = new org.postgresql.ds.PGSimpleDataSource();
        pg.setServerNames(SERVER);
        //pg.setPortNumber(5432);
        pg.setDatabaseName("joborders");
        pg.setUser(Gate.PGSQL.salt());
        pg.setPassword(Gate.PGPWD.salt());
        //pg.setSsl(true);
        mJDBC = (org.postgresql.core.BaseConnection)pg.getConnection();
    }

    public PgSQLConn(String database) throws java.sql.SQLException /*, ClassNotFoundException, InstantiationException, IllegalAccessException*/ {
        org.postgresql.ds.common.BaseDataSource pg = new org.postgresql.ds.PGSimpleDataSource();
        pg.setServerNames(SERVER);
        //pg.setPortNumber(5432);
        pg.setDatabaseName(database);
        pg.setUser("postgres");
        pg.setPassword("millennium@2000");
        //pg.setSsl(true);
        mJDBC = (org.postgresql.core.BaseConnection)pg.getConnection();
    }

    @Override
    public void cancelQuery() throws java.sql.SQLException {
        mJDBC.cancelQuery();
    }

    @Override
    public java.sql.ResultSet execSQLQuery(String string) throws java.sql.SQLException {
        return mJDBC.execSQLQuery(string);
    }

    @Override
    public java.sql.ResultSet execSQLQuery(String string, int i, int i1) throws java.sql.SQLException {
        return mJDBC.execSQLQuery(string, i, i1);
    }

    @Override
    public void execSQLUpdate(String string) throws java.sql.SQLException {
        mJDBC.execSQLUpdate(string);
    }

    @Override
    public org.postgresql.core.QueryExecutor getQueryExecutor() {
        return mJDBC.getQueryExecutor();
    }

    @Override
    public Object getObject(String string, String string1, byte[] bytes) throws java.sql.SQLException {
        return mJDBC.getObject(string, string1, bytes);
    }

    @Override
    public org.postgresql.core.Encoding getEncoding() throws java.sql.SQLException {
        return mJDBC.getEncoding();
    }

    @Override
    public org.postgresql.core.TypeInfo getTypeInfo() {
        return mJDBC.getTypeInfo();
    }

    @Override
    public byte[] encodeString(String string) throws java.sql.SQLException {
        return mJDBC.encodeString(string);
    }

    @Override
    public String escapeString(String string) throws java.sql.SQLException {
        return mJDBC.escapeString(string);
    }

    @Override
    public boolean getStandardConformingStrings() {
        return mJDBC.getStandardConformingStrings();
    }

    @Override
    public boolean getStringVarcharFlag() {
        return mJDBC.getStringVarcharFlag();
    }


    @Override
    public org.postgresql.PGNotification[] getNotifications() throws java.sql.SQLException {
        return mJDBC.getNotifications();
    }

    //@Override
//    public org.postgresql.copy.CopyManager getCopyAPI() throws java.sql.SQLException {
//        return getCopyAPI();
//    }

    @Override
    public org.postgresql.largeobject.LargeObjectManager getLargeObjectAPI() throws java.sql.SQLException {
        return mJDBC.getLargeObjectAPI();
    }

    @Override
    @Deprecated
    public org.postgresql.fastpath.Fastpath getFastpathAPI() throws java.sql.SQLException {
        return mJDBC.getFastpathAPI();
    }

    @Override
    @Deprecated
    public void addDataType(String string, String string1) {
        mJDBC.addDataType(string, string1);
    }

    @Override
    public void addDataType(String string, Class<? extends org.postgresql.util.PGobject> type) throws java.sql.SQLException {
        mJDBC.addDataType(string, type);
    }

    @Override
    public void setPrepareThreshold(int ent) {
        mJDBC.setPrepareThreshold(ent);
    }

    @Override
    public int getPrepareThreshold() {
        return mJDBC.getPrepareThreshold();
    }

    @Override
    public java.sql.Statement createStatement() throws java.sql.SQLException {
        return mJDBC.createStatement();
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws java.sql.SQLException {
        return mJDBC.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws java.sql.SQLException {
        mJDBC.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws java.sql.SQLException {
        return mJDBC.getAutoCommit();
    }

    @Override
    public void commit() throws java.sql.SQLException {
        mJDBC.commit();
    }

    @Override
    public void rollback() throws java.sql.SQLException {
        mJDBC.rollback();
    }

    @Override
    public void close() throws java.sql.SQLException {
        mJDBC.close();
    }

    @Override
    public boolean isClosed() throws java.sql.SQLException {
        return mJDBC.isClosed();
    }

    @Override
    public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
        return mJDBC.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws java.sql.SQLException {
        mJDBC.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws java.sql.SQLException {
        return mJDBC.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws java.sql.SQLException {
        mJDBC.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws java.sql.SQLException {
        return mJDBC.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws java.sql.SQLException {
        mJDBC.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws java.sql.SQLException {
        return mJDBC.getTransactionIsolation();
    }

    @Override
    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
        return mJDBC.getWarnings();
    }

    @Override
    public void clearWarnings() throws java.sql.SQLException {
        mJDBC.clearWarnings();
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public java.util.Map<String, Class<?>> getTypeMap() throws java.sql.SQLException {
        return mJDBC.getTypeMap();
    }

    @Override
    public void setTypeMap(java.util.Map<String, Class<?>> map) throws java.sql.SQLException {
        mJDBC.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws java.sql.SQLException {
        mJDBC.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws java.sql.SQLException {
        return mJDBC.getHoldability();
    }

    @Override
    public java.sql.Savepoint setSavepoint() throws java.sql.SQLException {
        return mJDBC.setSavepoint();
    }

    @Override
    public java.sql.Savepoint setSavepoint(String name) throws java.sql.SQLException {
        return mJDBC.setSavepoint(name);
    }

    @Override
    public void rollback(java.sql.Savepoint savepoint) throws java.sql.SQLException {
        mJDBC.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(java.sql.Savepoint savepoint) throws java.sql.SQLException {
        mJDBC.releaseSavepoint(savepoint);
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, columnIndexes);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, columnNames);
    }

    @Override
    public java.sql.Clob createClob() throws java.sql.SQLException {
        return mJDBC.createClob();
    }

    @Override
    public java.sql.Blob createBlob() throws java.sql.SQLException {
        return mJDBC.createBlob();
    }

    @Override
    public java.sql.NClob createNClob() throws java.sql.SQLException {
        return mJDBC.createNClob();
    }

    @Override
    public java.sql.SQLXML createSQLXML() throws java.sql.SQLException {
        return mJDBC.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws java.sql.SQLException {
        return mJDBC.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws java.sql.SQLClientInfoException {
        mJDBC.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(java.util.Properties properties) throws java.sql.SQLClientInfoException {
        mJDBC.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws java.sql.SQLException {
        return mJDBC.getClientInfo(name);
    }

    @Override
    public java.util.Properties getClientInfo() throws java.sql.SQLException {
        return mJDBC.getClientInfo();
    }

    @Override
    public java.sql.Array createArrayOf(String typeName, Object[] elements) throws java.sql.SQLException {
        return mJDBC.createArrayOf(typeName, elements);
    }

    @Override
    public java.sql.Struct createStruct(String typeName, Object[] attributes) throws java.sql.SQLException {
        return mJDBC.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws java.sql.SQLException {
        mJDBC.setSchema(schema);
    }

    @Override
    public String getSchema() throws java.sql.SQLException {
        return mJDBC.getSchema();
    }

    @Override
    public void abort(java.util.concurrent.Executor executor) throws java.sql.SQLException {
        mJDBC.abort(executor);
    }

    @Override
    public void setNetworkTimeout(java.util.concurrent.Executor executor, int milliseconds) throws java.sql.SQLException {
        mJDBC.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws java.sql.SQLException {
        return mJDBC.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
        return mJDBC.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
        return mJDBC.isWrapperFor(iface);
    }

    @Override
    public org.postgresql.copy.CopyManager getCopyAPI() throws java.sql.SQLException {
        return mJDBC.getCopyAPI();
    }

    @Override
    public boolean binaryTransferSend(int i) {
        return mJDBC.binaryTransferSend(i);
    }

    @Override
    public boolean haveMinimumServerVersion(int i) {
        return mJDBC.haveMinimumServerVersion(i);
    }

    @Override
    public boolean haveMinimumServerVersion(org.postgresql.core.Version vrsn) {
        return mJDBC.haveMinimumServerVersion(vrsn);
    }

    @Override
    @Deprecated
    public org.postgresql.jdbc.TimestampUtils getTimestampUtils() {
        return mJDBC.getTimestampUtils();
    }

    @Override
    public boolean isColumnSanitiserDisabled() {
        return mJDBC.isColumnSanitiserDisabled();
    }

    @Override
    public void addTimerTask(java.util.TimerTask tt, long l) {
        mJDBC.addTimerTask(tt, l);
    }

    @Override
    public void purgeTimerTasks() {
        mJDBC.purgeTimerTasks();
    }

    @Override
    public org.postgresql.util.LruCache<org.postgresql.jdbc.FieldMetadata.Key, org.postgresql.jdbc.FieldMetadata> getFieldMetadataCache() {
        return mJDBC.getFieldMetadataCache();
    }

    @Override
    public void setDefaultFetchSize(int i) throws java.sql.SQLException {
        mJDBC.setDefaultFetchSize(i);
    }

    @Override
    public int getDefaultFetchSize() {
        return mJDBC.getDefaultFetchSize();
    }

    @Override
    public int getBackendPID() {
        return mJDBC.getBackendPID();
    }

    @Override
    public String escapeIdentifier(String string) throws java.sql.SQLException {
        return mJDBC.escapeIdentifier(string);
    }

    @Override
    public String escapeLiteral(String string) throws java.sql.SQLException {
        return mJDBC.escapeLiteral(string);
    }

    @Override
    public org.postgresql.core.ReplicationProtocol getReplicationProtocol() {
        return mJDBC.getReplicationProtocol();
    }

    @Override
    public java.util.logging.Logger getLogger() {
        return mJDBC.getLogger();
    }

    @Override
    public org.postgresql.core.TransactionState getTransactionState() {
        return mJDBC.getTransactionState();
    }

    @Override
    public org.postgresql.core.CachedQuery createQuery(String string, boolean bln, boolean bln1, String... strings) throws java.sql.SQLException {
        return mJDBC.createQuery(string, bln, bln1, strings);
    }

    @Override
    public void setFlushCacheOnDeallocate(boolean bln) {
        mJDBC.setFlushCacheOnDeallocate(bln);
    }

    @Override
    public java.sql.Array createArrayOf(String string, Object o) throws java.sql.SQLException {
        return mJDBC.createArrayOf(string, o);
    }

    @Override
    public org.postgresql.PGNotification[] getNotifications(int i) throws java.sql.SQLException {
        return mJDBC.getNotifications(i);
    }

    @Override
    public org.postgresql.jdbc.PreferQueryMode getPreferQueryMode() {
        return mJDBC.getPreferQueryMode();
    }

    @Override
    public org.postgresql.jdbc.AutoSave getAutosave() {
        return mJDBC.getAutosave();
    }

    @Override
    public void setAutosave(org.postgresql.jdbc.AutoSave as) {
        mJDBC.setAutosave(as);
    }

    @Override
    public org.postgresql.replication.PGReplicationConnection getReplicationAPI() {
        return mJDBC.getReplicationAPI();
    }

    @Override
    public java.util.Map<String, String> getParameterStatuses() {
        return mJDBC.getParameterStatuses();
    }

    @Override
    public String getParameterStatus(String string) {
        return mJDBC.getParameterStatus(string);
    }

    @Override
    public boolean hintReadOnly() {
        return mJDBC.hintReadOnly();
    }

    @Override
    public org.postgresql.xml.PGXmlFactoryFactory getXmlFactoryFactory() throws java.sql.SQLException {
        return mJDBC.getXmlFactoryFactory();
    }

    @Override
    public boolean getLogServerErrorDetail() {
        return mJDBC.getLogServerErrorDetail();
    }

    @Override
    public void setAdaptiveFetch(boolean bln) {
        mJDBC.setAdaptiveFetch(bln);
    }

    @Override
    public boolean getAdaptiveFetch() {
        return mJDBC.getAdaptiveFetch();
    }

    @Override
    public void beginRequest() throws java.sql.SQLException {
        mJDBC.beginRequest();
    }

    @Override
    public void endRequest() throws java.sql.SQLException {
        mJDBC.endRequest();
    }

    @Override
    public boolean setShardingKeyIfValid(java.sql.ShardingKey shardingKey, java.sql.ShardingKey superShardingKey, int timeout) throws java.sql.SQLException {
        return mJDBC.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
    }

    @Override
    public boolean setShardingKeyIfValid(java.sql.ShardingKey shardingKey, int timeout) throws java.sql.SQLException {
        return mJDBC.setShardingKeyIfValid(shardingKey, timeout);
    }

    @Override
    public void setShardingKey(java.sql.ShardingKey shardingKey, java.sql.ShardingKey superShardingKey) throws java.sql.SQLException {
        org.postgresql.core.BaseConnection.super.setShardingKey(shardingKey, superShardingKey);
        mJDBC.setShardingKey(shardingKey, superShardingKey);
    }

    @Override
    public void setShardingKey(java.sql.ShardingKey shardingKey) throws java.sql.SQLException {
        //org.postgresql.core.BaseConnection.super.setShardingKey(shardingKey);
        mJDBC.setShardingKey(shardingKey);
    }
}
