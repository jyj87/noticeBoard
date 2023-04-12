package study.noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.noticeBoard.dto.CommentDto;
import study.noticeBoard.dto.PostsDto;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.repository.CommentRepository;
import study.noticeBoard.security.LoginUser;
import study.noticeBoard.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /* 댓글 등록 */
    @PostMapping("/any/posts/comment/")
    public String commentCreate(CommentDto.Request dto,
                                @RequestParam("postId") Long postId,
                                @LoginUser UserDto.Response user,
                                Model model) {
        commentService.save(dto, postId, user.getNickname());
        return "redirect:/any/posts/read/"+postId;
    }

    /* 댓글 글 삭제 */
    @GetMapping("/any/posts/comment/delete/{postId}/{commentId}")
    public String commentDelete(@PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId,
                             Model model) {
        commentService.delete(commentId);
        return "redirect:/any/posts/read/"+postId;
    }
}
