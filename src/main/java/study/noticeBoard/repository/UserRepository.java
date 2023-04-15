package study.noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.noticeBoard.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    User findByNickname(String nickname);

    /* 중복검사 중복인 경우 ture 리턴*/
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
