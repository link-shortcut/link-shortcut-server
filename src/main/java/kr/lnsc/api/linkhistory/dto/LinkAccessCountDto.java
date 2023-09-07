package kr.lnsc.api.linkhistory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
public class LinkAccessCountDto {

    @Schema(description = "접속 일자")
    private LocalDate date;

    @Schema(description = "접속 횟수")
    private long count;

    public LinkAccessCountDto(LocalDate date, long count) {
        this.date = date;
        this.count = count;
    }

    public LinkAccessCountDto(Date date, long count) {
        this.date = date.toLocalDate();
        this.count = count;
    }

    public boolean isEqualDate(LocalDate date) {
        return this.date.isEqual(date);
    }
}