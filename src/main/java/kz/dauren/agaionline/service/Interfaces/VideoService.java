package kz.dauren.agaionline.service.Interfaces;

import kz.dauren.agaionline.models.Video;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {
    Iterable<Video> findAllByPostId(Long id);

    Video findById(Long videoId);

    void save(Video video);

    boolean existsById(Long id);
}
