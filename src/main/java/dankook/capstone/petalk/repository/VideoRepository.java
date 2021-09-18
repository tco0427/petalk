package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.response.VideoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findListByMember(Member member);
}