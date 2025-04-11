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

public class Login {
    private final Registration registration;
    private boolean accessGranted;

    public Login(Registration registration) {
        this.registration = registration;
    }

    public boolean loginUser(String userNameAttempt, String passwordAttempt) {
        accessGranted = userNameAttempt != null && passwordAttempt != null &&
                        userNameAttempt.equals(registration.getUserName()) &&
                        passwordAttempt.equals(registration.getPassword());
        return accessGranted;
    }

    public String returnLoginStatus() 
    {
        // String interpolation is used here so I don't have to concatinate. 
        return accessGranted ?
                String.format("Welcome %s %s,\nit is great to see you.", registration.getFirstName(), registration.getLastName()) :
                "Username & Password do not match our records, please try again.";
    }

    public String getUsername() {
        return registration.getUserName();
    }
}