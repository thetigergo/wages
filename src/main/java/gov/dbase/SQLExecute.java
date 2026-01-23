/*
 * SQLExecute.java
 *
 * Created on August 1, 2007, 9:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.dbase;


/**
 * Setup a query statement to a single table.
 * @author thetiger
 */
public class SQLExecute {

    java.util.ArrayList<PropertyHolder> holder = new java.util.ArrayList<>();
    
    private final String TableName;

    /** 
     * Creates a new instance of SQLExecute with a table name specification
     * @param table
     */
    public SQLExecute(String table) {TableName = table;}
    
    /**
     * Specify a field name and its behavior.
     * @param fieldname Field name of a single table.
     * @param numeric Identify a type of this field name by true or false.
     * @param activity Activity taken when sent to a table.
     * @param setValue Set an initial value if needed, others put an empty
     * string if it is string or zero (0) for numeric.
     */
    public void FieldName(String fieldname, boolean numeric, gov.enums.Take activity, Object setValue) {
        FieldName(fieldname, numeric, activity, false, String.valueOf(setValue));
    }

    /**
     * Specify a field name and its behavior.
     * @param fieldname Field name of a single table.
     * @param numeric Identify a type of this field name by true or false.
     * @param activity Activity taken when sent to a table.
     * @param condition Put an optional value by true or false if certain condition is necessary.
     * @param setValue Set an initial value if needed, others put an empty
     * string if it is string or zero (0) for numeric.
     */
    public void FieldName(String fieldname, boolean numeric, gov.enums.Take activity, boolean condition, Object setValue) {
        PropertyHolder property =  new PropertyHolder(fieldname, numeric, activity, condition, setValue);
        holder.add(property);
    }
    
    public String Perform(gov.enums.Fire action) {
        String sqlStr = "", temp = "";
        switch (action) {
            case doInsert:
                for (int arrlen = 0; arrlen < holder.size(); arrlen++) {
                    if ((holder.get(arrlen).getActivity() == gov.enums.Take.InsertUpdate) ||
                        (holder.get(arrlen).getActivity() == gov.enums.Take.InsertOnly))
                        if (!holder.get(arrlen).getValueSet().isEmpty()) {
                            sqlStr += holder.get(arrlen).getFieldName() + ", ";
                            temp += holder.get(arrlen).getValueSet() + ", ";
                        }
                }
                sqlStr = sqlStr.substring(0, sqlStr.length() - 2) + ") VALUES(";
                sqlStr = "INSERT INTO " + this.TableName + "(" + sqlStr + temp.substring(0, temp.length() - 2) + ")";
                break;

            case doUpdate:
                for (int arrlen = 0; arrlen < holder.size(); arrlen++) {
                    if ((holder.get(arrlen).getActivity() == gov.enums.Take.InsertUpdate) ||
                        (holder.get(arrlen).getActivity() == gov.enums.Take.UpdateOnly)) {
                        if (!holder.get(arrlen).getValueSet().isEmpty())
                            sqlStr +=  holder.get(arrlen).getFieldName() + " = " + holder.get(arrlen).getValueSet() + ", ";
                    }
                    if (holder.get(arrlen).getCondtition()) 
                        temp += "(" + holder.get(arrlen).getFieldName() + " = " + holder.get(arrlen).getValueSet() + ") AND ";
                }
                temp = temp.substring(0, temp.length() - 5);
                temp = " WHERE " + temp;
                sqlStr = "UPDATE " + this.TableName + " SET " + sqlStr.substring(0, sqlStr.length() - 2) + temp;
                break;

            case doSelect:
                for (int arrlen = 0; arrlen < holder.size(); arrlen++) {
                    if (holder.get(arrlen).getCondtition()) {
                        temp += "(" + holder.get(arrlen).getFieldName() + " = " + holder.get(arrlen).getValueSet() + ") AND ";
                    }
                }
                temp = temp.substring(0, temp.length() - 5);
                sqlStr = "SELECT * FROM " + this.TableName + " WHERE " + temp;
                break;

            case doDelete:
                for (int arrlen = 0; arrlen < holder.size(); arrlen++) {
                    if (holder.get(arrlen).getCondtition()) {
                        temp += "(" + holder.get(arrlen).getFieldName() + " = " + holder.get(arrlen).getValueSet() + ") AND ";
                    }
                }
                temp = temp.substring(0, temp.length() - 5);
                sqlStr = "DELETE FROM " + this.TableName + " WHERE " + temp;
                break;

//            case actPutTo:
//                for (int arrlen = 0; arrlen < holder.size(); arrlen++) {
//                    if ((holder.get(arrlen).getActivity() == InsertUpdate) || (holder.get(arrlen).getActivity() == InsertOnly))
//                        if (!holder.get(arrlen).getValueSet().isEmpty()) {
//                            sqlStr += holder.get(arrlen).getFieldName() + ", ";
//                            temp += holder.get(arrlen).getValueSet() + ", ";
//                        }
//                }
//                sqlStr = sqlStr.substring(0, sqlStr.length() - 2) + ") SELECT ";
//                sqlStr = "INSERT INTO " + this.TableName + "(" + sqlStr + temp.substring(0, temp.length() - 2) + " ";
//                break;

            default:
                return "";
        }
        return sqlStr;
    }
}
class PropertyHolder {
    final short ConditionOnly = 3;
    
    private String Fieldname = "", Fieldvalue = "";
    private boolean Condition, Numeric;
    private gov.enums.Take Activity;
    
    PropertyHolder (String fieldname, boolean numeric, gov.enums.Take activity, boolean condition, Object setvalue) {
        Fieldname = fieldname;
        Numeric = numeric;
        Activity = activity;
        Condition = condition || (activity == gov.enums.Take.ConditionOnly);
        
        Fieldvalue = (numeric ? "" : "'") + String.valueOf(setvalue) + (numeric ? "" : "'");
    }
    
    public String getFieldName() {return Fieldname; }
    public String getValueSet() {return Fieldvalue;}
    public boolean isNumeric() {return Numeric;}
    public boolean getCondtition() {return Condition;}
    public gov.enums.Take getActivity() {return Activity;}
}