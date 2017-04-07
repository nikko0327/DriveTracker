//package app;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.*;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//
//import net.sf.json.JSONObject;
//
//import db_credentials.mysql_credentials;
//@WebServlet("/uploadServlet")
//@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
///**
// * Servlet implementation class createDrive
// */
//public class createDrive extends HttpServlet implements mysql_credentials {
//    private static final long serialVersionUID = 1L;
//    private String eMessage;
//
//    private HistoryInfo drive;
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
//    private String receivedOrSent;
//    private String encrypted;
//    private String box;
//    private String usb;
//    private String power;
//    private String rack;
//    private String shelf;
//    private String receivedDate;
//    private String shippingCarrier;
//    private String shippingTrackingNumber;
//    private String sentDate;
//    private String notes;
//    private String lastUpdated;
//    private String updatedBy;
//
//    private String isUpdate;
//
//    InputStream inputStream;
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public createDrive() {
//        super();
//    }
//
//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//     *      response)
//     */
//    protected void doPost(HttpServletRequest request,
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
//
//        if(driveState.startsWith("Shipped")
//                || driveState.startsWith("Returned"))
//            receivedOrSent = "Sent";
//        else
//            receivedOrSent = "Received";
//
//        encrypted = ""; //request.getParameter("encrypted");
//        box = ""; //request.getParameter("box");
//        usb = request.getParameter("usb");
//        power = request.getParameter("power");
//        rack = ""; //request.getParameter("rack");
//        shelf = ""; //request.getParameter("shelf");
//        receivedDate = request.getParameter("received_date");
//        shippingCarrier = request.getParameter("shipping_carrier");
//        shippingTrackingNumber = request.getParameter("shipping_tracking_number");
//        sentDate = request.getParameter("sent_date");
//        notes = request.getParameter("notes");
//        updatedBy = request.getParameter("updated_by");
//        isUpdate = request.getParameter("is_update");
//
////        //ADDED FOR  FILE UPLOAD
////
////          inputStream = null; // input stream of the upload file //
////
//        // obtains the upload file part in this multipart request
////           Part filePart = request.getPart("upload");
////        if (filePart != null) {
////            // prints out some information for debugging
////            System.out.println(filePart.getName());
////            System.out.println(filePart.getSize());
////            System.out.println(filePart.getContentType());
////
////            // obtains input stream of the upload file
////            inputStream = filePart.getInputStream();
////        }
////            //ADDED FOR  FILE UPLOAD
//
//        String latestDrive;
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        if (createDriveAndHistory()) {
//            latestDrive = getCreatedDrive();
//
//            response.getWriter().write(latestDrive);
//            //System.out.println("latest drive = " + latestDrive);
//        }
//        else {
//            JSONObject json = new JSONObject();
//            json.put("message", eMessage);
//            response.getWriter().write(json.toString());
//        }
//
//        response.flushBuffer();
//    }
//
//    private boolean createDriveAndHistory() {
//
//        boolean result = false;
//        Connection connect = null;
//        try {
//            java.util.Date currentDatetime = new java.util.Date();
//            java.sql.Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
//            lastUpdated = sqlTime.toString();
//
//            Class.forName("com.mysql.jdbc.Driver");
//            connect = DriverManager.getConnection(db_url, user_name, password);
//
//            String query_createSprint;
//
//            if(isUpdate.equals("true")) {
//                query_createSprint = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, "
//                        + "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, "
//                        + "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, "
//                        + "created = ?, last_updated = ?, updated_by = ?, "
//                        + "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, "
//                        + "received_date = ? "
//                        + "where pp_asset_tag = ?";
//            }
//
//            else {
//                query_createSprint = "insert into drive_info ("
//                        + "manufacturer_model, serial_number, property, "
//                        + "customer_name, cts, jira, label, drive_location, drive_state, "
//                        + "encrypted, box, usb, power, rack, shelf, notes, "
//                        + "created, last_updated, updated_by, "
//                        + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
//                        + "received_date, "
//                        + "pp_asset_tag) "
//                        + "values ("
//                        + "?,?,?,?,?,?,?,?,?,?,"
//                        + "?,?,?,?,?,?,?,?,?,?,"
//                        + "?,?,?,?);";
//            }
//
//            PreparedStatement prepCreateSprintStmt = connect.prepareStatement(query_createSprint);
//
//            prepCreateSprintStmt.setString(1, manufacturer);
//            prepCreateSprintStmt.setString(2, serialNumber);
//            prepCreateSprintStmt.setString(3, property);
//            prepCreateSprintStmt.setString(4, customerName);
//            prepCreateSprintStmt.setString(5, cts);
//            prepCreateSprintStmt.setString(6, jira);
//            prepCreateSprintStmt.setString(7, label);
//            prepCreateSprintStmt.setString(8, driveLocation);
//            prepCreateSprintStmt.setString(9, driveState);
//            prepCreateSprintStmt.setString(10, encrypted);
//            prepCreateSprintStmt.setString(11, box);
//            prepCreateSprintStmt.setString(12, usb);
//            prepCreateSprintStmt.setString(13, power);
//            prepCreateSprintStmt.setString(14, rack);
//            prepCreateSprintStmt.setString(15, shelf);
//            prepCreateSprintStmt.setString(16, notes);
//            prepCreateSprintStmt.setTimestamp(17, sqlTime);
//            prepCreateSprintStmt.setTimestamp(18, sqlTime);
//            prepCreateSprintStmt.setString(19, updatedBy);
//
//            if(receivedOrSent.equals("Received")) {
//                prepCreateSprintStmt.setString(20, "");
//                prepCreateSprintStmt.setString(21, "");
//                prepCreateSprintStmt.setString(22, "");
//            }
//            else if(receivedOrSent.equals("Sent")) {
//                prepCreateSprintStmt.setString(20, sentDate);
//                prepCreateSprintStmt.setString(21, shippingCarrier);
//                prepCreateSprintStmt.setString(22, shippingTrackingNumber);
//            }
//
//            prepCreateSprintStmt.setString(23, receivedDate);
//
//            prepCreateSprintStmt.setString(24, assetTag);
//
////            //ADDED FOR FILE UPLOAD
////            if (inputStream != null) {
////                // fetches input stream of the upload file for the blob column
////                prepCreateSprintStmt.setBlob(25, inputStream);
////            }
////            //ADDED FOR FILE UPLOAD
//
//            int createSprintStmtRes = prepCreateSprintStmt.executeUpdate();
//
//            System.out.println("Create drive: " + query_createSprint);
//
//            String query_createHistory = "insert into drive_history ("
//                    + "pp_asset_tag, manufacturer_model, serial_number, property, "
//                    + "customer_name, cts, jira, label, drive_location, drive_state, "
//                    + "encrypted, box, usb, power, rack, shelf, notes, "
//                    + "created, last_updated, updated_by, "
//                    + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
//                    + "received_date) "
//                    + "values ("
//                    + "?,?,?,?,?,?,?,?,?,?,"
//                    + "?,?,?,?,?,?,?,?,?,?,"
//                    + "?,?,?,?);";
//
//
//            PreparedStatement prepCreateHistoryStmt = connect.prepareStatement(query_createHistory);
//            prepCreateHistoryStmt.setString(1, assetTag);
//            prepCreateHistoryStmt.setString(2, manufacturer);
//            prepCreateHistoryStmt.setString(3, serialNumber);
//            prepCreateHistoryStmt.setString(4, property);
//            prepCreateHistoryStmt.setString(5, customerName);
//            prepCreateHistoryStmt.setString(6, cts);
//            prepCreateHistoryStmt.setString(7, jira);
//            prepCreateHistoryStmt.setString(8, label);
//            prepCreateHistoryStmt.setString(9, driveLocation);
//            prepCreateHistoryStmt.setString(10, driveState);
//            prepCreateHistoryStmt.setString(11, encrypted);
//            prepCreateHistoryStmt.setString(12, box);
//            prepCreateHistoryStmt.setString(13, usb);
//            prepCreateHistoryStmt.setString(14, power);
//            prepCreateHistoryStmt.setString(15, rack);
//            prepCreateHistoryStmt.setString(16, shelf);
//            prepCreateHistoryStmt.setString(17, notes);
//            prepCreateHistoryStmt.setTimestamp(18, sqlTime);
//            prepCreateHistoryStmt.setTimestamp(19, sqlTime);
//            prepCreateHistoryStmt.setString(20, updatedBy);
//
//            if(receivedOrSent.equals("Received")) {
//                prepCreateHistoryStmt.setString(21, "");
//                prepCreateHistoryStmt.setString(22, "");
//                prepCreateHistoryStmt.setString(23, "");
//            }
//            else if(receivedOrSent.equals("Sent")) {
//                prepCreateHistoryStmt.setString(21, sentDate);
//                prepCreateHistoryStmt.setString(22, shippingCarrier);
//                prepCreateHistoryStmt.setString(23, shippingTrackingNumber);
//            }
//
//            prepCreateHistoryStmt.setString(24, receivedDate);
//
////            //ADDED FOR FILE UPLOAD
////            if (inputStream != null) {
////                // fetches input stream of the upload file for the blob column
////                prepCreateSprintStmt.setBlob(25, inputStream);
////            }
////            //ADDED FOR FILE UPLOAD
//
//            int createHistoryStmtRes = prepCreateHistoryStmt.executeUpdate();
//
//            System.out.println("Create history: " + query_createHistory);
//
//            result = true;
//
//            prepCreateSprintStmt.close();
//            prepCreateHistoryStmt.close();
//
//            sendEmailNotification();
//
//        } catch(SQLException e) {
//            eMessage = e.getMessage();
//            e.printStackTrace();
//        } catch(ClassNotFoundException e) {
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
//        return result;
//    }
//
//    private String getCreatedDrive() {
//
//        JSONObject json = new JSONObject();
//        Connection connect = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connect = DriverManager.getConnection(db_url, user_name, password);
//
//            String query_getDriveById = "select * from drive_info where pp_asset_tag ='" + assetTag + "';";
//
//            PreparedStatement prepStmt = connect
//                    .prepareStatement(query_getDriveById);
//            ResultSet rs = prepStmt.executeQuery();
//
//            while (rs.next()) {
//                json.put("pp_asset_tag", assetTag);
//                json.put("manufacturer", rs.getString("manufacturer_model"));
//                json.put("serial_number", rs.getString("serial_number"));
//                json.put("property", rs.getString("property"));
//                json.put("customer_name", rs.getString("customer_name"));
//                json.put("cts", rs.getString("cts"));
//                json.put("jira", rs.getString("jira"));
//                json.put("label", rs.getString("label"));
//                json.put("drive_location", rs.getString("drive_location"));
//                json.put("drive_state", rs.getString("drive_state"));
//                json.put("box", rs.getString("box"));
//                json.put("usb", rs.getString("usb"));
//                json.put("power", rs.getString("power"));
//                json.put("notes", rs.getString("notes"));
//                json.put("received_date", rs.getString("received_date"));
//                json.put("sent_date", rs.getString("sent_date"));
//                json.put("shipping_carrier", rs.getString("shipping_carrier_sent"));
//                json.put("shipping_tracking_number", rs.getString("shipping_tracking_number_sent"));
//                json.put("created", rs.getString("created"));
//                json.put("last_updated", rs.getString("last_updated"));
//                json.put("updated_by", rs.getString("updated_by"));
////                json.put("inputStream", rs.getBlob("inputStream"));
//            }
//        } catch(SQLException e) {
//            eMessage = e.getMessage();
//            e.printStackTrace();
//        } catch(ClassNotFoundException e) {
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
//        return json.toString();
//    }
//
//    //Sends email to notify the drive has been created.
//    private void sendEmailNotification() {
//        HistoryInfo historyInfo = new HistoryInfo("CREATED");
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
import java.io.InputStream;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.sf.json.JSONObject;

import db_credentials.mysql_credentials;
@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
/**
 * Servlet implementation class createDrive
 */
public class createDrive extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private HistoryInfo drive;

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
    private String receivedOrSent;
    private String encrypted;
    private String box;
    private String usb;
    private String power;
    private String rack;
    private String shelf;
    private String receivedDate;
    private String shippingCarrier;
    private String shippingTrackingNumber;
    private String sentDate;
    private String return_media_to_customer;
    private String notes;
    private String lastUpdated;
    private String updatedBy;

    private String isUpdate;

    InputStream inputStream;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public createDrive() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
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

        if(driveState.startsWith("Shipped")
                || driveState.startsWith("Returned"))
            receivedOrSent = "Sent";
        else
            receivedOrSent = "Received";

        encrypted = ""; //request.getParameter("encrypted");
        box = ""; //request.getParameter("box");
        usb = request.getParameter("usb");
        power = request.getParameter("power");
        rack = ""; //request.getParameter("rack");
        shelf = ""; //request.getParameter("shelf");
        receivedDate = request.getParameter("received_date");
        shippingCarrier = request.getParameter("shipping_carrier");
        shippingTrackingNumber = request.getParameter("shipping_tracking_number");
        sentDate = request.getParameter("sent_date");
        return_media_to_customer = request.getParameter("return_media_to_customer");
        notes = request.getParameter("notes");
        updatedBy = request.getParameter("updated_by");
        isUpdate = request.getParameter("is_update");

        String latestDrive;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (createDriveAndHistory()) {
            latestDrive = getCreatedDrive();

            response.getWriter().write(latestDrive);
        }
        else {
            JSONObject json = new JSONObject();
            json.put("message", eMessage);
            response.getWriter().write(json.toString());
        }

        response.flushBuffer();
    }

    private boolean createDriveAndHistory() {

        boolean result = false;
        Connection connect = null;
        try {
            java.util.Date currentDatetime = new java.util.Date();
            java.sql.Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
            lastUpdated = sqlTime.toString();

            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            String query_createSprint;

            if(isUpdate.equals("true")) {
                query_createSprint = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, "
                        + "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, "
                        + "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, "
                        + "created = ?, last_updated = ?, updated_by = ?, "
                        + "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, "
                        + "received_date = ?, return_media_to_customer = ? "
                        + "where pp_asset_tag = ?";
            }

            else {
                query_createSprint = "insert into drive_info ("
                        + "manufacturer_model, serial_number, property, "
                        + "customer_name, cts, jira, label, drive_location, drive_state, "
                        + "encrypted, box, usb, power, rack, shelf, notes, "
                        + "created, last_updated, updated_by, "
                        + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
                        + "received_date, return_media_to_customer, "
                        + "pp_asset_tag) "
                        + "values ("
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?);";
            }

            PreparedStatement prepCreateSprintStmt = connect.prepareStatement(query_createSprint);

            prepCreateSprintStmt.setString(1, manufacturer);
            prepCreateSprintStmt.setString(2, serialNumber);
            prepCreateSprintStmt.setString(3, property);
            prepCreateSprintStmt.setString(4, customerName);
            prepCreateSprintStmt.setString(5, cts);
            prepCreateSprintStmt.setString(6, jira);
            prepCreateSprintStmt.setString(7, label);
            prepCreateSprintStmt.setString(8, driveLocation);
            prepCreateSprintStmt.setString(9, driveState);
            prepCreateSprintStmt.setString(10, encrypted);
            prepCreateSprintStmt.setString(11, box);
            prepCreateSprintStmt.setString(12, usb);
            prepCreateSprintStmt.setString(13, power);
            prepCreateSprintStmt.setString(14, rack);
            prepCreateSprintStmt.setString(15, shelf);
            prepCreateSprintStmt.setString(16, notes);
            prepCreateSprintStmt.setTimestamp(17, sqlTime);
            prepCreateSprintStmt.setTimestamp(18, sqlTime);
            prepCreateSprintStmt.setString(19, updatedBy);

            if(receivedOrSent.equals("Received")) {
                prepCreateSprintStmt.setString(20, "");
                prepCreateSprintStmt.setString(21, "");
                prepCreateSprintStmt.setString(22, "");
            }
            else if(receivedOrSent.equals("Sent")) {
                prepCreateSprintStmt.setString(20, sentDate);
                prepCreateSprintStmt.setString(21, shippingCarrier);
                prepCreateSprintStmt.setString(22, shippingTrackingNumber);
            }

            prepCreateSprintStmt.setString(23, receivedDate);
            prepCreateSprintStmt.setString(24, return_media_to_customer);
            prepCreateSprintStmt.setString(25, assetTag);

            int createSprintStmtRes = prepCreateSprintStmt.executeUpdate();

            System.out.println("Create drive: " + query_createSprint);

            String query_createHistory = "insert into drive_history ("
                    + "pp_asset_tag, manufacturer_model, serial_number, property, "
                    + "customer_name, cts, jira, label, drive_location, drive_state, "
                    + "encrypted, box, usb, power, rack, shelf, notes, "
                    + "created, last_updated, updated_by, "
                    + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, received_date, "
                    + "return_media_to_customer) "
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
            prepCreateHistoryStmt.setTimestamp(18, sqlTime);
            prepCreateHistoryStmt.setTimestamp(19, sqlTime);
            prepCreateHistoryStmt.setString(20, updatedBy);

            if(receivedOrSent.equals("Received")) {
                prepCreateHistoryStmt.setString(21, "");
                prepCreateHistoryStmt.setString(22, "");
                prepCreateHistoryStmt.setString(23, "");
            }
            else if(receivedOrSent.equals("Sent")) {
                prepCreateHistoryStmt.setString(21, sentDate);
                prepCreateHistoryStmt.setString(22, shippingCarrier);
                prepCreateHistoryStmt.setString(23, shippingTrackingNumber);
            }

            prepCreateHistoryStmt.setString(24, receivedDate);
            prepCreateHistoryStmt.setString(25, return_media_to_customer);

            int createHistoryStmtRes = prepCreateHistoryStmt.executeUpdate();

            System.out.println("Create history: " + query_createHistory);

            result = true;

            prepCreateSprintStmt.close();
            prepCreateHistoryStmt.close();

            sendEmailNotification();

        } catch(SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
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

    private String getCreatedDrive() {

        JSONObject json = new JSONObject();
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            String query_getDriveById = "select * from drive_info where pp_asset_tag ='" + assetTag + "';";

            PreparedStatement prepStmt = connect
                    .prepareStatement(query_getDriveById);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                json.put("pp_asset_tag", assetTag);
                json.put("manufacturer", rs.getString("manufacturer_model"));
                json.put("serial_number", rs.getString("serial_number"));
                json.put("property", rs.getString("property"));
                json.put("customer_name", rs.getString("customer_name"));
                json.put("cts", rs.getString("cts"));
                json.put("jira", rs.getString("jira"));
                json.put("label", rs.getString("label"));
                json.put("drive_location", rs.getString("drive_location"));
                json.put("drive_state", rs.getString("drive_state"));
                json.put("box", rs.getString("box"));
                json.put("usb", rs.getString("usb"));
                json.put("power", rs.getString("power"));
                json.put("notes", rs.getString("notes"));
                json.put("received_date", rs.getString("received_date"));
                json.put("sent_date", rs.getString("sent_date"));
                json.put("return_media_to_customer", rs.getString("return_media_to_customer"));
                json.put("shipping_carrier", rs.getString("shipping_carrier_sent"));
                json.put("shipping_tracking_number", rs.getString("shipping_tracking_number_sent"));
                json.put("created", rs.getString("created"));
                json.put("last_updated", rs.getString("last_updated"));
                json.put("updated_by", rs.getString("updated_by"));
//                json.put("inputStream", rs.getBlob("inputStream"));
            }
        } catch(SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
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

        return json.toString();
    }

    //Sends email to notify the drive has been created.
    private void sendEmailNotification() {
        HistoryInfo historyInfo = new HistoryInfo("CREATED");
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
