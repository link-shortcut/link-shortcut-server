package kr.lnsc.api.link.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ExpireTimeValidator implements ConstraintValidator<ExpireTime, LocalDate> {
    private static final String NULL_ERROR_MESSAGE = "만료일자를 입력해주세요.";
    private static final String INVALID_EXPIRE_DATE_MESSAGE = "만료일자는 오늘보다 이전 날일 수 없습니다. 확인 후 다시 입력해주세요.";

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            addConstraintViolation(context, NULL_ERROR_MESSAGE);
            return false;
        }

        LocalDate today = LocalDate.now();
        if (today.isAfter(value)) {
            addConstraintViolation(context, INVALID_EXPIRE_DATE_MESSAGE);
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
