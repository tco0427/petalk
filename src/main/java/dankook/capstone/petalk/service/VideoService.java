package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Video;
import dankook.capstone.petalk.dto.VideoDto;
import dankook.capstone.petalk.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(IllegalArgumentException::new);
    }

    public VideoDto findOneDto(Long id){return videoRepository.findVideo(id);}
}
