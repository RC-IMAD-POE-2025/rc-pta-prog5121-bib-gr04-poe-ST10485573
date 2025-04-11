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

/**
 *
 * @author Funiwe Ngobeni
 */

import java.util.regex.Pattern;

public class Registration {
    private String storedUserName, storedPassword, storedCellPhoneNumber, storedFirstName, storedLastName;

    // Registers a user by checking all fields first, then setting them if valid
    public String registerUser(String newUserName, String newPassword, String newCellPhoneNumber, String newFirstName, String newLastName) {
        String feedback = ""; // Build our feedback string here
        boolean isValid = true; // Assume success until proven otherwise

        // Check each field and build feedback
        if (checkUserName(newUserName)) {
            feedback += "Username successfully captured\n";
        } else {
            feedback += "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n";
            isValid = false;
        }

        if (checkPasswordComplexity(newPassword)) {
            feedback += "Password successfully captured\n";
        } else {
            feedback += "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n";
            isValid = false;
        }

        if (checkCellPhoneNumber(newCellPhoneNumber)) {
            feedback += "Cellphone number successfully captured\n";
        } else {
            feedback += "Cellphone number is incorrectly formatted or does not contain an international code, please correct the number and try again.\n";
            isValid = false;
        }

        if (isNameValid(newFirstName)) { // Creative: new helper for names
            feedback += "First name successfully captured\n";
        } else {
            feedback += "First name is invalid, please ensure it is not empty.\n";
            isValid = false;
        }

        if (isNameValid(newLastName)) {
            feedback += "Last name successfully captured\n";
        } else {
            feedback += "Last name is invalid, please ensure it is not empty.\n";
            isValid = false;
        }

        // If all checks passed, set the values
        if (isValid) {
            setUserName(newUserName);
            setPassword(newPassword);
            setCellPhoneNumber(newCellPhoneNumber);
            setFirstName(newFirstName);
            setLastName(newLastName);
            feedback += "Registration successful";
        } else {
            feedback += "Registration aborted";
        }

        return feedback;
    }

    // Getter methods - no changes needed
    public String getUserName() {
        return storedUserName;
    }

    public String getPassword() {
        return storedPassword;
    }

    public String getCellPhoneNumber() {
        return storedCellPhoneNumber;
    }

    public String getFirstName() {
        return storedFirstName;
    }

    public String getLastName() {
        return storedLastName;
    }

    // Setter methods - just store the values, no validation here
    public boolean setUserName(String userName) {
        storedUserName = userName;
        return true; // Always true since validation is done earlier
    }

    public boolean setPassword(String password) {
        storedPassword = password;
        return true;
    }

    public boolean setCellPhoneNumber(String cellPhoneNumber) {
        storedCellPhoneNumber = cellPhoneNumber;
        return true;
    }

    public boolean setFirstName(String firstName) {
        storedFirstName = firstName;
        return true;
    }

    public boolean setLastName(String lastName) {
        storedLastName = lastName;
        return true;
    }

    // Checker methods - moved validation logic here
    private boolean checkUserName(String userName) {
        return userName != null && userName.length() <= 5 && userName.contains("_");
    }

    private boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        return password.matches(".*[A-Z].*") && // Has capital
               password.matches(".*[0-9].*") && // Has digit
               password.matches(".*[!@#$%^&*()].*"); // Has special char
    }

    private boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && Pattern.matches("^\\+27[0-9]{9}$", cellPhoneNumber);
    }

    // Creative twist: a helper method for both first and last name validation
    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("^[a-zA-Z]+$");
    }
}
