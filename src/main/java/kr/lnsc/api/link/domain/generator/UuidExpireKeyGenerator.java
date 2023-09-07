package kr.lnsc.api.link.domain.generator;

import java.util.UUID;

public class UuidExpireKeyGenerator implements ExpireKeyGenerator {
    private static final int EXPIRE_KEY_LENGTH = 8;

    @Override
    public String generate() {
        return UUID.randomUUID().toString().substring(0, EXPIRE_KEY_LENGTH);
    }
}
