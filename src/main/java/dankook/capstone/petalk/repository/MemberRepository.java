package dankook.capstone.petalk.repository;


import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.PlatformType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPlatformId(Integer platformId);
    Optional<Member> findByUserId(String userId);
}
