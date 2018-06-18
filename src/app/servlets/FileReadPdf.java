package app.servlets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class GetDetails
 */
@WebServlet("/FileReadPdf")
public class FileReadPdf extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileReadPdf() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        System.out.println("--- read pdf ---");
        ServletOutputStream sos = null;
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String assetTag = request.getParameter("assetTag") != null ? request.getParameter("assetTag") : "NA";
            String description = request.getParameter("description") != null ? request.getParameter("description") : "NA";
            System.out.println(description);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; description=" + assetTag + ".pdf");

            sos = response.getOutputStream();

            connect = dataSource.getConnection();

            ps = connect.prepareStatement("select file from upload where pp_asset_tag=? and description=?");
            ps.setString(1, assetTag.trim());
            ps.setString(2, description.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                sos.write(rs.getBytes("file"));
            }

            sos.flush();

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            db_credentials.DB.closeResources(connect, ps, rs, sos);
//            try {
//                sos.close();
//            } catch (Exception e) {
//                throw new ServletException(e);
//            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
    }

}
