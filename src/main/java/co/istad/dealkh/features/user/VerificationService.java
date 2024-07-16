package co.istad.dealkh.features.user;

import co.istad.dealkh.domain.User;

public interface VerificationService {
    void sendVerificationEmail(User user, String token, String verifyUrl);
}
