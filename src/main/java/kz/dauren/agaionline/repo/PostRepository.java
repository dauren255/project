package kz.dauren.agaionline.repo;

import kz.dauren.agaionline.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByTitleContainingIgnoreCase(String Title);
}
