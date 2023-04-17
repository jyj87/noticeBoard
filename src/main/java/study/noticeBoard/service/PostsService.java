package study.noticeBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.noticeBoard.dto.PostsDto;
import study.noticeBoard.entity.Posts;
import study.noticeBoard.entity.User;
import study.noticeBoard.repository.PostsRepository;
import study.noticeBoard.repository.UserRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    /* CREATE */
    @Transactional
    public Long save(PostsDto.Request dto, String nickname) {
        log.info("PostsService save() 실행");
        User user = userRepository.findByNickname(nickname);
        dto.setUser(user);
        Posts posts = dto.toEntity();
        postsRepository.save(posts);

        return posts.getId();

    }

    /* Delete */
    @Transactional
    public void delete(Long id) {
        log.info("PostsService delete() 실행");
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        postsRepository.delete(posts);

    }

    /* UPDATE (dirty checking 영속성 컨텍스트)
     *  User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
     * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다. */
    @Transactional
    public void update(Long id, String title, String content) {
        log.info("PostsService update() 실행");
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        posts.update(title, content);

    }

    /* PostRead */
    @Transactional(readOnly = true)
    public PostsDto.Response postRead(Long id) {
        Posts post = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new PostsDto.Response(post);

    }

    /* postPageListRead */
    @Transactional(readOnly = true)
    public Page<Posts> postPageListRead(Pageable pageable) {
        postsRepository.findAll(pageable);
        return postsRepository.findAll(pageable);

    }

    /* UPDATE_VIEW
     *  조회수 카운팅 */
    @Transactional
    public int updateView(Long id) {
        return postsRepository.updateView(id);
    }

    /* 검색 조회 */
    @Transactional(readOnly = true)
    public Page<Posts> titleSearch(String keyword, Pageable pageable, int searchKey) {

        Page<Posts> postsList;
        /* 타이틀 검색 조회 */
        if (searchKey == 1) {
            postsList = postsRepository
                    .findByTitleContaining(keyword, pageable);
            /* 작성자 검색 조회 */
        } else {
            postsList = postsRepository
                    .findByWriterContaining(keyword, pageable);
        }
        return postsList;
    }


}
