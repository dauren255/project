package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.Post;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.repo.PostRepository;
import kz.dauren.agaionline.repo.UserRepository;
import kz.dauren.agaionline.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;

    //GET MAPPINGS
    @GetMapping("/blog")
    public String blogMain(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()) {
            posts = postRepository.findAllByTitleContaining(filter);
        } else {
            posts = postRepository.findAll();
        }
        model.addAttribute("title", "Блог сайта");
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "blogMain";
    }

    @GetMapping("/myBlog")
    public String myBlogMain(@AuthenticationPrincipal User user, Model model) {
        Iterable<Post> posts = postRepository.findAllById(user.getPostIds());
        model.addAttribute("title", "Мои курсы");
        model.addAttribute("posts", posts);
        return "myBlog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/addBlog")
    public String add(Post post, Model model) {
        model.addAttribute(post);
        model.addAttribute("title", "Добавить блог");
        return "addBlog";
    }

    @GetMapping("/blog/{id}")
    public String blogPage(@PathVariable Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Post post = postRepository.findById(id).get();
        model.addAttribute("post", post);
        model.addAttribute("videos", videoRepository.findAllByPostId(post.getId()));
        model.addAttribute("title", "Страница ");
        return "blogPage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/{id}/editBlog")
    public String edit(@PathVariable Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("oldPost", postRepository.findById(id).get());
        model.addAttribute("pageTitle", "Редактировать блог");
        return "editBlog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/blog/{id}/addStudent")
    public String addStudent(@PathVariable Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("id", postRepository.findById(id).get().getId());
        model.addAttribute("users", userRepository.findAllByPostsContains(postRepository.findById(id).get()));
        model.addAttribute("pageTitle", "Добавить студента");
        return "addStudentToBlog";
    }

    //POSTMAPPINGS
    @PostMapping("/blog/addBlog")
    public String addBlog(@AuthenticationPrincipal User user, @Valid Post post, Model model) {
        post.setAuthor(user);
        postRepository.save(post);
        model.addAttribute("posts", postRepository.findAll());
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/editBlog")
    public String editBlog(@AuthenticationPrincipal User user, @Valid Post post, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "editBlog";
        }
        post.setAuthor(user);
        postRepository.save(post);
        model.addAttribute("posts", postRepository.findAll());
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/deleteBlog")
    public String deleteBlog(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blog/{id}/addStudent")
    public String addStudentToBlog(@PathVariable Long id, @RequestParam String username, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user.getPosts().contains(postRepository.findById(id).get())){
            model.addAttribute("message", "Такой студент уже есть");
            return "redirect:/blog/{id}/addStudent";
        }
        user.getPosts().add(postRepository.findById(id).get());
        userRepository.save(user);
        model.addAttribute("pageTitle", "Добавить студента");
        return "addStudentToBlog";
    }
}
