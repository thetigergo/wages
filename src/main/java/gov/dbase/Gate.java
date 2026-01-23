
package gov.dbase;

/**
 *
 * @author felix
 */
public enum Gate {
    PGSQL("postgres"),
    PGPWD("millennium@2000"),
    MSSQL("sa"),
    MSPWD("systemadministrator");

    private final String trial;
    
    private Gate(String test) {trial = test;}

    protected String salt() {return trial;}
}
