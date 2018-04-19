package app;

import db_credentials.mysql_credentials;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class EmailNotifier implements mysql_credentials {
    HistoryInfo info;
    String from;
    String host;
    Properties props;
    String eMessage;
    String serverUrl = "http://lv-drivetrac.corp.proofpoint.com/DriveTracker/";

    private Set<InternetAddress> recipients;
    private String essential;

    private final String groupNameEnterprise = "Enterprise";
    private final String groupNameEssentials = "Essentials";
    private final String groupNameMaster = "Master";

    public EmailNotifier(HistoryInfo historyInfo, String essential) {
        this.essential = essential;
        recipients = new HashSet<InternetAddress>();
        loadRecipients();

        info = historyInfo;
        from = "DriveTracking@proofpoint.com";
        host = "smtp.us.proofpoint.com";

        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
    }

    public boolean send() {
        boolean result = false;

        Session mailSession = Session.getInstance(props);
        try {
            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(from));

            // check if there is anyone to send to, if not dont do it
            if (recipients.isEmpty()) {
                this.eMessage = "Warning: No one will receive an email for this particular action.";

                return false;
            }

            InternetAddress[] addresses = recipients.toArray(new InternetAddress[0]);
            message.addRecipients(Message.RecipientType.BCC, addresses);

            message.setSubject("[DriveTracking] (" + info.getAssetTag() + ") "
                    + info.getType() + " for " + info.getCustomerName());

            if (info.getType().equals("DELETED")) {
                message.setText("Drive removed from system with following details\n"
                        + "By " + info.getUpdatedBy() + "\n"
                        + "At " + info.getLastUpdated()
                );
            } else {
                message.setText("Drive " + info.getType().toLowerCase() + " in system with following details\n"
                        + "By " + info.getUpdatedBy() + "\n"
                        + "At " + info.getLastUpdated() + "\n\n"
                        + "PP Asset Tag: " + info.getAssetTag() + "\n"
                        + "Customer: " + info.getCustomerName() + "\n"
                        + "Drive Location: " + info.getDriveLocation() + "\n"
                        + "Drive State: " + info.getDriveState() + "\n"
                        + "Notes: " + info.getNotes() + "\n\n"
                        + "Go to " + serverUrl + "historyDrive.jsp?pp_asset_tag=" + info.getAssetTag() + " for more details."
                );
            }

            Transport.send(message);

            result = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            eMessage = "Unable to Send Message";
        }

        return result;
    }

    private void loadRecipients() {
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(db_url, user_name, password);

            String query_selectUsers = "SELECT username FROM user_info WHERE notification='Yes'";
            if (this.essential.equalsIgnoreCase("Yes")) {
                query_selectUsers += " AND group_name IN ('" + this.groupNameEssentials + "', '" + this.groupNameMaster + "');";
            } else if (this.essential.equalsIgnoreCase("No")) {
                query_selectUsers += " AND group_name IN ('" + this.groupNameEnterprise + "', '" + this.groupNameMaster + "');";
            } else {
                query_selectUsers += " AND group_name='" + this.groupNameMaster + "';";
            }

            PreparedStatement prepSelectUsersStmt = connect.prepareStatement(query_selectUsers);
            ResultSet rs = prepSelectUsersStmt.executeQuery();

            while (rs.next())
                recipients.add(new InternetAddress(rs.getString("username") + "@proofpoint.com"));

            rs.close();
            prepSelectUsersStmt.close();

        } catch (SQLException | ClassNotFoundException | AddressException e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (connect != null)
                    connect.close();
            } catch (SQLException se) {
                eMessage = se.getMessage();
                se.printStackTrace();
            }
        }
    }
}
