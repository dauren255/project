package kz.dauren.agaionline.repo;

import kz.dauren.agaionline.models.Post;
import kz.dauren.agaionline.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
    Iterable<User> findAllByPostsContains(Post post);
    Iterable<User> findAllByOrderById();

}
