package app;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgraham on 4/13/17.
 */

public class DataFoundObject implements mysql_credentials {

    private String assetTag;
    private String serialNumber;
    private String customerName;
    private String driveState;
    private String driveLocation;
    private String tableName;
    private String essential;

    private String eMessage = "";
    private String searchResult = "";

    public DataFoundObject(String assetTag, String serialNumber, String customerName,
                           String driveState, String driveLocation, String tableName, String essential) {
        this.assetTag = assetTag;
        this.serialNumber = serialNumber;
        this.customerName = customerName;
        this.driveState = driveState;
        this.driveLocation = driveLocation;
        this.tableName = tableName;
        this.essential = essential;
    }

    public String getErrorMessage() {
        return eMessage;
    }

    public String getData() {
        return searchResult;
    }

    public boolean foundDataSuccessfully() {
        boolean result = false;
        assetTag.trim();
        serialNumber.trim();
        customerName.trim();
        driveState.trim();
        driveLocation.trim();
        essential.trim();

        Connection connect = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

            String query_searchDrive = "";

            if ((assetTag.equalsIgnoreCase(null) || assetTag.equalsIgnoreCase(""))
                    && (serialNumber.equalsIgnoreCase(null) || serialNumber.equalsIgnoreCase(""))
                    && (customerName.equalsIgnoreCase(null) || customerName.equalsIgnoreCase(""))
                    && (driveState.equalsIgnoreCase(null) || driveState.equalsIgnoreCase(""))
                    && (driveLocation.equalsIgnoreCase(null) || driveLocation.equalsIgnoreCase(""))
                    && (essential.equalsIgnoreCase(null) || essential.equalsIgnoreCase(""))) {
                //get all drives except the ones returned to customer
                query_searchDrive = "select * from " + tableName +
                        " where drive_state <> 'Import Complete / Return Media to Customer' " +
                        "and drive_state <> 'Project Closed / Media Returned' " +
                        "order by last_updated desc;";
            }
            else {
                query_searchDrive = "select * from " + tableName + " where";

                if (!(assetTag.equalsIgnoreCase(null) || assetTag.equalsIgnoreCase("")))
                    query_searchDrive += " pp_asset_tag like '%" + assetTag + "%'";

                if (!(serialNumber.equalsIgnoreCase(null) || serialNumber.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " serial_number like '%" + serialNumber + "%'";
                    else
                        query_searchDrive += " and serial_number like '%" + serialNumber + "%'";
                }

                if (!(customerName.equalsIgnoreCase(null) || customerName.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " customer_name like '%" + customerName + "%'";
                    else
                        query_searchDrive += " and customer_name like '%" + customerName + "%'";
                }

                if (!(driveState.equalsIgnoreCase(null) || driveState.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " drive_state = '" + driveState + "'";
                    else
                        query_searchDrive += " and drive_state = '" + driveState + "'";
                }

                if (!(driveLocation.equalsIgnoreCase(null) || driveLocation.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " drive_location = '" + driveLocation + "'";
                    else
                        query_searchDrive += " and drive_location = '" + driveLocation + "'";
                }

                if (!(essential.equalsIgnoreCase(null) || essential.equalsIgnoreCase(""))) {
                    if (query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " essential = '" + essential + "'";
                    else
                        query_searchDrive += " and essential = '" + essential + "'";
                }

                query_searchDrive += " order by last_updated desc;";
            }

            System.out.println("Search drive: " + query_searchDrive);

            PreparedStatement prepSearchDriveStmt = connect.prepareStatement(query_searchDrive);
            ResultSet rs = prepSearchDriveStmt.executeQuery();

            result = true;

            while(rs.next()){
                Map<String, String> map = new HashMap<String, String>();

                map.put("pp_asset_tag", rs.getString("pp_asset_tag"));
                map.put("manufacturer", rs.getString("manufacturer_model"));
                map.put("serial_number", rs.getString("serial_number"));
                map.put("property", rs.getString("property"));
                map.put("label", rs.getString("label"));
                map.put("drive_state", rs.getString("drive_state"));
                map.put("drive_location", rs.getString("drive_location"));
                map.put("return_media_to_customer", rs.getString("return_media_to_customer"));
                map.put("cts", rs.getString("cts"));
                map.put("jira", rs.getString("jira"));
                map.put("customer_name", rs.getString("customer_name"));
                map.put("encrypted", rs.getString("encrypted"));
                map.put("box", rs.getString("box"));
                map.put("usb", rs.getString("usb"));
                map.put("power", rs.getString("power"));
                map.put("rack", rs.getString("rack"));
                map.put("shelf", rs.getString("shelf"));
                map.put("notes", rs.getString("notes"));
                map.put("received_date", rs.getString("received_date"));
                map.put("sent_date", rs.getString("sent_date"));
                map.put("shipping_carrier_sent", rs.getString("shipping_carrier_sent"));
                map.put("shipping_tracking_number_sent", rs.getString("shipping_tracking_number_sent"));
                map.put("created", rs.getTimestamp("created").toString());
                map.put("last_updated", rs.getTimestamp("last_updated").toString());
                map.put("updated_by", rs.getString("updated_by"));
                map.put("essential", rs.getString("essential"));
                list.add(map);
            }

            int totalMatches = list.size();

            JSONObject json = new JSONObject();
            json.accumulate("totalMatches", totalMatches);
            json.accumulate("drives", list);

            searchResult = json.toString();

            rs.close();
            prepSearchDriveStmt.close();

        } catch(SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if(connect != null)
                    connect.close();
            } catch(SQLException se) {
                eMessage = se.getMessage();
                se.printStackTrace();
            }
        }
        return result;
    }
}
