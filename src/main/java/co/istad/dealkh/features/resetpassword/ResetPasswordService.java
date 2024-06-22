package co.istad.dealkh.features.resetpassword;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.resetpassword.dto.ConfirmOtpCode;
import co.istad.dealkh.features.resetpassword.dto.ResetPasswordRequest;
import co.istad.dealkh.features.resetpassword.dto.SentOtpRequest;

public interface ResetPasswordService {
    BaseResponse<?> sendOtp(SentOtpRequest sentOtpRequest);

    BaseResponse<?> confirmOtp(ConfirmOtpCode confirmOtpCode);

    BaseResponse<?> resetPassword(ResetPasswordRequest resetPasswordRequest);
}
