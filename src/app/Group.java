package app;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Group extends HttpServlet implements mysql_credentials {
    private String username;
    private String eMessage;
    private String groupName;

    public Group() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        this.username = request.getParameter("username");

        JSONObject json = new JSONObject();

        if(getGroup()) {
            json.put("result", "Yes");
            json.put("group_name", this.groupName);
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
    private boolean getGroup() {
        boolean result = false;
        Connection connect = null;

        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT group_name FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else

            this.groupName = rs.getString("group_name");

            rs.close();
            prepStatement.close();
            result = true;
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
