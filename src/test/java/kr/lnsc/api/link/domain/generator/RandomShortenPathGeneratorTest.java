package kr.lnsc.api.link.domain.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RandomShortenPathGeneratorTest {
    private Set<Character> pathAvailableCharacters = pathAvailableCharacters();
    private RandomShortenPathGenerator generator = new RandomShortenPathGenerator();

    @DisplayName("생성된 단축 Path는 대소문자 구분 가능한 영어 알파벳 7자리의 조합이다.")
    @RepeatedTest(10)
    void generatePath() {
        final int VALID_LENGTH = 7;

        String generatedPath = generator.generate();

        boolean isInvalidCharacterIncluded = generatedPath.chars()
                .mapToObj(ch -> (char) ch)
                .anyMatch(ch -> !pathAvailableCharacters.contains(ch));

        assertThat(generatedPath).hasSize(VALID_LENGTH);
        assertThat(isInvalidCharacterIncluded).isFalse();
    }

    private static Set<Character> pathAvailableCharacters() {
        Set<Character> result = new HashSet<>();
        for (int ch = 'a'; ch <= 'z'; ch++) {
            result.add((char) ch);
        }
        for (int ch = 'A'; ch <= 'Z'; ch++) {
            result.add((char) ch);
        }
        return result;
    }
}