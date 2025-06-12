package st10485573;

import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the user interface for messaging features using JOptionPane dialogs.
 * This is where the user will interact with the chat app after logging in.
 *
 * @author Funiwe Ngobeni
 */
public class MessageGUI {

    // We need the username of the person who logged in
    private final String loggedInUser;
    // This object will handle all our arrays and reports
    private final Report reportManager;

    /**
     * Constructor that takes the logged-in user's name.
     */
    public MessageGUI(String username) {
        this.loggedInUser = username;
        this.reportManager = new Report(); // Create our report manager
    }

    /**
     * Starts and manages the main application loop.
     * This method shows the main menu and handles user choices.
     */
    public void startMessagingInteraction() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat, " + loggedInUser + "!", "Funiwe POE", JOptionPane.INFORMATION_MESSAGE);

        boolean continueMessaging = true;
        while (continueMessaging) {
            String[] menuOptions = {"Send a Message", "Show Reports", "Quit"};
            int menuChoice = JOptionPane.showOptionDialog(null,
                    "What would you like to do?",
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menuOptions, menuOptions[0]);

            switch (menuChoice) {
                case 0: // Send a Message
                    processNewMessage();
                    break;
                case 1: // Show Reports
                    showReportMenu();
                    break;
                case 2: // Quit
                default: // Also handles closing the dialog
                    continueMessaging = false;
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * This is the menu for all the Part 3 reports.
     */
    private void showReportMenu() {
        boolean continueReporting = true;
        while(continueReporting) {
            String[] reportOptions = {
                "Display Sent Senders/Recipients", 
                "Display Longest Message", 
                "Search Message by ID", 
                "Search Messages by Recipient", 
                "Delete Message by Hash", 
                "Generate Full Report",
                "Back to Main Menu"
            };
            int reportChoice = JOptionPane.showOptionDialog(null,
                    "Please choose a report to view:",
                    "Reports Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, reportOptions, reportOptions[0]);

            String output = "";
            String inputValue;

            switch(reportChoice) {
                case 0: // Display Sent Senders/Recipients
                    output = reportManager.displaySentMessageDetails();
                    break;
                case 1: // Display Longest Message
                    output = "Longest message found:\n\n\"" + reportManager.getLongestMessage() + "\"";
                    break;
                case 2: // Search by ID
                    inputValue = JOptionPane.showInputDialog("Enter the Message ID to search for:");
                    if (inputValue != null && !inputValue.isBlank()) {
                        output = reportManager.findMessageById(inputValue);
                    }
                    break;
                case 3: // Search by Recipient
                    inputValue = JOptionPane.showInputDialog("Enter the Recipient's number to search for:");
                     if (inputValue != null && !inputValue.isBlank()) {
                        output = reportManager.findMessagesByRecipient(inputValue);
                    }
                    break;
                case 4: // Delete by Hash
                    inputValue = JOptionPane.showInputDialog("Enter the full Message Hash to delete:");
                     if (inputValue != null && !inputValue.isBlank()) {
                        output = reportManager.deleteMessageByHash(inputValue.toUpperCase());
                    }
                    break;
                case 5: // Full Report
                    output = reportManager.generateFullReport();
                    break;
                case 6: // Back to Main Menu
                default:
                    continueReporting = false;
                    break;
            }

            if (continueReporting && (output != null && !output.isEmpty())) {
                JOptionPane.showMessageDialog(null, output, "Report Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the process for a single new message.
     */
    private void processNewMessage() {
        String recipient = JOptionPane.showInputDialog(null, "Enter recipient's cell number (e.g., +27718693002):", "New Message - Recipient", JOptionPane.PLAIN_MESSAGE);
        if (recipient == null) return; // User cancelled

        String payload = JOptionPane.showInputDialog(null, "Enter your message (max 250 characters):", "New Message - Payload", JOptionPane.PLAIN_MESSAGE);
        if (payload == null) return; // User cancelled

        // Create the message object with the logged-in user as the sender
        Message currentMessage = new Message(loggedInUser, recipient, payload);
        JOptionPane.showMessageDialog(null, "Message created with ID: " + currentMessage.getMessageID(), "Message ID", JOptionPane.INFORMATION_MESSAGE);
        
        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int actionChoice = JOptionPane.showOptionDialog(null,
                "Choose an action for this message:",
                "Message Action",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (actionChoice) {
            case 0: // Send
                currentMessage.setMessageStatus("Sent");
                reportManager.addMessage(currentMessage);
                storeMessageToJSON(currentMessage); // *** NEW: SAVE JSON ON SEND ***
                JOptionPane.showMessageDialog(null, "Message sent, saved to JSON, and added to reports!", "Sent", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1: // Store
                currentMessage.setMessageStatus("Stored");
                reportManager.addMessage(currentMessage);
                storeMessageToJSON(currentMessage); // This was already here
                JOptionPane.showMessageDialog(null, "Message stored, saved to JSON, and added to reports!", "Stored", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2: // Disregard
            default:
                currentMessage.setMessageStatus("Disregarded");
                reportManager.addMessage(currentMessage);
                // We don't save a JSON for disregarded messages
                JOptionPane.showMessageDialog(null, "Message disregarded and added to reports.", "Disregarded", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    /**
     * Saves a message object to a JSON file.
     * Reference: This method was generated with assistance from an AI tool (ChatGPT) as per POE instructions.
     */
    private void storeMessageToJSON(Message msg) {
        JSONObject msgJson = new JSONObject();
        msgJson.put("MESSAGE_ID", msg.getMessageID());
        msgJson.put("MESSAGE_SENDER", msg.getMessageSender());
        msgJson.put("MESSAGE_RECIPIENT", msg.getMessageRecipient());
        msgJson.put("MESSAGE_PAYLOAD", msg.getMessagePayload());
        msgJson.put("MESSAGE_STATUS", msg.getMessageStatus());

        // We can name the file by its ID to make sure it's unique
        String fileName = "message_" + msg.getMessageID() + ".json";
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(msgJson.toJSONString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not save message to JSON file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
