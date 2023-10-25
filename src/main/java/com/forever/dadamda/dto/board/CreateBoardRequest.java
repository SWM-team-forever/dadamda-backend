package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.entity.user.User;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateBoardRequest {

    @NotBlank(message = "보드명을 입력해주세요.")
    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String title;

    @Size(max = 1000, message = "최대 1000자까지 입력할 수 있습니다.")
    private String description;

    @NotBlank(message = "태그를 입력해주세요.")
    private String tag;

    public Board toEntity(User user, UUID uuid) {
        return Board.builder()
                .user(user)
                .title(title)
                .description(description)
                .tag(TAG.from(tag))
                .uuid(uuid)
                .authorshipUser(user)
                .build();
    }
}
