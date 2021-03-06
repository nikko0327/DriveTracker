package app.servlets;

import app.EmailNotifier;
import app.HistoryInfo;
import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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
    private String essential;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateDrive() {
        super();
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        System.out.println("--- updateDrive ---");

        assetTag = request.getParameter("pp_asset_tag");
        manufacturer = request.getParameter("manufacturer");
        serialNumber = request.getParameter("serial_number");
        property = request.getParameter("property");
        customerName = request.getParameter("customer_name");
        System.out.println("--- updateDrive's post: " + customerName + " ---");
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
        essential = request.getParameter("essential");

        JSONObject json = new JSONObject();

        if (updateDriveAndAddToHistory()) {
            json.put("result", "success");
        } else
            json.put("result", eMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    public boolean updateDriveAndAddToHistory() {

        boolean result = false;
        Connection connect = null;
        PreparedStatement psUpdateDrive = null;
        PreparedStatement psSelectDrive = null;

        try {
            connect = dataSource.getConnection();

            Date currentDatetime = new Date();
            Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
            lastUpdated = sqlTime.toString();

            String query_updateDrive = "update drive_info set manufacturer_model = ?, serial_number = ?, property = ?, " +
                    "customer_name = ?, cts = ?, jira = ?, label = ?, drive_location = ?, drive_state = ?, " +
                    "encrypted = ?, box = ?, usb = ?, power = ?, rack = ?, shelf = ?, notes = ?, " +
                    "received_date = ?, " +
                    "sent_date = ?, shipping_carrier_sent = ?, shipping_tracking_number_sent = ?, " +
                    "updated_by = ?, last_updated = ?, return_media_to_customer = ?, essential = ? " +
                    "where pp_asset_tag = '" + assetTag + "'";

            psUpdateDrive = connect.prepareStatement(query_updateDrive);
            psUpdateDrive.setString(1, manufacturer);
            psUpdateDrive.setString(2, serialNumber);
            psUpdateDrive.setString(3, property);
            psUpdateDrive.setString(4, customerName);
            psUpdateDrive.setString(5, cts);
            psUpdateDrive.setString(6, jira);
            psUpdateDrive.setString(7, label);
            psUpdateDrive.setString(8, driveLocation);
            psUpdateDrive.setString(9, driveState);
            psUpdateDrive.setString(10, encrypted);
            psUpdateDrive.setString(11, box);
            psUpdateDrive.setString(12, usb);
            psUpdateDrive.setString(13, power);
            psUpdateDrive.setString(14, rack);
            psUpdateDrive.setString(15, shelf);
            psUpdateDrive.setString(16, notes);
            psUpdateDrive.setString(17, receivedDate);
            psUpdateDrive.setString(18, sentDate);
            psUpdateDrive.setString(19, shippingCarrierSent);
            psUpdateDrive.setString(20, shippingTrackingNumberSent);
            psUpdateDrive.setString(21, updatedBy);
            psUpdateDrive.setTimestamp(22, sqlTime);
            psUpdateDrive.setString(23, return_media_to_customer);
            psUpdateDrive.setString(24, essential);
            psUpdateDrive.executeUpdate();

            System.out.println("Update drive: " + query_updateDrive);
            result = true;
            sendEmailNotification();

        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, psUpdateDrive, psSelectDrive);
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
        EmailNotifier se = new EmailNotifier(historyInfo, this.essential);
        se.send();
    }
}
