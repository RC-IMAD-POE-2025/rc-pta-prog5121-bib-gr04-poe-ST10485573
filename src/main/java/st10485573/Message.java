package st10485573;

import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.Random;

/**
 * Represents a single message in the QuickChat application.
 * Handles message creation, validation, hashing, sending, and storage.
 *
 * @author Funiwe Ngobeni
 */
public class Message {
    private final String MESSAGE_ID;
    private final String MESSAGE_RECIPIENT;
    private final String MESSAGE_PAYLOAD;
    private int MESSAGE_INDEX;
    private String MESSAGE_HASH;

    // Static counter for unique indexing of sent messages
    private static int messageDispatchCounter = 0;
    // Static storage for details of the most recently sent message
    private static String lastSentMessageInfo = "";

    private static final int MAX_PAYLOAD_LENGTH = 250;
    private static final Random idGeneratorRandom = new Random();

    /**
     * Constructs a new Message.
     *
     * @param recipient The recipient's cellphone number.
     * @param payload   The content of the message.
     */
    public Message(final String recipient, final String payload) {
        // Generate a 10-digit random number string for MESSAGE_ID
        this.MESSAGE_ID = String.format("%010d", Math.abs(idGeneratorRandom.nextLong() % 10000000000L));
        this.MESSAGE_RECIPIENT = recipient;
        this.MESSAGE_PAYLOAD = payload;
        this.MESSAGE_INDEX = 0; // Initial index for a new (unsent) message
        this.MESSAGE_HASH = "";   // Initial hash is empty
    }

    // --- Getters ---
    public String getMessageID() { return MESSAGE_ID; }
    public String getMessageRecipient() { return MESSAGE_RECIPIENT; }
    public String getMessagePayload() { return MESSAGE_PAYLOAD; }
    public int getMessageIndex() { return MESSAGE_INDEX; }
    public String getMessageHash() { return MESSAGE_HASH; }

    /**
     * Validates the format of a given message ID (10 digits).
     *
     * @param id The message ID string.
     * @return true if valid, false otherwise.
     */
    public boolean checkMessageID(final String id) {
        if (id == null) {
            return false;
        }
        return id.matches("\\d{10}");
    }

    /**
     * Validates the message payload length.
     *
     * @param payload The message content.
     * @return A status string: "Message ready to send." or an error message with details.
     */
    public String validatePayloadLength(final String payload) {
        if (payload == null) {
            return "Message exceeds " + MAX_PAYLOAD_LENGTH + " characters by " + (0 - MAX_PAYLOAD_LENGTH) + ", please reduce size.";
        }
        if (payload.length() <= MAX_PAYLOAD_LENGTH) {
            return "Message ready to send.";
        } else {
            int excess = payload.length() - MAX_PAYLOAD_LENGTH;
            return "Message exceeds " + MAX_PAYLOAD_LENGTH + " characters by " + excess + ", please reduce size.";
        }
    }

