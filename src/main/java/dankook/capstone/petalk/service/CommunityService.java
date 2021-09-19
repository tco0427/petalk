package dankook.capstone.petalk.service;

import dankook.capstone.petalk.entity.Community;
import dankook.capstone.petalk.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Transactional
    public Long register(Community community){
        communityRepository.save(community);
        return community.getId();
    }

    public List<Community> findAllCommunity(){
        return communityRepository.findAll();
    }

    public Community findOne(Long communityId){
        return communityRepository.findById(communityId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void update(Long id, String content){
        Community community = communityRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        community.setContent(content);
    }

    @Transactional
    public void deleteById(Long id){
        communityRepository.deleteById(id);
    }

//    public List<Community> findAll(CommunitySearch communitySearch){
//      동적쿼리 작성해야함
//    }

    public Slice<Community> findAllBySlice(int page){
        PageRequest pageRequest = PageRequest.of(page,10);

        return communityRepository.findAllBy(pageRequest);
    }
}
