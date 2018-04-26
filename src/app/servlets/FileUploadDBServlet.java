package app.servlets;

import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/FileUpload")
@MultipartConfig
public class FileUploadDBServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String eMessage = "";

    private Part filePart;
    private String ppAssetTag;
    private String description;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        System.out.println("--- file upload ---");
        try {
            response.setContentType("application/json");

            this.filePart = request.getPart("file");
            this.ppAssetTag = request.getParameter("assetTag");
            this.description = request.getParameter("description");

            JSONObject json = new JSONObject();
            if (uploadFileAndData()) {
                json.put("result", "success");
            } else {
                json.put("result", eMessage);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            response.flushBuffer();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @SuppressWarnings("Duplicates")
    private boolean uploadFileAndData() {
        boolean result = false;
        InputStream pdfFileBytes = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // if file is NOT a PDF just return false
            if (!filePart.getContentType().equals("application/pdf")) {
                eMessage = "File type must be pdf!";
                return false;
            }
            //if file is too BIG then just return false
            else if (filePart.getSize() > 1048576) { //2mb
                eMessage = "Maximum file size is 2 MB!";
                return false;
            }
            pdfFileBytes = filePart.getInputStream();  // to get the body of the request as binary data

            final byte[] bytes = new byte[pdfFileBytes.available()];
            pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.

            con = dataSource.getConnection();

            ps = con.prepareStatement("INSERT INTO upload VALUES(default,?,?,?)");
            ps.setString(1, ppAssetTag);
            ps.setBytes(2, bytes);    //Storing binary data in blob field.
            ps.setString(3, description);

            if (ps.executeUpdate() >= 1) {
                System.out.println("File Stored");
            }

            result = true;
        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(con, ps, pdfFileBytes);
        }

        return result;
    }
}
