package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Comment;
import dankook.capstone.petalk.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long register(Comment comment){
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }
}
