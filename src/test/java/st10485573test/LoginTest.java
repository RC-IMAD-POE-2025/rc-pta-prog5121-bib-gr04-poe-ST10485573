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

import st10485573.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Funiwe Ngobeni
 */

public class LoginTest {
    private Registration registration;
    private Login login;

    // Test data constants
    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Passw0rd!";
    private static final String INVALID_USERNAME = "wrong";
    private static final String INVALID_PASSWORD = "wrong";

    // Message constants
    private static final String SUCCESS_MESSAGE = "Welcome John Doe,\nit is great to see you.";
    private static final String ERROR_MESSAGE = "Username & Password do not match our records, please try again.";

    @BeforeEach
    public void setUp() {
        registration = new Registration();
        registration.registerUser(VALID_USERNAME, VALID_PASSWORD, "+27123456789", "John", "Doe");
        login = new Login(registration);
    }

    @Test
    public void testValidLogin() {
        assertTrue(login.loginUser(VALID_USERNAME, VALID_PASSWORD));
        assertEquals(SUCCESS_MESSAGE, login.returnLoginStatus());
    }

    @Test
    public void testInvalidLogin() {
        assertFalse(login.loginUser(INVALID_USERNAME, INVALID_PASSWORD));
        assertEquals(ERROR_MESSAGE, login.returnLoginStatus());
    }
}
