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
package st10485573test;

import st10485573.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Funiwe Ngobeni
 */

public class RegistrationTest {
    private Registration registration;

    // Test data constants
    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Passw0rd!";
    private static final String VALID_CELL_PHONE = "+27123456789";
    private static final String VALID_FIRST_NAME = "Funiwe";
    private static final String VALID_LAST_NAME = "Ngobeni";
    private static final String INVALID_USERNAME = "kyle";
    private static final String INVALID_PASSWORD = "pass";
    private static final String INVALID_CELL_PHONE = "12345";
    private static final String INVALID_FIRST_NAME = "Funiwe123";
    private static final String INVALID_LAST_NAME = "";

    // Message constants
    private static final String USERNAME_SUCCESS = "Username successfully captured";
    private static final String PASSWORD_SUCCESS = "Password successfully captured";
    private static final String CELL_PHONE_SUCCESS = "Cellphone number successfully captured";
    private static final String FIRST_NAME_SUCCESS = "First name successfully captured";
    private static final String LAST_NAME_SUCCESS = "Last name successfully captured";
    private static final String REGISTRATION_SUCCESS = "Registration successful";
    private static final String USERNAME_ERROR = "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
    private static final String PASSWORD_ERROR = "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    private static final String CELL_PHONE_ERROR = "Cellphone number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
    private static final String FIRST_NAME_ERROR = "First name is invalid, please ensure it is not empty";
    private static final String LAST_NAME_ERROR = "Last name is invalid, please ensure it is not empty";
    private static final String REGISTRATION_ABORTED = "Registration aborted";

    @BeforeEach
    public void setUp() {
        registration = new Registration();
    }

    @Test
    public void testValidRegistration() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE, VALID_FIRST_NAME, VALID_LAST_NAME);
        assertTrue(result.contains(USERNAME_SUCCESS));
        assertTrue(result.contains(PASSWORD_SUCCESS));
        assertTrue(result.contains(CELL_PHONE_SUCCESS));
        assertTrue(result.contains(FIRST_NAME_SUCCESS));
        assertTrue(result.contains(LAST_NAME_SUCCESS));
        assertTrue(result.contains(REGISTRATION_SUCCESS));
    }

    @Test
    public void testInvalidUsername() {
        String result = registration.registerUser(INVALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE, VALID_FIRST_NAME, VALID_LAST_NAME);
        assertTrue(result.contains(USERNAME_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }

    @Test
    public void testInvalidPassword() {
        String result = registration.registerUser(VALID_USERNAME, INVALID_PASSWORD, VALID_CELL_PHONE, VALID_FIRST_NAME, VALID_LAST_NAME);
        assertTrue(result.contains(PASSWORD_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }

    @Test
    public void testInvalidCellPhone() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, INVALID_CELL_PHONE, VALID_FIRST_NAME, VALID_LAST_NAME);
        assertTrue(result.contains(CELL_PHONE_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }

    @Test
    public void testInvalidFirstName() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE, INVALID_FIRST_NAME, VALID_LAST_NAME);
        assertTrue(result.contains(FIRST_NAME_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }

    @Test
    public void testInvalidLastName() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE, VALID_FIRST_NAME, INVALID_LAST_NAME);
        assertTrue(result.contains(LAST_NAME_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }

    @Test
    public void testAllInvalid() {
        String result = registration.registerUser(INVALID_USERNAME, INVALID_PASSWORD, INVALID_CELL_PHONE, INVALID_FIRST_NAME, INVALID_LAST_NAME);
        assertTrue(result.contains(USERNAME_ERROR));
        assertTrue(result.contains(PASSWORD_ERROR));
        assertTrue(result.contains(CELL_PHONE_ERROR));
        assertTrue(result.contains(FIRST_NAME_ERROR));
        assertTrue(result.contains(LAST_NAME_ERROR));
        assertTrue(result.contains(REGISTRATION_ABORTED));
    }
}
