//package app;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import db_credentials.mysql_credentials;
//
///**
// * Servlet implementation class updateDrive
// */
//public class updateDrive extends HttpServlet implements mysql_credentials {
//	private static final long serialVersionUID = 1L;
//    private String eMessage;
//
//    private String assetTag;
//    private String manufacturer;
//    private String serialNumber;
//    private String property;
//    private String customerName;
//    private String cts;
//    private String jira;
//    private String label;
//    private String driveLocation;
//    private String driveState;
//    private String encrypted;
//    private String box;
//    private String usb;
//    private String power;
//    private String rack;
//    private String shelf;
//    private String notes;
//    private String receivedDate;
//    private String sentDate;
//    private String shippingCarrierSent;
//    private String shippingTrackingNumberSent;
//    private String lastUpdated;
//    private String updatedBy;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public updateDrive() {
//		super();
//	}
//
//	/**
//	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request,
//                          HttpServletResponse response) throws ServletException, IOException {
//
//        assetTag = request.getParameter("pp_asset_tag");
//        manufacturer = request.getParameter("manufacturer");
//        serialNumber = request.getParameter("serial_number");
//        property = request.getParameter("property");
//        customerName = request.getParameter("customer_name");
//        cts = request.getParameter("cts");
//        jira = request.getParameter("jira");
//        label = request.getParameter("label");
//        driveLocation = request.getParameter("drive_location");
//        driveState = request.getParameter("drive_state");
//        encrypted = ""; //request.getParameter("encrypted");
//        box = ""; //request.getParameter("box");
//        usb = request.getParameter("usb");
//        power = request.getParameter("power");
//        rack = ""; //request.getParameter("rack");
//        shelf = ""; //request.getParameter("shelf");
//        notes = request.getParameter("notes");
//        receivedDate = request.getParameter("received_date");
//        sentDate = request.getParameter("sent_date");
//        shippingCarrierSent = request.getParameter("shipping_carrier_sent");
//        shippingTrackingNumberSent = request.getParameter("shipping_tracking_number_sent");
//        updatedBy = request.getParameter("updated_by");
//
//        JSONObject json = new JSONObject();
//
//        if(updateDriveAndAddToHistory()) {
//            json.put("result", "success");
//        }
//        else
//            json.put("result", eMessage);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(json.toString());
//        response.flushBuffer();
//    }
//
//	public boolean updateDriveAndAddToHistory() {
//
//        boolean result = false;
//		Connection connect = null;
//		try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connect = DriverManager.getConnection(db_url, user_name, password);
//
//			Date currentDatetime = new Date();
//            Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
//            lastUpdated = sqlTime.toString();
//
//            String query_updateDrive = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, " +
//                                       "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, " +
//                                       "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, " +
//                                       "received_date = ?, " +
//                                       "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, " +
//                                       "updated_by = ?, last_updated = ?" +
//                                       "where pp_asset_tag = '" + assetTag +"'";
//
//            PreparedStatement prepUpdateDriveStmt = connect.prepareStatement(query_updateDrive);
//            prepUpdateDriveStmt.setString(1, manufacturer);
//            prepUpdateDriveStmt.setString(2, serialNumber);
//            prepUpdateDriveStmt.setString(3, property);
//            prepUpdateDriveStmt.setString(4, customerName);
//            prepUpdateDriveStmt.setString(5, cts);
//            prepUpdateDriveStmt.setString(6, jira);
//            prepUpdateDriveStmt.setString(7, label);
//            prepUpdateDriveStmt.setString(8, driveLocation);
//            prepUpdateDriveStmt.setString(9, driveState);
//            prepUpdateDriveStmt.setString(10, encrypted);
//            prepUpdateDriveStmt.setString(11, box);
//            prepUpdateDriveStmt.setString(12, usb);
//            prepUpdateDriveStmt.setString(13, power);
//            prepUpdateDriveStmt.setString(14, rack);
//            prepUpdateDriveStmt.setString(15, shelf);
//            prepUpdateDriveStmt.setString(16, notes);
//            prepUpdateDriveStmt.setString(17, receivedDate);
//            prepUpdateDriveStmt.setString(18, sentDate);
//            prepUpdateDriveStmt.setString(19, shippingCarrierSent);
//            prepUpdateDriveStmt.setString(20, shippingTrackingNumberSent);
//            prepUpdateDriveStmt.setString(21, updatedBy);
//            prepUpdateDriveStmt.setTimestamp(22, sqlTime);
//
//			int updateRes = prepUpdateDriveStmt.executeUpdate();
//
//            System.out.println("Update drive: " + query_updateDrive);
//
//            String query_selectDriveById = "select * from drive_info where pp_asset_tag = '" + assetTag + "'";
//            PreparedStatement prepSelectDriveStmt = connect.prepareStatement(query_selectDriveById);
//            ResultSet selectDriveRes = prepSelectDriveStmt.executeQuery();
//
//            while (selectDriveRes.next()) {
//                Timestamp created = selectDriveRes.getTimestamp("created");
//
//                String query_createHistory = "insert into drive_history ("
//                        + "pp_asset_tag, manufacturer_model, serial_number, property, "
//                        + "customer_name, cts, jira, label, drive_location, drive_state, "
//                        + "encrypted, box, usb, power, rack, shelf, notes, "
//                        + "received_date, "
//                        + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
//                        + "created, last_updated, updated_by) "
//                        + "values ("
//                        + "?,?,?,?,?,?,?,?,?,?,"
//                        + "?,?,?,?,?,?,?,?,?,?,"
//                        + "?,?,?,?);";
//
//                PreparedStatement prepCreateHistoryStmt = connect.prepareStatement(query_createHistory);
//
//                prepCreateHistoryStmt.setString(1, assetTag);
//                prepCreateHistoryStmt.setString(2, manufacturer);
//                prepCreateHistoryStmt.setString(3, serialNumber);
//                prepCreateHistoryStmt.setString(4, property);
//                prepCreateHistoryStmt.setString(5, customerName);
//                prepCreateHistoryStmt.setString(6, cts);
//                prepCreateHistoryStmt.setString(7, jira);
//                prepCreateHistoryStmt.setString(8, label);
//                prepCreateHistoryStmt.setString(9, driveLocation);
//                prepCreateHistoryStmt.setString(10, driveState);
//                prepCreateHistoryStmt.setString(11, encrypted);
//                prepCreateHistoryStmt.setString(12, box);
//                prepCreateHistoryStmt.setString(13, usb);
//                prepCreateHistoryStmt.setString(14, power);
//                prepCreateHistoryStmt.setString(15, rack);
//                prepCreateHistoryStmt.setString(16, shelf);
//                prepCreateHistoryStmt.setString(17, notes);
//                prepCreateHistoryStmt.setString(18, receivedDate);
//                prepCreateHistoryStmt.setString(19, sentDate);
//                prepCreateHistoryStmt.setString(20, shippingCarrierSent);
//                prepCreateHistoryStmt.setString(21, shippingTrackingNumberSent);
//                prepCreateHistoryStmt.setTimestamp(22, created);
//                prepCreateHistoryStmt.setTimestamp(23, sqlTime);
//                prepCreateHistoryStmt.setString(24, updatedBy);
//
//                int historyRes = prepCreateHistoryStmt.executeUpdate();
//
//                System.out.println("Create history: " + query_createHistory);
//
//                prepCreateHistoryStmt.close();
//            }
//
//            result = true;
//
//            prepSelectDriveStmt.close();
//            prepUpdateDriveStmt.close();
//
//            sendEmailNotification();
//
//        } catch(SQLException e) {
//            eMessage = e.getMessage();
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            eMessage = e.getMessage();
//            e.printStackTrace();
//        } finally {
//            try {
//                if(connect != null)
//                    connect.close();
//            } catch(SQLException se) {
//                eMessage = se.getMessage();
//                se.printStackTrace();
//            }
//        }
//
//		return result;
//	}
//
//    private void sendEmailNotification() {
//        HistoryInfo historyInfo = new HistoryInfo("UPDATED");
//        historyInfo.setAssetTag(assetTag);
//        historyInfo.setCustomerName(customerName);
//        historyInfo.setDriveLocation(driveLocation);
//        historyInfo.setDriveState(driveState);
//        historyInfo.setNotes(notes);
//        historyInfo.setLastUpdated(lastUpdated);
//        historyInfo.setUpdatedBy(updatedBy);
//        EmailNotifier se = new EmailNotifier(historyInfo);
//        se.send();
//    }
//}


