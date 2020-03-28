package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.Post;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.repo.PostRepository;
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

    //GET MAPPINGS
    @GetMapping("/blog")
    public String blogMain(@RequestParam(required = false, defaultValue = "")String filter,  Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()){
            posts = postRepository.findAllByTitleContaining(filter);
        } else {
            posts = postRepository.findAll();
        }
        model.addAttribute("title", "Блог сайта");
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "blogMain.html";
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
        if(!postRepository.existsById(id)){
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
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("oldPost", postRepository.findById(id).get());
        model.addAttribute("pageTitle", "Редактировать блог");
        return "editBlog";
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
}
