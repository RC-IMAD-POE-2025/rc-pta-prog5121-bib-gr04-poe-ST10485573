package st10485573test;

import st10485573.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the updated Message class.
 * This class now only tests the functionality that is still inside Message.java,
 * like the constructor and hash creation.
 *
 * @author Funiwe Ngobeni
 */
public class MessageTest {

    @Test
    public void testMessageConstructor_InitializesPropertiesCorrectly() {
        // Test if the constructor sets up the message details as expected
        Message msg = new Message("Funiwe", "+27123456789", "Hello World");

        assertNotNull(msg.getMessageID(), "Message ID should be auto-generated and not null.");
        assertTrue(msg.getMessageID().matches("\\d{10}"), "Message ID should be 10 digits long.");
        assertEquals("Funiwe", msg.getMessageSender(), "Sender should match the constructor argument.");
        assertEquals("+27123456789", msg.getMessageRecipient(), "Recipient should match the constructor argument.");
        assertEquals("Hello World", msg.getMessagePayload(), "Payload should match the constructor argument.");
        assertEquals("New", msg.getMessageStatus(), "A new message should have the status 'New'.");
    }

    @Test
    public void testCreateMessageHash_WithSpecifiedData_ReturnsCorrectHash() {
        // This test makes sure the hash is created in the correct format
        // We use the special constructor here to create a message with a predictable ID
        Message msg = new Message("0012345678", "Funiwe", "+27718693002", "Hi Mike, can you join us for dinner tonight", "Sent");

        // The hash format is: FirstTwoOfID:FirstInitialOfSender:FirstWordLastWord
        String expectedHash = "00:F:HITONIGHT";
        assertEquals(expectedHash, msg.createMessageHash(), "The generated hash should be in the correct format.");
    }

    @Test
    public void testCreateMessageHash_WithOneWordPayload_ReturnsCorrectHash() {
        // Test what happens if the message is only one word
        Message msg = new Message("AB98765432", "Funiwe", "+27821112222", "Greetings", "Sent");
        
        // If there's only one word, it should be used for both first and last word in the hash
        String expectedHash = "AB:F:GREETINGSGREETINGS";
        assertEquals(expectedHash, msg.createMessageHash(), "Hash with a single word payload should use that word twice.");
    }
}