package app;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import db_credentials.mysql_credentials;

/**
 * Servlet implementation class updateDrive
 */
public class updateDrive extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private String assetTag;
    private String manufacturer;
    private String serialNumber;
    private String property;
    private String customerName;
    private String cts;
    private String jira;
    private String label;
    private String driveLocation;
    private String driveState;
    private String encrypted;
    private String box;
    private String usb;
    private String power;
    private String rack;
    private String shelf;
    private String notes;
    private String receivedDate;
    private String sentDate;
    private String shippingCarrierSent;
    private String shippingTrackingNumberSent;
    private String lastUpdated;
    private String updatedBy;
    private String return_media_to_customer;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateDrive() {
        super();
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        assetTag = request.getParameter("pp_asset_tag");
        manufacturer = request.getParameter("manufacturer");
        serialNumber = request.getParameter("serial_number");
        property = request.getParameter("property");
        customerName = request.getParameter("customer_name");
        cts = request.getParameter("cts");
        jira = request.getParameter("jira");
        label = request.getParameter("label");
        driveLocation = request.getParameter("drive_location");
        driveState = request.getParameter("drive_state");
        encrypted = ""; //request.getParameter("encrypted");
        box = ""; //request.getParameter("box");
        usb = request.getParameter("usb");
        power = request.getParameter("power");
        rack = ""; //request.getParameter("rack");
        shelf = ""; //request.getParameter("shelf");
        notes = request.getParameter("notes");
        receivedDate = request.getParameter("received_date");
        sentDate = request.getParameter("sent_date");
        shippingCarrierSent = request.getParameter("shipping_carrier_sent");
        shippingTrackingNumberSent = request.getParameter("shipping_tracking_number_sent");
        updatedBy = request.getParameter("updated_by");
        return_media_to_customer = request.getParameter("return_media_to_customer");

        JSONObject json = new JSONObject();

        if(updateDriveAndAddToHistory()) {
            json.put("result", "success");
        }
        else
            json.put("result", eMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    public boolean updateDriveAndAddToHistory() {

        boolean result = false;
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            Date currentDatetime = new Date();
            Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
            lastUpdated = sqlTime.toString();

            String query_updateDrive = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, " +
                    "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, " +
                    "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, " +
                    "received_date = ?, " +
                    "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, " +
                    "updated_by = ?, last_updated = ?, return_media_to_customer = ? " +
                    "where pp_asset_tag = '" + assetTag +"'";

            PreparedStatement prepUpdateDriveStmt = connect.prepareStatement(query_updateDrive);
            prepUpdateDriveStmt.setString(1, manufacturer);
            prepUpdateDriveStmt.setString(2, serialNumber);
            prepUpdateDriveStmt.setString(3, property);
            prepUpdateDriveStmt.setString(4, customerName);
            prepUpdateDriveStmt.setString(5, cts);
            prepUpdateDriveStmt.setString(6, jira);
            prepUpdateDriveStmt.setString(7, label);
            prepUpdateDriveStmt.setString(8, driveLocation);
            prepUpdateDriveStmt.setString(9, driveState);
            prepUpdateDriveStmt.setString(10, encrypted);
            prepUpdateDriveStmt.setString(11, box);
            prepUpdateDriveStmt.setString(12, usb);
            prepUpdateDriveStmt.setString(13, power);
            prepUpdateDriveStmt.setString(14, rack);
            prepUpdateDriveStmt.setString(15, shelf);
            prepUpdateDriveStmt.setString(16, notes);
            prepUpdateDriveStmt.setString(17, receivedDate);
            prepUpdateDriveStmt.setString(18, sentDate);
            prepUpdateDriveStmt.setString(19, shippingCarrierSent);
            prepUpdateDriveStmt.setString(20, shippingTrackingNumberSent);
            prepUpdateDriveStmt.setString(21, updatedBy);
            prepUpdateDriveStmt.setTimestamp(22, sqlTime);
            prepUpdateDriveStmt.setString(23, return_media_to_customer);

            int updateRes = prepUpdateDriveStmt.executeUpdate();

            System.out.println("Update drive: " + query_updateDrive);

            String query_selectDriveById = "select * from drive_info where pp_asset_tag = '" + assetTag + "'";
            PreparedStatement prepSelectDriveStmt = connect.prepareStatement(query_selectDriveById);
            ResultSet selectDriveRes = prepSelectDriveStmt.executeQuery();

            while (selectDriveRes.next()) {
                Timestamp created = selectDriveRes.getTimestamp("created");

                String query_createHistory = "insert into drive_history ("
                        + "pp_asset_tag, manufacturer_model, serial_number, property, "
                        + "customer_name, cts, jira, label, drive_location, drive_state, "
                        + "encrypted, box, usb, power, rack, shelf, notes, "
                        + "received_date, "
                        + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
                        + "created, last_updated, updated_by, return_media_to_customer) "
                        + "values ("
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?);";

                PreparedStatement prepCreateHistoryStmt = connect.prepareStatement(query_createHistory);

                prepCreateHistoryStmt.setString(1, assetTag);
                prepCreateHistoryStmt.setString(2, manufacturer);
                prepCreateHistoryStmt.setString(3, serialNumber);
                prepCreateHistoryStmt.setString(4, property);
                prepCreateHistoryStmt.setString(5, customerName);
                prepCreateHistoryStmt.setString(6, cts);
                prepCreateHistoryStmt.setString(7, jira);
                prepCreateHistoryStmt.setString(8, label);
                prepCreateHistoryStmt.setString(9, driveLocation);
                prepCreateHistoryStmt.setString(10, driveState);
                prepCreateHistoryStmt.setString(11, encrypted);
                prepCreateHistoryStmt.setString(12, box);
                prepCreateHistoryStmt.setString(13, usb);
                prepCreateHistoryStmt.setString(14, power);
                prepCreateHistoryStmt.setString(15, rack);
                prepCreateHistoryStmt.setString(16, shelf);
                prepCreateHistoryStmt.setString(17, notes);
                prepCreateHistoryStmt.setString(18, receivedDate);
                prepCreateHistoryStmt.setString(19, sentDate);
                prepCreateHistoryStmt.setString(20, shippingCarrierSent);
                prepCreateHistoryStmt.setString(21, shippingTrackingNumberSent);
                prepCreateHistoryStmt.setTimestamp(22, created);
                prepCreateHistoryStmt.setTimestamp(23, sqlTime);
                prepCreateHistoryStmt.setString(24, updatedBy);
                prepCreateHistoryStmt.setString(25, return_media_to_customer);

                int historyRes = prepCreateHistoryStmt.executeUpdate();

                System.out.println("Create history: " + query_createHistory);

                prepCreateHistoryStmt.close();
            }

            result = true;

            prepSelectDriveStmt.close();
            prepUpdateDriveStmt.close();

            sendEmailNotification();

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

    private void sendEmailNotification() {
        HistoryInfo historyInfo = new HistoryInfo("UPDATED");
        historyInfo.setAssetTag(assetTag);
        historyInfo.setCustomerName(customerName);
        historyInfo.setDriveLocation(driveLocation);
        historyInfo.setDriveState(driveState);
        historyInfo.setNotes(notes);
        historyInfo.setLastUpdated(lastUpdated);
        historyInfo.setUpdatedBy(updatedBy);
        EmailNotifier se = new EmailNotifier(historyInfo);
        se.send();
    }
}
