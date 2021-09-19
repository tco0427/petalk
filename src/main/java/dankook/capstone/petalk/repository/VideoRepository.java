package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findListByMember(Member member);
}