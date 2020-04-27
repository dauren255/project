package kz.dauren.agaionline.service.Interfaces;

import kz.dauren.agaionline.models.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    Iterable<Post> findAllByTitleContainingIgnoreCase(String filter);

    Iterable<Post> findAll();

    boolean existsById(Long id);

    Post findById(Long id);

    void save(Post post);

    void delete(Post post);
}
