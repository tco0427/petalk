package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.entity.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    Slice<Community> findAllBy(Pageable pageable);
}
