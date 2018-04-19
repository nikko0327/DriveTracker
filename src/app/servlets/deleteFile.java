package app.servlets;

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

/**
 * Servlet implementation class deleteDrive
 */
public class deleteFile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String eMessage;

    private String id;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteFile() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        id = request.getParameter("id");

        JSONObject json = new JSONObject();

        if (removeFile()) {
            json.put("result", "success");
        } else {
            json.put("result", eMessage);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    @SuppressWarnings("Duplicates")
    public boolean removeFile() {
        boolean result = false;

        Connection connect = null;
        PreparedStatement ps = null;

        try {
            connect = dataSource.getConnection();
            System.out.println("Connection successful trying to delete file?");

            String query_deleteDrive = "DELETE FROM upload WHERE id = '" + this.id + "';";

            ps = connect.prepareStatement(query_deleteDrive);
            ps.executeUpdate();

            ps.close();
            System.out.println("Delete file: " + query_deleteDrive);
            result = true;
        } catch (SQLException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, ps);
        }

        return result;
    }
}
