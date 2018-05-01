package app;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class EmailNotifier {
    private HistoryInfo info;
    private String from;
    private String host;
    private Properties props;
    private String eMessage;
    private String serverUrl = "http://lv-drivetrac.corp.proofpoint.com/DriveTracker/";

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
        System.out.println("-- Trying to send email... --");
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

            //System.out.println("Email message: " + message.toString());

            Transport.send(message);

            result = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            eMessage = "Unable to Send Message";
        }
        if(result == false) {
            System.out.println("-- Email sending failed. --");
        } else {
            System.out.println("-- Email supposedly sent --");
        }
        return result;
    }

    private void loadRecipients() {
        Connection connect = null;
        PreparedStatement psSelectUser = null;
        ResultSet rs = null;

        try {
            connect = db_credentials.DB.getConnection();

            String query_selectUsers = "SELECT username FROM user_info WHERE notification='Yes'";
            if (this.essential.equalsIgnoreCase("yes")) {
                // if in essentials, mail to nlee@proofpoint
                query_selectUsers += " AND group_name IN ('" + this.groupNameEssentials + "', '" + this.groupNameMaster + "');";
            } else if (this.essential.equalsIgnoreCase("no")) {
                // if
                query_selectUsers += " AND group_name IN ('" + this.groupNameEnterprise + "', '" + this.groupNameMaster + "');";
            } else {
                query_selectUsers += " AND group_name='" + this.groupNameMaster + "';";
            }

            psSelectUser = connect.prepareStatement(query_selectUsers);
            rs = psSelectUser.executeQuery();

            while (rs.next()) {
                recipients.add(new InternetAddress(rs.getString("username") + "@proofpoint.com"));
            }

        } catch (Exception e) {
            eMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            db_credentials.DB.closeResources(connect, psSelectUser, rs);
        }
    }
}
