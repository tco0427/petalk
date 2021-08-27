package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.response.VideoDto;
import dankook.capstone.petalk.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {
    private final VideoRepository videoRepository;

    @Transactional
    public Long save(Video video){
        videoRepository.save(video);
        return video.getId();
    }

    public Video findOne(Long videoId){
        return videoRepository.findById(videoId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void update(Long id, Emotion emotion){
        Video video = videoRepository.findById(id).orElse(null);
        video.setEmotion(emotion);
    }

    public VideoDto findOneDto(Long id){return videoRepository.findVideo(id);}
}
