package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.resetpassword.dto.ResetPasswordRequest;

public interface ResetPasswordService {
    BaseResponse<?> sendOtp(String email);

    BaseResponse<?> confirmOtp(String email, Integer opt);

    BaseResponse<?> updatePassword(ResetPasswordRequest resetPasswordRequest);
}
