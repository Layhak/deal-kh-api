package co.istad.dealkh.features.auth.dto;

import lombok.Builder;

/**
 * AuthRequest is a record class that represents an authentication request containing
 * an email and a password.
 *
 * <p>This class uses the following annotation:
 * <ul>
 * <li>{@link Builder} - Generates a builder for this record, providing a flexible way to create instances.</li>
 * </ul>
 * </p>
 *
 * @param email    the email of the user attempting to authenticate
 * @param password the password of the user attempting to authenticate
 */
@Builder
public record AuthRequest(String email, String password) {
}

