package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
}