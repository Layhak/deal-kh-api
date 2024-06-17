package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;

public interface ResetPasswordService {
    BaseResponse<?> sendOtp(String email);

    BaseResponse<?> confirmOtp(String email, Integer opt);

    BaseResponse<?> updatePassword(String email, Integer confirmationCode, String newPassword, String confirmPassword);
}
