package app.user;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private String username;
    private Cookie loginCookie = null;
    private String errorMessage = null;

    @Resource(name = "jdbc/DriveTrackerDB")
    private DataSource dataSource;

    public User(String username, Cookie[] cookies) {
        this.username = username;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    this.loginCookie = cookie;
                    break;
                }
            }
        } else {
            //TODO: if user does not have cookies log them out
        }
    }

    private String assignGroup() {
        return "";
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getUsername() {
        return this.username;
    }

    public String getGroup() {

        String result = null;
        Connection connect = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connect = dataSource.getConnection();

            String query = "SELECT group_name FROM user_info WHERE username = ?;";

            ps = connect.prepareStatement(query);
            ps.setString(1, this.username);
            rs = ps.executeQuery();

            // Move pointer to first row of the result set
            rs.first();

            if (rs.getString("group_name") != null) {
                result = rs.getString("group_name");
            } else {
                this.errorMessage = "No group name found!";
                return "Error";
            }
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            db_credentials.DB.closeResources(connect, ps, rs);
        }
        //make sure we don't return null, otherwise we'll get errors
        return (result != null ? result : "Error");
    }

    public String toggleNotifications() {
        if (this.loginCookie == null) {
            return "Error";
        }

        String toggleTo = (usingNotifications().equalsIgnoreCase("Yes") ? "No" : "Yes");

        Connection connect = null;
        PreparedStatement ps = null;

        try {

            connect = dataSource.getConnection();

            String query = "UPDATE user_info SET notification = ? WHERE username = ?;";

            ps = connect.prepareStatement(query);

            ps.setString(1, toggleTo);
            ps.setString(2, this.username);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            db_credentials.DB.closeResources(connect, ps);
        }
        return "Success";
    }

    public String usingNotifications() {
        if (this.loginCookie == null)
            return "Error";

        boolean result = false;
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect = dataSource.getConnection();

            String query = "SELECT notification FROM user_info WHERE username = ?;";

            ps = connect.prepareStatement(query);
            ps.setString(1, this.username);
            rs = ps.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("notification").equalsIgnoreCase("Yes");
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            db_credentials.DB.closeResources(connect, ps, rs);
        }

        return (result ? "Yes" : "No");
    }

    public String isAdmin() {
        if (this.loginCookie == null)
            return "Error";

        boolean result = false;

        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connect = dataSource.getConnection();

            String query = "SELECT admin FROM user_info WHERE username = ?;";

            ps = connect.prepareStatement(query);
            ps.setString(1, this.username);

            rs = ps.executeQuery();
            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("admin").equalsIgnoreCase("Yes");

        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            db_credentials.DB.closeResources(connect, ps, rs);
        }
        return (result ? "Yes" : "No");
    }

    public Cookie getLoginCookie() {
        return this.loginCookie;
    }
}
