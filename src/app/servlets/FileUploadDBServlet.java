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
import java.sql.Statement;


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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        this.filePart = request.getPart("file");
        this.ppAssetTag = request.getParameter("assetTag");
        this.description = request.getParameter("description");

        JSONObject json = new JSONObject();
        if (uploadFileAndData()) {
            json.put("result", "success");
        } else
            json.put("result", eMessage);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    private boolean uploadFileAndData() {
        boolean result = false;
        InputStream pdfFileBytes = null;

        Connection connect = null;
        PreparedStatement ps = null;

        try {
            //if file is NOT a PDF just return false
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

            try {
                connect = dataSource.getConnection();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
                return false;
            }

            Statement s = null;
            try {
                s = connect.createStatement();
                //to create table with blob field (One time only)
                s.executeUpdate("CREATE TABLE upload (id int not null auto_increment, pp_asset_tag varchar (10) not null , file MEDIUMBLOB, description varchar(50) not null, Primary key (id))");
            } catch (Exception e) {
                System.out.println("Tables already created, skipping table creation process");
            } finally {
                try {
                    s.close();
                } catch (SQLException sql) {
                    sql.printStackTrace();
                }
            }

            int success = 0;
            ps = connect.prepareStatement("INSERT INTO upload VALUES(default,?,?,?)");
            ps.setString(1, ppAssetTag);
            ps.setBytes(2, bytes);    //Storing binary data in blob field.
            ps.setString(3, description);
            success = ps.executeUpdate();

            if (success >= 1) {
                System.out.println("File Stored");
            }

            result = true;

        } catch (SQLException | IOException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, ps);
        }

        return result;
    }
}
