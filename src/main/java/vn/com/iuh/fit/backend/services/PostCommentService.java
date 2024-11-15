package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.PostComment;
import vn.com.iuh.fit.backend.repositories.PostCommentRepository;

import java.util.List;

@Service
public class PostCommentService {
    @Autowired
    private PostCommentRepository postCommentRepository;

    public List<PostComment> getPostCommentsByPostId(Long postId) {
        return postCommentRepository.findByPostId(postId);
    }
}
