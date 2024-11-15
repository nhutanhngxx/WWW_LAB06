package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.iuh.fit.backend.models.Post;
import vn.com.iuh.fit.backend.models.PostComment;
import vn.com.iuh.fit.backend.repositories.UserRepository;
import vn.com.iuh.fit.backend.services.PostCommentService;
import vn.com.iuh.fit.backend.services.PostService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

}
