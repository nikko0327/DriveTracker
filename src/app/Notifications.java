package app;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Notifications extends HttpServlet implements mysql_credentials {

    private String username;
    private String eMessage;
    private String toggle;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notifications() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        this.username = request.getParameter("username");
        this.toggle = request.getParameter("toggle");


        JSONObject json = new JSONObject();

        if(notificationsEnabled()) {
            json.put("result", "Yes");
        }
        else
            json.put("result", eMessage);

        // send json back to user
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    @SuppressWarnings("Duplicates")
    private boolean notificationsEnabled() {
        boolean result = false;
        Connection connect = null;

        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT notification FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("notification").equalsIgnoreCase("Yes");

            rs.close();
            prepStatement.close();

            if (this.toggle.equalsIgnoreCase("Yes")) {
                String toggleTo = (result ? "No" : "Yes");

                String query2 = "UPDATE user_info SET notification = ? WHERE username = ?;";
                PreparedStatement prepStatement2 = connect.prepareStatement(query2);
                prepStatement2.setString(1, toggleTo);
                prepStatement2.setString(2, this.username);

                prepStatement2.executeUpdate();

                prepStatement2.close();

            }


        } catch (SQLException e) {
            this.eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException e) {
                this.eMessage = e.getMessage();
                e.printStackTrace();
            }
        }
        return result;
    }
}
