package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {
    private VideoRepository videoRepository;

    @Transactional
    public Long save(Video video){
        videoRepository.save(video);
        return video.getId();
    }

    public Optional<Video> findOne(Long videoId){
        return videoRepository.findById(videoId);
    }

}
