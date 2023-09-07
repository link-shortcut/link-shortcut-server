package kr.lnsc.api.link.domain.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UuidExpireKeyGeneratorTest {
    private ExpireKeyGenerator generator = new UuidExpireKeyGenerator();

    @DisplayName("8자리의 랜덤한 만료키를 생성한다.")
    @Test
    void generateExpireKey() {
        final int EXPIRE_KEY_LENGTH = 8;
        String expireKey = generator.generate();

        assertThat(expireKey).hasSize(EXPIRE_KEY_LENGTH);
    }
}