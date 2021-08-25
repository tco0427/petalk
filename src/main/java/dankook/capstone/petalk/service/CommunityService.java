package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Community;
import dankook.capstone.petalk.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void update(Long id, String content, File attachment){
        Community community = communityRepository.findById(id).get();
        community.setContent(content);
    }

    @Transactional
    public void deleteById(Long id){
        communityRepository.deleteById(id);
    }

//    public List<Community> findAll(CommunitySearch communitySearch){
//      동적쿼리 작성해야함
//    }
}
