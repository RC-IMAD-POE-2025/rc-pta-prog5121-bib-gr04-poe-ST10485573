package st10485573test;

import st10485573.Message;
import st10485573.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Report class.
 * This class tests all the array and reporting functionality from Part 3.
 *
 * @author Funiwe Ngobeni
 */
public class ReportTest {

    private Report report;
    private Message msg1, msg2, msg3, msg4, msg5;

    // This method runs before each test to set up our test data
    @BeforeEach
    public void setUp() {
        report = new Report();
        
        // Using the exact test data from the POE PDF for Part 3
        // Student name Funiwe Ngobeni will be the sender
        msg1 = new Message("id1", "Funiwe", "+27834557896", "Did you get the cake?", "Sent");
        msg2 = new Message("id2", "Funiwe", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        msg3 = new Message("id3", "Funiwe", "+27834484567", "Yohoooo, I am at your gate.", "Disregarded");
        msg4 = new Message("id4", "Funiwe", "0838884567", "It is dinner time!", "Sent"); // This number format is invalid based on part 1, but we follow the POE test data.
        msg5 = new Message("id5", "Funiwe", "+27838884567", "Ok, I am leaving without you.", "Stored");

        // Add all messages to the report manager
        report.addMessage(msg1);
        report.addMessage(msg2);
        report.addMessage(msg3);
        report.addMessage(msg4);
        report.addMessage(msg5);
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // This test checks if the report shows the correct details for SENT messages only.
        String expected = "--- Sent Message Details ---\n" +
                          "Sender: Funiwe, Recipient: +27834557896\n" +
                          "Sender: Funiwe, Recipient: 0838884567\n";
        assertEquals(expected, report.displaySentMessageDetails());
    }

    @Test
    public void testDisplayTheLongestMessage() {
        // This test checks for the longest message payload overall.
        // The POE asks for the longest "sent" message, but the expected output from the
        // test data is the message "Where are you? You are late! I have asked you to be on time.",
        // which has a "Stored" status. The getLongestMessage() method now handles this correctly.
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, report.getLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        String expected = "Message Found!\n" +
                          "Recipient: 0838884567\n" +
                          "Message: It is dinner time!";
        assertEquals(expected, report.findMessageById("id4"));
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        // Search for all messages sent or stored to +27838884567
        String expected = "--- Messages for +27838884567 ---\n" +
                          "- Where are you? You are late! I have asked you to be on time.\n" +
                          "- Ok, I am leaving without you.\n";
        assertEquals(expected, report.findMessagesByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingMessageHash() {
        // Hash for msg2: ID part is "id", so starts with "ID". Sender is Funiwe. First/last words are WHERE/time.
        // So the hash is ID:F:WHEREtime.
        String hashToDelete = msg2.getMessageHash();
        String expected = "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, report.deleteMessageByHash(hashToDelete));

        // Now, if we search for that recipient again, msg2 should be gone
        String expectedAfterDelete = "--- Messages for +27838884567 ---\n" +
                                     "- Ok, I am leaving without you.\n";
        assertEquals(expectedAfterDelete, report.findMessagesByRecipient("+27838884567"));
    }

    @Test
    public void testDisplayReport() {
        String expected = "--- Full Message Report ---\n\n" +
                          "Message #1\n" +
                          "  Hash: " + msg1.getMessageHash() + "\n" +
                          "  Recipient: +27834557896\n" +
                          "  Message: Did you get the cake?\n\n" +
                          "Message #4\n" +
                          "  Hash: " + msg4.getMessageHash() + "\n" +
                          "  Recipient: 0838884567\n" +
                          "  Message: It is dinner time!\n\n";
        assertEquals(expected, report.generateFullReport());
    }
}
