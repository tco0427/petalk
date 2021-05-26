package dankook.capstone.petalk.repository;


import dankook.capstone.petalk.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<dankook.capstone.petalk.domain.Member> findById(String id){
        return em.createQuery("select m from Member m where m.id=:id",Member.class)
                .setParameter("id",id)
                .getResultList();
    }
}
