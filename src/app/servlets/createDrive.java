package app.servlets;

import app.EmailNotifier;
import app.HistoryInfo;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
/**
 * Servlet implementation class createDrive
 */
public class createDrive extends HttpServlet {
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
    private String essential;
    private String isUpdate;
    private JSONObject newCreatedDrive;

    InputStream inputStream;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public createDrive() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {

        System.out.println("--- createDrive ---");
        try {
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

            if (driveState.startsWith("Shipped") || driveState.startsWith("Returned")) {
                receivedOrSent = "Sent";
            } else {
                receivedOrSent = "Received";
            }

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
            essential = request.getParameter("essential");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (createDriveAndHistory()) {
                response.getWriter().write(newCreatedDrive.toString());
            } else {
                JSONObject json = new JSONObject();
                json.put("message", eMessage);
                response.getWriter().write(json.toString());
            }

            response.flushBuffer();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private boolean createDriveAndHistory() {

        boolean result = false;

        Connection connect = null;
        PreparedStatement psCreateSprint = null;
        PreparedStatement psCreateHistory = null;

        try {
            java.util.Date currentDatetime = new java.util.Date();
            java.sql.Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
            lastUpdated = sqlTime.toString();

            connect = dataSource.getConnection();

            String query_createSprint;

            if (isUpdate.equals("true")) {
                query_createSprint = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, "
                        + "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, "
                        + "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, "
                        + "created = ?, last_updated = ?, updated_by = ?, "
                        + "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, "
                        + "received_date = ?, return_media_to_customer = ?, essential = ? "
                        + "where pp_asset_tag = ?";
            } else {
                query_createSprint = "insert into drive_info ("
                        + "pp_asset_tag, manufacturer_model, serial_number, property, "
                        + "customer_name, cts, jira, label, drive_location, drive_state, "
                        + "encrypted, box, usb, power, rack, shelf, notes, "
                        + "created, last_updated, updated_by, "
                        + "sent_date, shipping_carrier_sent, shipping_tracking_number_sent, "
                        + "received_date, return_media_to_customer, "
                        + "essential)"
                        + "values ("
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?);";
            }

            psCreateSprint = connect.prepareStatement(query_createSprint);

            psCreateSprint.setString(1, assetTag);
            psCreateSprint.setString(2, manufacturer);
            psCreateSprint.setString(3, serialNumber);
            psCreateSprint.setString(4, property);
            psCreateSprint.setString(5, customerName);
            psCreateSprint.setString(6, cts);
            psCreateSprint.setString(7, jira);
            psCreateSprint.setString(8, label);
            psCreateSprint.setString(9, driveLocation);
            psCreateSprint.setString(10, driveState);
            psCreateSprint.setString(11, encrypted);
            psCreateSprint.setString(12, box);
            psCreateSprint.setString(13, usb);
            psCreateSprint.setString(14, power);
            psCreateSprint.setString(15, rack);
            psCreateSprint.setString(16, shelf);
            psCreateSprint.setString(17, notes);
            psCreateSprint.setTimestamp(18, sqlTime);
            psCreateSprint.setTimestamp(19, sqlTime);
            psCreateSprint.setString(20, updatedBy);

            if (receivedOrSent.equals("Received")) {
                psCreateSprint.setString(21, "");
                psCreateSprint.setString(22, "");
                psCreateSprint.setString(23, "");
            } else if (receivedOrSent.equals("Sent")) {
                psCreateSprint.setString(21, sentDate);
                psCreateSprint.setString(22, shippingCarrier);
                psCreateSprint.setString(23, shippingTrackingNumber);
            }

            psCreateSprint.setString(24, receivedDate);
            psCreateSprint.setString(25, return_media_to_customer);
            psCreateSprint.setString(26, essential);

            psCreateSprint.executeUpdate();

            System.out.println("Create drive: " + query_createSprint);

            newCreatedDrive = new JSONObject();
            newCreatedDrive.put("pp_asset_tag", assetTag);
            newCreatedDrive.put("manufacturer", manufacturer);
            newCreatedDrive.put("serial_number", serialNumber);
            newCreatedDrive.put("property", property);
            newCreatedDrive.put("customer_name", customerName);
            newCreatedDrive.put("cts", cts);
            newCreatedDrive.put("jira", jira);
            newCreatedDrive.put("label", label);
            newCreatedDrive.put("drive_location", driveLocation);
            newCreatedDrive.put("drive_state", driveState);
            newCreatedDrive.put("box", box);
            newCreatedDrive.put("usb", usb);
            newCreatedDrive.put("power", power);
            newCreatedDrive.put("notes", notes);
            newCreatedDrive.put("received_date", receivedDate);
            newCreatedDrive.put("sent_date", sentDate);
            newCreatedDrive.put("return_media_to_customer", return_media_to_customer);
            newCreatedDrive.put("shipping_carrier", shippingCarrier);
            newCreatedDrive.put("shipping_tracking_number", shippingTrackingNumber);
            newCreatedDrive.put("created", sqlTime.toString());
            newCreatedDrive.put("last_updated", sqlTime.toString());
            newCreatedDrive.put("updated_by", updatedBy);
            newCreatedDrive.put("essential", essential);

            result = true;

            sendEmailNotification();

        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, psCreateHistory, psCreateSprint);
        }

        return result;
    }

    @SuppressWarnings("Duplicates")
    private String getCreatedDrive() {

        JSONObject json = new JSONObject();

        Connection connect = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            connect = dataSource.getConnection();

            String query_getDriveById = "select * from drive_info where pp_asset_tag ='" + assetTag + "';";

            prepStmt = connect.prepareStatement(query_getDriveById);
            rs = prepStmt.executeQuery();

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
                json.put("essential", rs.getString("essential"));
                // json.put("inputStream", rs.getBlob("inputStream"));
            }
        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, prepStmt, rs);
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
        EmailNotifier se = new EmailNotifier(historyInfo, this.essential);
        se.send();
    }
}
