package kr.lnsc.api.link.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExpireTimeValidatorTest {
    private ExpireTimeValidator expireTimeValidator = new ExpireTimeValidator();

    @DisplayName("입력한 만료일자가 오늘보다 이전 날일 경우 유효성 검사에 실패한다.")
    @Test
    void expireDateIsAfterFail() {
        final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        given(context.buildConstraintViolationWithTemplate(anyString()))
                .willReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));

        boolean isValid = expireTimeValidator.isValid(YESTERDAY, context);

        assertThat(isValid).isFalse();
    }

    @DisplayName("입력한 만료일자가 오늘 포함 이후 날일 경우 유효성 검사에 성공한다.")
    @Test
    void expireDateSuccess() {
        final LocalDate TODAY = LocalDate.now();

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        given(context.buildConstraintViolationWithTemplate(anyString()))
                .willReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));

        boolean isValid = expireTimeValidator.isValid(TODAY, context);

        assertThat(isValid).isTrue();
    }
}