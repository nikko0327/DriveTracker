package app.servlets;

import app.user.User;
import db_credentials.mysql_credentials;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoServlet extends HttpServlet implements mysql_credentials {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            String action = request.getParameter("action");
            String username = request.getParameter("username");

            User user = new User(username, request.getCookies());

            JSONObject json = new JSONObject();

            // System.out.println(String.format("username: %s| notification %s | groupname: %s | admin: %s", username, user.usingNotifications(), user.getGroup(), user.isAdmin()));

            switch (action) {
                case "isAdmin":
                    json.put("result", user.isAdmin());
                    break;
                case "notifications":
                    json.put("result", user.usingNotifications());
                    break;
                case "toggleNotify":
                    json.put("result", user.toggleNotifications());
                    break;
                case "getGroup":
                    json.put("result", user.getGroup());
                    break;
                default:
                    // let client know there is an error by placing Error in the result json
                    json.put("result", "Error");
                    json.put("error", user.getErrorMessage());
                    break;
            }

            //send requested info back to user
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
