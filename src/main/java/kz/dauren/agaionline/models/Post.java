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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "post_author",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User author;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToMany(mappedBy = "requestPosts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> requestUsers;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Video> videos;

    public Post(String title, String anons, String full_text, User author) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.author = author;
    }

//    public String getAuthorName(){
//        return author != null ? author : "<NONE>";
//    }
    public Post(String title, String anons, String full_text, int views){
         this.title = title;
         this.anons = anons;
         this.full_text = full_text;
         this.views = views;
     }
}
