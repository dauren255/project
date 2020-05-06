package kz.dauren.agaionline.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, full_text, link;
    private int views;
    private String author;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToMany(mappedBy = "requestPosts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> requestUsers;

    public Post(String title, String anons, String full_text, String author) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.author = author;
    }

    public String getAuthorName(){
        return author != null ? author : "<NONE>";
    }
    public Post(String title, String anons, String full_text, int views){
         this.title = title;
         this.anons = anons;
         this.full_text = full_text;
         this.views = views;
     }
}
