package app;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/* This class is only used to check to see if a certain user is an admin or not - zgraham 7-27-17 */
public class Admin extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;

    private String eMessage; //error message
    private String username; //username, duh
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
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

        if(isAdmin()) {
            json.put("result", "Yes");
        }
        else
            json.put("result", "No");

        // send json back to user
        sendResponse(response, json);
    }

    //method checks username in the mysql database and returns true if user is an admin and false if not
    private boolean isAdmin() {
        boolean result = false;
        Connection connect = null;

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT admin FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("admin").equalsIgnoreCase("Yes");

            rs.close();
            prepStatement.close();
        } catch( SQLException e) {
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

    private void sendResponse(HttpServletResponse response, JSONObject json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }
}
