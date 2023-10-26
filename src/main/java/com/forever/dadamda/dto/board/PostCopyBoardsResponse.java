package com.forever.dadamda.dto.board;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PostCopyBoardsResponse {

    private UUID uuid;
}
