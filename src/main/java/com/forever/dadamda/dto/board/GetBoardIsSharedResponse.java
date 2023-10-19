package com.forever.dadamda.dto.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GetBoardIsSharedResponse {

    @JsonProperty("isShared")
    private Boolean isShared;
}
