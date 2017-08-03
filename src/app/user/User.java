package app.user;

import javax.servlet.http.Cookie;
import java.sql.*;

import db_credentials.mysql_credentials;

public class User implements mysql_credentials{

    private String username;
    private Cookie loginCookie = null;
    private String errorMessage = null;

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
        String result = "";
        Connection connect = null;

        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT group_name FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            result = rs.getString("group_name");

            rs.close();
            prepStatement.close();
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    public String toggleNotifications() {
        if (this.loginCookie == null)
            return "Error";

        String toggleTo = (usingNotifications().equalsIgnoreCase("Yes") ? "No" : "Yes");
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "UPDATE user_info SET notification = ? WHERE username = ?;";
            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, toggleTo);
            prepStatement.setString(2, this.username);
            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return "Success";
    }

    public String usingNotifications() {
        if (this.loginCookie == null)
            return "Error";

        boolean result = false;
        Connection connect = null;

        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT notification FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("notification").equalsIgnoreCase("Yes");

            rs.close();
            prepStatement.close();
        } catch (SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (result ? "Yes" : "No");
    }

    public String isAdmin() {
        if (this.loginCookie == null)
            return "Error";

        boolean result = false;
        Connection connect = null;

        try {
            connect = DriverManager.getConnection(db_url, user_name, password);
            String query = "SELECT admin FROM user_info WHERE username = ?;";

            PreparedStatement prepStatement = connect.prepareStatement(query);
            prepStatement.setString(1, this.username);
            ResultSet rs = prepStatement.executeQuery();

            // Move pointer to first row of the result set
            rs.first();
            // result is true if "Yes" and false if anything else
            result = rs.getString("admin").equalsIgnoreCase("Yes");

            rs.close();
            prepStatement.close();
        } catch( SQLException e) {
            this.errorMessage = e.getMessage();
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (result ? "Yes" : "No");
    }
    public Cookie getLoginCookie() {
        return this.loginCookie;
    }
}
