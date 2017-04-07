package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

@WebServlet("/FileUpload")
@MultipartConfig
public class FileUploadDBServlet extends HttpServlet implements mysql_credentials {

    private static final long serialVersionUID = 1L;

    private String eMessage;

    private Part filePart;
    private String assetAndPP;
    private String description;

    protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        this.filePart = request.getPart("file");
        this.assetAndPP = "PS" + request.getParameter("assetTag");
        this.description = request.getParameter("description");

        //System.out.println(assetAndPP);
        //System.out.println(description);

        JSONObject json = new JSONObject();
        if(uploadFileAndData()) {
            json.put("result", "success");
        }
        else
            json.put("result", eMessage);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        response.flushBuffer();
    }

    private boolean uploadFileAndData() {
        boolean result = false;
        InputStream pdfFileBytes = null;
        Connection con = null;
        Statement stmt = null;

        try {
            //if file is NOT a PDF just return false
            if (!filePart.getContentType().equals("application/pdf"))
                return false;
            //if file is too BIG then just return false
            else if (filePart.getSize() > 1048576 )//2mb
                return false;

            pdfFileBytes = filePart.getInputStream();  // to get the body of the request as binary data

            final byte[] bytes = new byte[pdfFileBytes.available()];
            pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.


            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(db_url, user_name, password);
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }

            try {
                stmt = con.createStatement();
                //to create table with blob field (One time only)
                stmt.executeUpdate("CREATE TABLE upload (id int not null auto_increment, pp_asset_tag varchar (10) not null , file MEDIUMBLOB, description varchar(50) not null, Primary key (id))");

            } catch (Exception e) {
                System.out.println("Tables already created, skipping table creation process");
            }

            int success=0;
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO upload VALUES(default,?,?,?)");
            pstmt.setString(1, assetAndPP);
            pstmt.setBytes(2,bytes);    //Storing binary data in blob field.
            pstmt.setString(3, description);
            success = pstmt.executeUpdate();

            if(success >= 1)
                System.out.println("File Stored");

            con.close();
            result = true;

        } catch (FileNotFoundException fnf) {
            eMessage = fnf.getMessage();
        } catch (SQLException e) {
            eMessage = e.getMessage();
        } catch (IOException e) {
            eMessage = e.getMessage();
        }
        finally {
            try {
                if(con != null)
                    con.close();
                if(pdfFileBytes != null)
                    pdfFileBytes.close();
            } catch(SQLException se) {
                eMessage = se.getMessage();
                se.printStackTrace();
            } catch(IOException e) {
                eMessage = e.getMessage();
            }
        }

        return result;
    }
//    protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
//        //response.setContentType("text/html;charset=UTF-8");
//        boolean result = false;
//        response.setContentType("application/json");
//
//        final Part filePart = request.getPart("file");
//        String assetTag = request.getParameter("assetTag");
//        String description = request.getParameter("description");
//        String assetAndPP = "PS" + assetTag;
//
//        System.out.println(assetTag);
//        System.out.println(description);
//
//
//
//        InputStream pdfFileBytes = null;
//        final PrintWriter writer = response.getWriter();
//
//        try {
//
//            if (!filePart.getContentType().equals("application/pdf")) {
//                //writer.println("<br/> Invalid File");
//                return;
//            }
//
//            else if (filePart.getSize() > 1048576 ) { //2mb
//                {
//                    //writer.println("<br/> File size is too big");
//                    return;
//                }
//            }
//
//            pdfFileBytes = filePart.getInputStream();  // to get the body of the request as binary data
//
//            final byte[] bytes = new byte[pdfFileBytes.available()];
//            pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.
//
//            Connection con = null;
//            Statement stmt = null;
//
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                con = DriverManager.getConnection(db_url, user_name, password);
//            } catch (Exception e) {
//                System.out.println(e);
//                System.exit(0);
//            }
//
//            try {
//                stmt = con.createStatement();
//                //to create table with blob field (One time only)
//                stmt.executeUpdate("CREATE TABLE upload (id int not null auto_increment, pp_asset_tag varchar (10) not null , file MEDIUMBLOB, description varchar(50) not null, Primary key (id))");
//
//            } catch (Exception e) {
//                System.out.println("Tables already created, skipping table creation process");
//            }
//
//            int success=0;
//            PreparedStatement pstmt = con.prepareStatement("INSERT INTO upload VALUES(default,?,?,?)");
//            pstmt.setString(1, assetAndPP);
//            pstmt.setBytes(2,bytes);    //Storing binary data in blob field.
//            pstmt.setString(3, description);
//            success = pstmt.executeUpdate();
//
//            if(success >= 1) System.out.println("File Stored");
//
//            con.close();
//            //writer.println("<br/> File Successfully Stored. You can now close this page and create/update a drive.");
//
//        } catch (FileNotFoundException fnf) {
//            //writer.println("You did not specify a file to upload");
//            //writer.println("<br/> ERROR: " + fnf.getMessage());
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            //writer.println("<br/> Upload Failed.");
//            e.printStackTrace();
//        } finally {
//
//            if (pdfFileBytes != null) {
//                pdfFileBytes.close();
//            }
//            if (writer != null) {
//                writer.close();
//            }
//        }
//        JSONObject json = new JSONObject();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(json.toString());
//        response.flushBuffer();
//    }
}
