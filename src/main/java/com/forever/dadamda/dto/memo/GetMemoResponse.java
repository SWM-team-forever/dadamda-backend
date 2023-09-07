package com.forever.dadamda.dto.memo;

import com.forever.dadamda.entity.Memo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMemoResponse {

    private Long memoId;
    private String memoText;
    private String memoImageUrl;
    private LocalDateTime createdDate;

    public static GetMemoResponse of(Memo memo) {
        return GetMemoResponse.builder()
                .memoId(memo.getId())
                .memoText(memo.getMemoText())
                .memoImageUrl(memo.getMemoImageUrl())
                .createdDate(memo.getCreatedDate())
                .build();
    }
}
