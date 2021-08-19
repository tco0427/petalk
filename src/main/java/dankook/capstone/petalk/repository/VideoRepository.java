package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.VideoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    @Query("select new dankook.capstone.petalk.dto.VideoDto" +
            "(v.id, v.video, v.emotion)" +
            "from Video v")
    VideoDto findVideo(Long id);
}