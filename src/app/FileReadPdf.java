package app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db_credentials.mysql_credentials;

/**
 * Servlet implementation class GetDetails
 */
@WebServlet("/FileReadPdf")
public class FileReadPdf extends HttpServlet implements mysql_credentials {
    private static final long serialVersionUID = 1L;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String assetTag = request.getParameter("assetTag") != null ? request.getParameter("assetTag"):"NA";
        String description = request.getParameter("description") != null ? request.getParameter("description"):"NA";
        System.out.println(description);

        ServletOutputStream sos;
        Connection con = null;
        PreparedStatement pstmt = null;

        response.setContentType("application/pdf");

        response.setHeader("Content-disposition","inline; description="+assetTag+".pdf" );

        sos = response.getOutputStream();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, user_name, password);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        ResultSet rset=null;
        try {
            pstmt = con.prepareStatement("Select file from upload where pp_asset_tag=? and description=?");
            pstmt.setString(1, assetTag.trim());
            pstmt.setString(2, description.trim());
            rset = pstmt.executeQuery();

            if (rset.next())
                sos.write(rset.getBytes("file"));
            else
                return;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sos.flush();
        sos.close();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
