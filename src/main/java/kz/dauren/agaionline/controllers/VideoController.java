package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.Post;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.models.Video;
import kz.dauren.agaionline.repo.PostRepository;
import kz.dauren.agaionline.repo.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/blog/{id}/addVideo")
    public String addVideo(@PathVariable Long id,  Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", "Добавить видео ");
        model.addAttribute("post", postRepository.findById(id).get());
        return "addVideo";
    }
    @GetMapping("/blog/{postId}/editVideo/{videoId}")
    public String editVideo(@PathVariable Long postId, @PathVariable Long videoId,   Model model) {
        model.addAttribute("title", "Редактировать видео ");
        model.addAttribute("video", videoRepository.findById(videoId).get());
        return "editVideo";
    }
    @PostMapping("/blog/{id}/addVideo")
    public String addVideoForm(@PathVariable Long id, @Valid Video video, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        video.setPost(postRepository.findById(id).get());
        video.setLink("https://youtube.com/embed/"+video.getLink()+"?modestbranding=1;&showinfo=0;&rel=0;");
        videoRepository.save(video);
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @PostMapping("/video/{id}")
    public String editVideoPage(@PathVariable Long id, @RequestParam String link, @RequestParam String name, Model model) {
        if(!videoRepository.existsById(id)){
            return "redirect:/blog";
        }
        Video video = videoRepository.findById(id).get();
        video.setName(name);
        video.setLink("https://youtube.com/embed/"+link+"?modestbranding=1;&showinfo=0;&rel=0;");
        videoRepository.save(video);
        return "redirect:/blog/" + video.getPost().getId();
    }
}
