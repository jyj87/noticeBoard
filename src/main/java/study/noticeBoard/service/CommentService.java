package study.noticeBoard.service;

import com.sun.xml.txw2.IllegalAnnotationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.noticeBoard.dto.CommentDto;
import study.noticeBoard.dto.PostsDto;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.entity.Comment;
import study.noticeBoard.entity.Posts;
import study.noticeBoard.entity.User;
import study.noticeBoard.repository.CommentRepository;
import study.noticeBoard.repository.PostsRepository;
import study.noticeBoard.repository.UserRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;


    /* CREATE */
    @Transactional
    public Long save(CommentDto.Request dto, Long id, String nickname){
        log.info("PostsService save() 코멘트 저장");
        User user = userRepository.findByNickname(nickname);
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalAnnotationException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));
        Comment comment = dto.toEntity();
        comment.setUser(user);
        comment.setPosts(posts);
        commentRepository.save(comment);

        return comment.getId();

    }

    /* DELETE */
    @Transactional
    public Long delete(Long id){
        log.info("PostsService delete() 코멘트 삭제");
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalAnnotationException("해당 댓글이 존재하지 않습니다. " + id));
        commentRepository.delete(comment);

        return comment.getId();

    }


}
