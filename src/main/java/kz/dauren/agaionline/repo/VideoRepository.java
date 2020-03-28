package kz.dauren.agaionline.repo;

import kz.dauren.agaionline.models.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
    Iterable<Video> findAllByPostId(Long id);
}
