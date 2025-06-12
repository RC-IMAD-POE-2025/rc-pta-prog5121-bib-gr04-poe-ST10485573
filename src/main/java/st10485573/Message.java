package st10485573;

import java.util.Random;

/**
 * Represents a single message in the QuickChat application.
 * This class just holds the information for one message.
 *
 * @author Funiwe Ngobeni
 */
public class Message {

    // All the details for a message
    private String messageID;
    private String messageRecipient;
    private String messagePayload;
    private String messageSender;
    private String messageHash;
    private String messageStatus; // "New", "Sent", "Stored", "Disregarded"

    // A random number generator to create unique IDs
    private static final Random idGenerator = new Random();

    /**
     * Constructor to create a new message.
     * It automatically generates a unique 10-digit ID.
     */
    public Message(String sender, String recipient, String payload) {
        // We need to know who sent the message!
        this.messageSender = sender;
        this.messageRecipient = recipient;
        this.messagePayload = payload;

        // Create a random 10-digit number for the ID
        this.messageID = String.format("%010d", Math.abs(idGenerator.nextLong() % 10000000000L));
        
        // When a message is new, it has no hash or status yet
        this.messageHash = "";
        this.messageStatus = "New";
    }
    
    // This is a special constructor for our tests, so we can use predictable data
    public Message(String id, String sender, String recipient, String payload, String status) {
        this.messageID = id;
        this.messageSender = sender;
        this.messageRecipient = recipient;
        this.messagePayload = payload;
        this.messageStatus = status;
        // When we create the hash, we'll use the details from this message
        this.messageHash = createMessageHash(); 
    }

    // --- These are the 'getter' methods to get the message details ---
    public String getMessageID() { return messageID; }
    public String getMessageRecipient() { return messageRecipient; }
    public String getMessagePayload() { return messagePayload; }
    public String getMessageSender() { return messageSender; }
    public String getMessageHash() { return messageHash; }
    public String getMessageStatus() { return messageStatus; }

    // --- These are the 'setter' methods to update the message details ---
    public void setMessageHash(String hash) { this.messageHash = hash; }
    public void setMessageStatus(String status) { this.messageStatus = status; }

    /**
     * Creates a message hash based on the POE requirements.
     * Format: FirstTwoCharsOfID:SenderFirstInitial:FirstWordOfPayloadLastWordOfPayload (ALL CAPS).
     */
    public final String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messagePayload == null || messageSender == null || messageSender.isEmpty()) {
            return ""; // Can't make a hash if data is missing
        }

        String idStart = messageID.substring(0, 2);
        String senderInitial = messageSender.substring(0, 1);
        String content = messagePayload.trim();

        if (content.isEmpty()) {
            return (idStart + ":" + senderInitial + ":").toUpperCase();
        }

        String[] words = content.split("\\s+");
        String first = words[0];
        String last = words.length > 1 ? words[words.length - 1] : first;

        // POE format: 00:0:HITHANKS (ID part : message number : words)
        // I am interpreting "message number" as the sender's initial for better tracking
        return (idStart + ":" + senderInitial + ":" + first + last).toUpperCase();
    }
}
