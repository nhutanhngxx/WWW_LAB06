package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.Post;
import vn.com.iuh.fit.backend.models.User;
import vn.com.iuh.fit.backend.repositories.PostRepository;
import vn.com.iuh.fit.backend.repositories.UserRepository;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }
}
