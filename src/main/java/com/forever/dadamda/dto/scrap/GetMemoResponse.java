package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.Memo;
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
    private String memo;
    private String memoImageUrl;

    public static GetMemoResponse of(Memo memo) {
        return GetMemoResponse.builder()
                .memoId(memo.getId())
                .memo(memo.getMemo())
                .memoImageUrl(memo.getMemoImageUrl())
                .build();
    }
}
