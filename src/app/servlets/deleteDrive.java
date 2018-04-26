package app.servlets;

import app.EmailNotifier;
import app.HistoryInfo;
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
 * Servlet implementation class deleteDrive
 */
public class deleteDrive extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private String assetTag;
    private String customerName;
    private String updatedBy;
    private String essential;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteDrive() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- deleteDrive ---");
        assetTag = request.getParameter("pp_asset_tag");
        customerName = request.getParameter("customer_name");
        updatedBy = request.getParameter("updated_by");
        essential = request.getParameter("essential");

        JSONObject json = new JSONObject();

        if (removeDriveAndHistory()) {
            json.put("result", "success");
        } else {
            json.put("result", eMessage);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    public boolean removeDriveAndHistory() {
        boolean result = false;

        Connection connect = null;
        PreparedStatement psDeleteDrive = null;
        PreparedStatement psDeleteHistory = null;
        PreparedStatement psDeleteFile = null;

        try {
            connect = dataSource.getConnection();

            String query_deleteDrive = "delete from drive_info where pp_asset_tag = '" + assetTag + "';";

            psDeleteDrive = connect.prepareStatement(query_deleteDrive);
            int deleteDriveRes = psDeleteDrive.executeUpdate();

            System.out.println("Delete drive: " + query_deleteDrive);

            String query_deleteHistory = "delete from drive_history where pp_asset_tag = '" + assetTag + "';";

            psDeleteHistory = connect.prepareStatement(query_deleteHistory);
            int deleteHistoryRes = psDeleteHistory.executeUpdate();

            System.out.println("Delete history: " + query_deleteHistory);

            //Deleting file that is related to current PP Asset Tag
            String query_deleteFile = "delete from upload where pp_asset_tag = '" + assetTag + "';";

            psDeleteFile = connect.prepareStatement(query_deleteFile);
            int deleteFileRes = psDeleteFile.executeUpdate();

            System.out.println("Delete drive: " + query_deleteDrive);

            result = true;

            sendEmailNotification();

        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, psDeleteDrive, psDeleteFile, psDeleteHistory);
        }

        return result;
    }

    private void sendEmailNotification() {
        HistoryInfo historyInfo = new HistoryInfo("DELETED");
        historyInfo.setAssetTag(assetTag);
        historyInfo.setCustomerName(customerName);
        historyInfo.setUpdatedBy(updatedBy);

        Date currentDatetime = new Date();
        Timestamp sqlTime = new Timestamp(currentDatetime.getTime());
        historyInfo.setLastUpdated(sqlTime.toString());

        EmailNotifier se = new EmailNotifier(historyInfo, this.essential);
        se.send();
    }
}
