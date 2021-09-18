package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Emotion;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.response.VideoDto;
import dankook.capstone.petalk.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Video> findListByMember(Member member){
        return videoRepository.findListByMember(member);
    }

    @Transactional
    public void updateEmotion(Long id, Emotion emotion){
        Video video = videoRepository.findById(id)
                        .orElseThrow(NoSuchElementException::new);
        video.setEmotion(emotion);
    }
}
