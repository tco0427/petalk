package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Community;
import dankook.capstone.petalk.domain.Gender;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
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

    public Optional<Community> findOne(Long communityId){
        return communityRepository.findById(communityId);
    }

    @Transactional
    public void update(Long id, String content, File attachment){
        Community community = communityRepository.findById(id).get();
        community.setContent(content);
        community.setDate(new Date(System.currentTimeMillis()));
    }

    @Transactional
    public void deleteById(Long id){
        communityRepository.deleteById(id);
    }
}
