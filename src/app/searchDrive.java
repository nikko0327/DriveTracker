package app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class searchDrive
 */
public class searchDrive extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;
    private String searchResult;
    private String eMessage;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDrive() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String assetTag = request.getParameter("pp_asset_tag");
        String serialNumber = request.getParameter("serial_number");
        String customerName = request.getParameter("customer_name");
        String driveState = request.getParameter("drive_state");
        String driveLocation = request.getParameter("drive_location");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if(getSearchResult(assetTag, serialNumber, customerName, driveState, driveLocation))
            response.getWriter().write(searchResult);
        else
            response.getWriter().write(new JSONObject().put("message", eMessage).toString());

        response.flushBuffer();
    }

    public boolean getSearchResult(String assetTag, String serialNumber, String customerName,
                                   String driveState, String driveLocation){

        assetTag.trim();
        serialNumber.trim();
        customerName.trim();
        driveState.trim();
        driveLocation.trim();

        boolean result = false;

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
                    && (driveLocation.equalsIgnoreCase(null) || driveLocation.equalsIgnoreCase(""))) {
                //get all drives except the ones returned to customer
                query_searchDrive = "select * from drive_info " +
                        "where drive_state <> 'Import Complete / Return Media to Customer' " +
                        "and drive_state <> 'Project Closed / Media Returned' " +
                        "order by last_updated desc;";
            }
            else {
                query_searchDrive = "select * from drive_info where";

                if (!(assetTag.equalsIgnoreCase(null) || assetTag.equalsIgnoreCase("")))
                    query_searchDrive += " pp_asset_tag like '%" + assetTag + "%'";

                if (!(serialNumber.equalsIgnoreCase(null) || serialNumber.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from drive_info where"))
                        query_searchDrive += " serial_number like '%" + serialNumber + "%'";
                    else
                        query_searchDrive += " and serial_number like '%" + serialNumber + "%'";
                }

                if (!(customerName.equalsIgnoreCase(null) || customerName.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from drive_info where"))
                        query_searchDrive += " customer_name like '%" + customerName + "%'";
                    else
                        query_searchDrive += " and customer_name like '%" + customerName + "%'";
                }

                if (!(driveState.equalsIgnoreCase(null) || driveState.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from drive_info where"))
                        query_searchDrive += " drive_state = '" + driveState + "'";
                    else
                        query_searchDrive += " and drive_state = '" + driveState + "'";
                }

                if (!(driveLocation.equalsIgnoreCase(null) || driveLocation.equalsIgnoreCase(""))) {
                    if(query_searchDrive.equalsIgnoreCase("select * from drive_info where"))
                        query_searchDrive += " drive_location = '" + driveLocation + "'";
                    else
                        query_searchDrive += " and drive_location = '" + driveLocation + "'";
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
