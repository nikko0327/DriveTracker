package app.servlets;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Servlet implementation class deleteDrive
 */
public class deleteFile extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private String id;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteFile() { super(); }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        id = request.getParameter("id");

        JSONObject json = new JSONObject();

        if(removeFile())
            json.put("result", "success");
        else
            json.put("result", eMessage);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    @SuppressWarnings("Duplicates")
    public boolean removeFile() {
        boolean result = false;

        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            String query_deleteDrive = "DELETE FROM upload WHERE id = '" + this.id + "';";

            PreparedStatement preparedStatement = connect.prepareStatement(query_deleteDrive);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            System.out.println("Delete file: " + query_deleteDrive);
            result = true;
        } catch(SQLException | ClassNotFoundException e) {
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