    /**
     * Validates the recipient's cellphone number format (+27 followed by 9 digits).
     *
     * @param recipient The cellphone number.
     * @return A status string: "Cell phone number successfully captured." or an error message.
     */
    public String validateRecipientNumber(final String recipient) {
        if (recipient == null || recipient.isBlank()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        String regexSA = "^\\+27[0-9]{9}$";
        if (Pattern.matches(regexSA, recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Creates a message hash.
     * Format: FirstTwoCharsOfID:Index:FirstWordOfPayloadLastWordOfPayload (ALL CAPS).
     *
     * @param id      The message ID.
     * @param index   The message index.
     * @param payload The message content.
     * @return The uppercase hash string, or empty if inputs are invalid.
     */
    public String createMessageHash(final String id, int index, final String payload) {
        if (id == null || id.length() < 2 || payload == null) {
            return ""; // Invalid inputs for hash generation
        }

        String idStart = id.substring(0, 2);
        String content = payload.trim();

        if (content.isEmpty()) {
            return (idStart + ":" + index + ":").toUpperCase();
        }

        String[] words = content.split("\\s+");
        String first = words[0];
        String last = words[words.length - 1];

        return (idStart + ":" + index + ":" + first + last).toUpperCase();
    }

    /**
     * Processes the message for sending: validates, assigns index, generates hash.
     *
     * @return A status string: "Message successfully sent." or an error message.
     */
    public String sentMessage() {
        if (this.MESSAGE_PAYLOAD == null || this.MESSAGE_PAYLOAD.trim().isEmpty()) {
            return "Failed to send message: Message content cannot be empty";
        }
        String payloadValMsg = validatePayloadLength(this.MESSAGE_PAYLOAD);
        if (!payloadValMsg.equals("Message ready to send.")) {
            if (this.MESSAGE_PAYLOAD.length() > MAX_PAYLOAD_LENGTH) {
                 return "Failed to send message: Payload too long";
            }
            return payloadValMsg; // More specific error from validation
        }

        String recipientValMsg = validateRecipientNumber(this.MESSAGE_RECIPIENT);
        if (!recipientValMsg.equals("Cell phone number successfully captured.")) {
            return "Failed to send message: Invalid recipient";
        }

        if (!checkMessageID(this.MESSAGE_ID)) { // Should be true by construction
            return "Failed to send message: Invalid message ID (system error)";
        }

        messageDispatchCounter++;
        this.MESSAGE_INDEX = messageDispatchCounter;
        this.MESSAGE_HASH = createMessageHash(this.MESSAGE_ID, this.MESSAGE_INDEX, this.MESSAGE_PAYLOAD);

        lastSentMessageInfo = String.format("ID: %s, Recipient: %s, Payload: \"%s\", Hash: %s, Index: %d",
                this.MESSAGE_ID, this.MESSAGE_RECIPIENT, this.MESSAGE_PAYLOAD, this.MESSAGE_HASH, this.MESSAGE_INDEX);

        return "Message successfully sent.";
    }

    /**
     * Returns details of the last successfully sent message.
     *
     * @return Details string or "No messages sent".
     */
    public String printMessages() {
        if (messageDispatchCounter == 0 || lastSentMessageInfo.isEmpty()) {
            return "No messages sent";
        }
        return lastSentMessageInfo;
    }

    /**
     * Returns the total number of successfully sent messages.
     *
     * @return Total sent messages count.
     */
    public static int returnTotalMessages() {
        return messageDispatchCounter;
    }

    /**
     * Stores the current message to a JSON file (message_INDEX.json).
     *
     * @return Status string: "Message successfully stored." or an error message.
     */
    public String storeMessage() {
        JSONObject msgJson = new JSONObject();
        msgJson.put("MESSAGE_ID", this.MESSAGE_ID);
        msgJson.put("MESSAGE_RECIPIENT", this.MESSAGE_RECIPIENT);
        msgJson.put("MESSAGE_PAYLOAD", this.MESSAGE_PAYLOAD);
        msgJson.put("MESSAGE_INDEX", this.MESSAGE_INDEX);
        msgJson.put("MESSAGE_HASH", this.MESSAGE_HASH);

        String fileName = "message_" + this.MESSAGE_INDEX + ".json";
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(msgJson.toJSONString());
            return "Message successfully stored.";
        } catch (IOException e) {
            System.err.println("File storage error: " + e.getMessage());
            return "Failed to store message: IO Exception.";
        }
    }

    /**
     * Returns a notification string confirming Message ID generation.
     *
     * @return Formatted string with the message ID.
     */
    public String getGeneratedIdNotification() {
        return "Message ID generated: " + this.MESSAGE_ID;
    }

    /**
     * Resets static counters for testing purposes.
     */
    public static void resetMessageCounterForTesting() {
        messageDispatchCounter = 0;
        lastSentMessageInfo = "";
    }
}
