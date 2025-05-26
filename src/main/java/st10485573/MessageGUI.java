package st10485573;

import javax.swing.JOptionPane;

/**
 * Handles the user interface for messaging features using JOptionPane dialogs.
 *
 * @author Funiwe Ngobeni
 */
public class MessageGUI {

    /**
     * Starts and manages the interactive messaging session.
     * This method orchestrates the process of sending/storing multiple messages.
     */
    public void startMessagingInteraction() {
        JOptionPane.showMessageDialog(null,
                "Welcome to QuickChat",
                "Funiwe Part 2", JOptionPane.INFORMATION_MESSAGE);

        boolean continueMessaging = true;
        while (continueMessaging) {
            String choice = JOptionPane.showInputDialog(null,
                    "Messaging Menu:\n1. Send Messages\n2. Show recently sent messages\n3. Quit",
                    "Main Menu", JOptionPane.QUESTION_MESSAGE);

            if (choice == null) { // User cancelled main menu
                continueMessaging = false;
                break;
            }

            switch (choice) {
                case "1": // Process Messages
                    processMessageBatch();
                    break;

                case "2": // Show Last Sent Message Details
                    JOptionPane.showMessageDialog(null, "Coming Soon in Part3", "Last Sent Message", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case "3": // Exit Messaging
                    continueMessaging = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select an option from the menu.", "Menu Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Exiting messaging feature.", "QuickChat Messaging", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the processing of a batch of messages.
     */
    private void processMessageBatch() {
        String numMessagesStr = JOptionPane.showInputDialog(null,
                "How many messages would you like to process?",
                "Number of Messages", JOptionPane.QUESTION_MESSAGE);

        if (numMessagesStr == null || numMessagesStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Number of messages not provided. Returning to menu.", "Input Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numMessages;
        try {
            numMessages = Integer.parseInt(numMessagesStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number entered. Please use digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (numMessages <= 0) {
            JOptionPane.showMessageDialog(null, "Please enter a positive number of messages.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            JOptionPane.showMessageDialog(null, "Processing Message " + (i + 1) + " of " + numMessages, "Message Progress", JOptionPane.INFORMATION_MESSAGE);

            String recipient = JOptionPane.showInputDialog(null, "Enter recipient's cell number (e.g., +27718693002):", "Message " + (i + 1) + " - Recipient", JOptionPane.PLAIN_MESSAGE);
            if (recipient == null) {
                JOptionPane.showMessageDialog(null, "Recipient input cancelled for Message " + (i + 1) + ". Skipping.", "Cancelled", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            String payload = JOptionPane.showInputDialog(null, "Enter message payload:", "Message " + (i + 1) + " - Payload", JOptionPane.PLAIN_MESSAGE);
            if (payload == null) {
                JOptionPane.showMessageDialog(null, "Payload input cancelled for Message " + (i + 1) + ". Skipping.", "Cancelled", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            Message currentMessage = new Message(recipient, payload);
            JOptionPane.showMessageDialog(null, currentMessage.getGeneratedIdNotification(), "Message ID", JOptionPane.INFORMATION_MESSAGE);

            // Perform validations before offering actions
            String recipientValidationMsg = currentMessage.validateRecipientNumber(currentMessage.getMessageRecipient());
            if (!recipientValidationMsg.equals("Cell phone number successfully captured.")) {
                JOptionPane.showMessageDialog(null, "Validation Failed for Message " + (i + 1) + ":\n" + recipientValidationMsg, "Recipient Error", JOptionPane.ERROR_MESSAGE);
                continue; // Skip to next message
            }

            String payloadValidationMsg = currentMessage.validatePayloadLength(currentMessage.getMessagePayload());
            if (!payloadValidationMsg.equals("Message ready to send.")) {
                JOptionPane.showMessageDialog(null, "Validation Failed for Message " + (i + 1) + ":\n" + payloadValidationMsg, "Payload Error", JOptionPane.ERROR_MESSAGE);
                continue; // Skip to next message
            }

            String[] options = {"Send Message", "Store Message", "Disregard Message"};
            int actionChoice = JOptionPane.showOptionDialog(null,
                    "Choose an action for this message:\nTo: " + currentMessage.getMessageRecipient() + "\nMessage: " + currentMessage.getMessagePayload().substring(0, Math.min(currentMessage.getMessagePayload().length(), 50)) + "...", // Show snippet
                    "Message " + (i + 1) + " - Action",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            String resultMessage;
            switch (actionChoice) {
                case 0: // Send Message
                    resultMessage = currentMessage.sentMessage();
                    JOptionPane.showMessageDialog(null, resultMessage, "Send Status", JOptionPane.INFORMATION_MESSAGE);
                    if (resultMessage.equals("Message successfully sent.")) {
                        JOptionPane.showMessageDialog(null, "Details of sent message:\n" + currentMessage.printMessages(), "Sent Message Details", JOptionPane.INFORMATION_MESSAGE);
                        String storeSentResult = currentMessage.storeMessage(); // Also store sent message
                        JOptionPane.showMessageDialog(null, "Sent message also stored: " + storeSentResult, "Store Status", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 1: // Store Message
                    resultMessage = currentMessage.storeMessage();
                    JOptionPane.showMessageDialog(null, resultMessage, "Store Status", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2: // Disregard Message
                    JOptionPane.showMessageDialog(null, "Message disregarded by user.", "Disregarded", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "No action selected for message " + (i + 1) + ".", "Action Skipped", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Batch processing complete. Total messages successfully sent in this session: " + Message.returnTotalMessages(), "Batch Summary", JOptionPane.INFORMATION_MESSAGE);
    }
}
