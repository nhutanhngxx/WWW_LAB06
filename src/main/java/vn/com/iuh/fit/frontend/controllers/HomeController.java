package vn.com.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.com.iuh.fit.backend.models.Post;
import vn.com.iuh.fit.backend.models.PostComment;
import vn.com.iuh.fit.backend.models.User;
import vn.com.iuh.fit.backend.repositories.UserRepository;
import vn.com.iuh.fit.backend.services.PostCommentService;
import vn.com.iuh.fit.backend.services.PostService;
import vn.com.iuh.fit.backend.services.UserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public HomeController(UserService userService) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userService = userService;
    }

    @GetMapping("/index")
    public String showPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        List<PostComment> comments = new ArrayList<>();
        for (Post post : posts) {
            List<PostComment> postComments = postCommentService.getPostCommentsByPostId(post.getId());
            comments.addAll(postComments);
        }
        model.addAttribute("comments", comments);
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        System.out.println("showLogin");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String emailOrMobile,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = new User();
        if (emailOrMobile.contains("@")) {
            user = userRepository.findByEmail(emailOrMobile);
        } else if (emailOrMobile.matches("\\d{10,15}")) {
            user = userRepository.findByMobile(emailOrMobile);
        }
        System.out.println("Raw password: " + password);
        System.out.println("Password hash from DB: " + user.getPasswordHash());
        if (user != null && user.getPasswordHash() != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            user.setLastLogin(Instant.now());
            session.setAttribute("user", user);
            userService.saveUser(user);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Login failed! Invalid email/phone or password.");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, @RequestParam String password) {
        user.setPasswordHash(passwordEncoder.encode(password));
        try {
            user.setRegisteredAt(Instant.now());
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    @PostMapping("/comment")
    public String commentPost(@RequestParam Long postId,
                              @RequestParam String comment,
                              @RequestParam long userId,
                              HttpSession session,
                              Model model) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        if (user != null || post != null) {
            PostComment postComment = new PostComment();
            postComment.setTitle("Comment");
            postComment.setContent(comment);
            postComment.setCreatedAt(Instant.now());
            postComment.setPublished(true);
            postComment.setPublishedAt(Instant.now());
            postComment.setPost(post);
            postComment.setUser(user);
            postCommentService.savePostComment(postComment);
            session.setAttribute("user", user);
            model.addAttribute("postId", postId);
            return "redirect:/index";
        }
        return "index";
    }

    @PostMapping("/reply")
    public String replyComment(@RequestParam Long postId,
                               @RequestParam Long commentId,
                               @RequestParam String commentReply,
                               @RequestParam long userId,
                               HttpSession session,
                               Model model) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        PostComment parentComment = postCommentService.getPostCommentById(commentId);
        if (user != null || post != null || parentComment != null) {
            PostComment replyComment = new PostComment();
            replyComment.setTitle("Reply");
            replyComment.setContent(commentReply);
            replyComment.setCreatedAt(Instant.now());
            replyComment.setPublished(true);
            replyComment.setPublishedAt(Instant.now());
            replyComment.setPost(post);
            replyComment.setUser(user);
            replyComment.setParent(parentComment);
            postCommentService.savePostComment(replyComment);
            session.setAttribute("user", user);
            model.addAttribute("postId", postId);
            return "redirect:/index";
        }
        return "index";
    }

    @PostMapping("/post")
    public String post(@RequestParam Long userId,
                       @RequestParam String content) {
        User user = userService.getUserById(userId);
        Post post = new Post();
        post.setTitle("Post");
        post.setContent(content);
        post.setCreatedAt(Instant.now());
        post.setPublished(true);
        post.setPublishedAt(Instant.now());
        post.setAuthor(user);
        postService.savePost(post);
        return "redirect:/index";
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
