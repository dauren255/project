package kz.dauren.agaionline.repo;

import kz.dauren.agaionline.models.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByTitleContaining(String Title);
}
