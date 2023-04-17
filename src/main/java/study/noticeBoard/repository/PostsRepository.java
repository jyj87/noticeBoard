package study.noticeBoard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.noticeBoard.entity.Posts;

import java.util.List;


@Repository
public interface PostsRepository
        extends JpaRepository<Posts, Long> {

    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    /* 타이틀 검색 */
    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);

    /* 작성자 검색 */
    Page<Posts> findByWriterContaining(String keyword, Pageable pageable);
}
