package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.iuh.fit.backend.models.Post;
import vn.com.iuh.fit.backend.models.PostComment;
import vn.com.iuh.fit.backend.services.PostCommentService;
import vn.com.iuh.fit.backend.services.PostService;

import java.util.List;

@Controller
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostService postService;

    public PostCommentController(PostCommentService postCommentService, PostService postService) {
        this.postCommentService = postCommentService;
        this.postService = postService;
    }
}
