package app.servlets;

import app.DataFoundObject;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by zgraham on 4/13/17.
 */
public class DriveSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     **/
    public DriveSearchServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String assetTag = request.getParameter("pp_asset_tag");
        String serialNumber = request.getParameter("serial_number");
        String customerName = request.getParameter("customer_name");
        String driveState = request.getParameter("drive_state");
        String driveLocation = request.getParameter("drive_location");
        String tableName = request.getParameter("tableName");
        String essential = request.getParameter("essential");

        DataFoundObject dfo = new DataFoundObject(assetTag, serialNumber, customerName, driveState, driveLocation, tableName, essential);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (dfo.foundDataSuccessfully()) { //
            response.getWriter().write(dfo.getData());
        } else {
            response.getWriter().write(new JSONObject().put("message", dfo.getErrorMessage()).toString());
        }

        response.flushBuffer();
    }
}
