package kr.lnsc.api.link.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.request.ExpireShortenLinkRequest;
import kr.lnsc.api.link.dto.response.CreateShortenLinkResponse;
import kr.lnsc.api.link.dto.response.GetLinkStatInfoResponse;
import kr.lnsc.api.util.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static kr.lnsc.api.fixture.HttpMethodFixture.*;
import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_ALREADY_EXPIRED;
import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("단축 URL을 생성한다.")
    @Test
    void createLink() {
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.originalUrl.getValue(), EXAMPLE_TODAY_EXPIRED.expireDate);

        ExtractableResponse<Response> response = httpPost(request, "/api/link/create");
        CreateShortenLinkResponse responseDto = response.body().as(CreateShortenLinkResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getShortenUrl()).isNotNull();
        assertThat(responseDto.getExpireKey()).isNotNull();
        assertThat(responseDto.getExpiredAt()).isEqualTo(toExpiredAt(EXAMPLE_TODAY_EXPIRED.expireDate));
    }

    @DisplayName("같은 URL로 여러개의 단축 URL을 생성한다.")
    @Test
    void createLinkWithSameOriginalURL() {
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.originalUrl.getValue(), EXAMPLE_TODAY_EXPIRED.expireDate);

        ExtractableResponse<Response> firstResponse = httpPost(request, "/api/link/create");
        assertThat(firstResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> secondResponse = httpPost(request, "/api/link/create");
        assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("단축 URL로 접속하면 원래 URL로 리다이렉트한다.")
    @Test
    void accessToLink() {
        ExtractableResponse<Response> response = httpGet("/" + EXAMPLE_TODAY_EXPIRED.shortenPath);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.header(HttpHeaders.LOCATION))
                .isEqualTo(EXAMPLE_TODAY_EXPIRED.originalUrl.getValue());
    }

    @DisplayName("만료된 단축 URL로 접속하면 404 에러가 발생한다.")
    @Test
    void accessToExpiredOrNotExistLink() {
        ExtractableResponse<Response> response = httpGet("/" + EXAMPLE_ALREADY_EXPIRED.shortenPath);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(getExceptionMessage(response))
                .isEqualTo("해당 링크가 이미 만료되었거나 존재하지 않아 불러올 수 없습니다.");
    }

    @DisplayName("단축 URL을 만료시킨다.")
    @Test
    void expireLink() {
        ExpireShortenLinkRequest request =
                new ExpireShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.shortenPath, EXAMPLE_TODAY_EXPIRED.expireKey);

        ExtractableResponse<Response> response = httpPost(request, "/api/link/expire");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("단축 URL 만료시 유효하지 않은 만료키를 입력하면 400 에러가 발생한다.")
    @Test
    void expireLinkWithInvalidExpireKey() {
        final String INVALID_EXPIRE_KEY = "INVALID" + EXAMPLE_TODAY_EXPIRED.expireKey;
        ExpireShortenLinkRequest request =
                new ExpireShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.shortenPath, INVALID_EXPIRE_KEY);

        ExtractableResponse<Response> response = httpPost(request, "/api/link/expire");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(getExceptionMessage(response))
                .isEqualTo("만료키가 일치하지 않거나 유효하지 않는 만료키입니다. 확인 후 다시 입력해주세요.");
    }

    @DisplayName("단축 URL의 통계 정보를 조회한다.")
    @Test
    void getLinkStats() {
        ExtractableResponse<Response> response =
                httpGet("/api/link/" + EXAMPLE_TODAY_EXPIRED.shortenPath);
        GetLinkStatInfoResponse responseDto = response.body().as(GetLinkStatInfoResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getOriginalUrl()).isEqualTo(EXAMPLE_TODAY_EXPIRED.originalUrl.getValue());
        assertThat(responseDto.getDailyAccessCount()).isEqualTo(0L);
        assertThat(responseDto.getTotalAccessCount()).isEqualTo(1L);
        assertThat(responseDto.getCreatedAt()).isNotNull();
        assertThat(responseDto.getExpiredAt()).isEqualTo(toExpiredAt(EXAMPLE_TODAY_EXPIRED.expireDate));
    }

    @DisplayName("만료된 단축 URL로 통계 정보를 조회할 경우 404 에러가 발생한다.")
    @Test
    void getExpiredLinkStats() {
        ExtractableResponse<Response> response =
                httpGet("/api/link/" + EXAMPLE_ALREADY_EXPIRED.shortenPath);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(getExceptionMessage(response))
                .isEqualTo("해당 링크가 이미 만료되었거나 존재하지 않아 불러올 수 없습니다.");
    }

    private LocalDateTime toExpiredAt(LocalDate expireDate) {
        return LocalDateTime.of(expireDate.plusDays(1), LocalTime.MIN);
    }
}
