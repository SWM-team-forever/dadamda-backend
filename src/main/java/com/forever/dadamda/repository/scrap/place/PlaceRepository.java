package com.forever.dadamda.repository.scrap.place;

import com.forever.dadamda.entity.scrap.Place;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Slice<Place>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);
}
