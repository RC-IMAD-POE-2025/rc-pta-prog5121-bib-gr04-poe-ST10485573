/*
 * The MIT License
 *
 * Copyright 2025 Funiwe Ngobeni.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package st10485573;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Main application class for Funiwe Ngobeni's QuickChat.
 * Initializes core logic objects and starts the GUI flow with Registration.
 *
 * @author Funiwe Ngobeni
 * @version 1.1 (Corrected main app flow)
 */
public class FuniweNgobeniApp {

    /**
     * Main method to launch the QuickChat application.
     * It creates the core logic objects and displays the initial Registration GUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // It's good practice to run Swing GUI code on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create core business logic objects
                Registration reg = new Registration();
                Login login = new Login(reg); // Login logic depends on Registration data

                // Start with the registration form.
                // The RegistrationGUI will be responsible for transitioning to LoginGUI.
                RegistrationGUI regGUI = new RegistrationGUI(reg, login);
                regGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when this main window is closed
                regGUI.setLocationRelativeTo(null); // Center it on screen
                regGUI.setVisible(true); // Show it!
            }
        });
    }
}