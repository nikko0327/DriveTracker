package app;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgraham on 4/13/17.
 */

public class DataFoundObject {

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
        assetTag = assetTag.trim();
        serialNumber = serialNumber.trim();
        customerName = customerName.trim();
        driveState = driveState.trim();
        driveLocation = driveLocation.trim();
        essential = essential.trim();

        Connection connect = null;
        PreparedStatement prepSearchDriveStmt = null;
        ResultSet rs = null;

        try {
            connect = db_credentials.DB.getConnection();

            ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

            String query_searchDrive;

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
            } else {
                query_searchDrive = "select * from " + tableName + " where";

                if (!(assetTag.equalsIgnoreCase(null) || assetTag.equalsIgnoreCase("")))
                    query_searchDrive += " pp_asset_tag like '%" + assetTag + "%'";

                if (!(serialNumber.equalsIgnoreCase(null) || serialNumber.equalsIgnoreCase(""))) {
                    if (query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " serial_number like '%" + serialNumber + "%'";
                    else
                        query_searchDrive += " and serial_number like '%" + serialNumber + "%'";
                }

                if (!(customerName.equalsIgnoreCase(null) || customerName.equalsIgnoreCase(""))) {
                    if (query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " customer_name like '%" + customerName + "%'";
                    else
                        query_searchDrive += " and customer_name like '%" + customerName + "%'";
                }

                if (!(driveState.equalsIgnoreCase(null) || driveState.equalsIgnoreCase(""))) {
                    if (query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
                        query_searchDrive += " drive_state = '" + driveState + "'";
                    else
                        query_searchDrive += " and drive_state = '" + driveState + "'";
                }

                if (!(driveLocation.equalsIgnoreCase(null) || driveLocation.equalsIgnoreCase(""))) {
                    if (query_searchDrive.equalsIgnoreCase("select * from " + tableName + " where"))
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

            prepSearchDriveStmt = connect.prepareStatement(query_searchDrive);
            rs = prepSearchDriveStmt.executeQuery();

            result = true;

            while (rs.next()) {
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

        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, rs, prepSearchDriveStmt);
        }
        return result;
    }
}
