package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.Video;
import kz.dauren.agaionline.service.PostServiceImpl;
import kz.dauren.agaionline.service.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VideoServiceImpl videoServiceImpl;
    @Autowired
    private PostServiceImpl postServiceImpl;


    @GetMapping("/blog/{id}/addVideo")
    public String addVideo(@PathVariable Long id,  Model model) {
        if(!postServiceImpl.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", "Добавить видео ");
        model.addAttribute("post", postServiceImpl.findById(id));
        return "addVideo";
    }
    @GetMapping("/blog/{postId}/editVideo/{videoId}")
    public String editVideo(@PathVariable Long postId, @PathVariable Long videoId,   Model model) {
        model.addAttribute("title", "Редактировать видео ");
        model.addAttribute("video", videoServiceImpl.findById(videoId));
        return "editVideo";
    }
    @PostMapping("/blog/{id}/addVideo")
    public String addVideoForm(@PathVariable Long id, @Valid Video video, Model model) {
        if(!postServiceImpl.existsById(id)){
            return "redirect:/blog";
        }
        video.setPost(postServiceImpl.findById(id));
        video.setLink("https://youtube.com/embed/"+video.getLink()+"?modestbranding=1;&showinfo=0;&rel=0;");
        videoServiceImpl.save(video);
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @PostMapping("/video/{id}")
    public String editVideoPage(@PathVariable Long id, @RequestParam String link, @RequestParam String name, Model model) {
        if(!videoServiceImpl.existsById(id)){
            return "redirect:/blog";
        }
        Video video = videoServiceImpl.findById(id);
        video.setName(name);
        video.setLink("https://youtube.com/embed/"+link+"?modestbranding=1;&showinfo=0;&rel=0;");
        videoServiceImpl.save(video);
        return "redirect:/blog/" + video.getPost().getId();
    }
}
