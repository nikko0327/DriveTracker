package app.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.EmailNotifier;
import app.HistoryInfo;
import net.sf.json.JSONObject;

import db_credentials.mysql_credentials;

/**
 * Servlet implementation class deleteDrive
 */
public class deleteDrive extends HttpServlet implements mysql_credentials{
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private String assetTag;
    private String customerName;
    private String updatedBy;
    private String essential;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteDrive() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        assetTag = request.getParameter("pp_asset_tag");
        customerName = request.getParameter("customer_name");
        updatedBy = request.getParameter("updated_by");
        essential = request.getParameter("essential");

        JSONObject json = new JSONObject();

        if(removeDriveAndHistory())
            json.put("result", "success");
        else
            json.put("result", eMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    public boolean removeDriveAndHistory() {
        boolean result = false;

        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            String query_deleteDrive = "delete from drive_info where pp_asset_tag = '" + assetTag + "';";

            PreparedStatement prepDeleteDriveStmt = connect.prepareStatement(query_deleteDrive);
            int deleteDriveRes = prepDeleteDriveStmt.executeUpdate();

            System.out.println("Delete drive: " + query_deleteDrive);

            String query_deleteHistory = "delete from drive_history where pp_asset_tag = '" + assetTag + "';";

            PreparedStatement prepDeleteHistoryStmt = connect.prepareStatement(query_deleteHistory);
            int deleteHistoryRes = prepDeleteHistoryStmt.executeUpdate();

            System.out.println("Delete history: " + query_deleteHistory);

            //Deleting file that is related to current PP Asset Tag
            String query_deleteFile= "delete from upload where pp_asset_tag = '" + assetTag + "';";

            PreparedStatement prepDeleteFileStmt = connect.prepareStatement(query_deleteFile);
            int deleteFileRes = prepDeleteFileStmt.executeUpdate();

            System.out.println("Delete drive: " + query_deleteDrive);


            result = true;

            prepDeleteDriveStmt.close();
            prepDeleteHistoryStmt.close();

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
