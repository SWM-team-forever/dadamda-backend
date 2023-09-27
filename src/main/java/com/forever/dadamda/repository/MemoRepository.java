package com.forever.dadamda.repository;

import com.forever.dadamda.entity.Memo;
import com.forever.dadamda.entity.scrap.Scrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findMemosByScrapAndDeletedDateIsNull(Scrap scrap);

    Optional<Memo> findMemoByIdAndScrapAndDeletedDateIsNull(Long memoId, Scrap scrap);
}