/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.enums;

/**
 * Fire or Perform an action to a client-server database
 * for posting data.
 *
 * @author Felix E. Rendon
 */
public enum Fire {
    /**
     * Perform an Insert by sql statement
     */
    doInsert,

    /**
     * Perform an Update by sql statement
     */
    doUpdate,

    /**
     * Perform a Select by sql statement
     */
    doSelect,

    /**
     * Perform a Delete statement
     */
    doDelete
}
