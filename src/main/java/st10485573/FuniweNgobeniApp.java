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

public class FuniweNgobeniApp 
{
    // Main method to kick things off
    public static void main(String[] args) {
        // Create our core objects
        Registration reg = new Registration();
        Login login = new Login(reg);

        // Start with the registration form
        RegistrationGUI regGUI = new RegistrationGUI(reg, login);
        regGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when closed
        regGUI.setLocationRelativeTo(null); // Center it on screen
        regGUI.setVisible(true); // Show it!
    }
}