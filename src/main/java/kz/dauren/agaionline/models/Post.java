package kz.dauren.agaionline.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, full_text, link;
    private int views;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;



    public Post(String title, String anons, String full_text, User author) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.author = author;
    }

    public String getAuthorName(){
        return author != null ? author.getLastname() + " " + author.getFirstname() : "<NONE>";
    }
    public Post(String title, String anons, String full_text, int views){
         this.title = title;
         this.anons = anons;
         this.full_text = full_text;
         this.views = views;
     }
}
