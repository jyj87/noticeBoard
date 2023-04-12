package study.noticeBoard.controller;

import groovy.lang.GString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.noticeBoard.dto.CommentDto;
import study.noticeBoard.dto.PostsDto;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.entity.Posts;
import study.noticeBoard.security.LoginUser;
import study.noticeBoard.service.PostsService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;


    /* 게시판 글 등록 */
    @PostMapping("/any/posts/create")
    public String postCreate(PostsDto.Request dto,
                             @LoginUser UserDto.Response user, Model model) {
        postsService.save(dto, user.getNickname());
        return "redirect:/any/posts/";
    }

    /* 게시판 글 삭제 */
    @GetMapping("/any/posts/delete/{id}")
    public String postDelete(@PathVariable("id") Long id,
                             Model model) {
        postsService.delete(id);
        return "redirect:/any/posts/";
    }

    /* 게시판 글 수정 */
    @PostMapping("/any/posts/update")
    public String postUpdate(PostsDto.Request dto, Model model) {

        postsService.update(dto.getId(), dto.getTitle(), dto.getContent());
        return "redirect:/any/posts/";
    }

    /* 게시판 글 수정폼으로 이동 */
    @GetMapping("/any/posts/modify/{id}")
    public String modifyView(@PathVariable("id") Long id,
                             @LoginUser UserDto.Response user,
                             Model model) {

        PostsDto.Response dto = postsService.postRead(id);

        model.addAttribute("post", dto);

        return "board/update";
    }

    /* 게시판 글 리스트 */
    @GetMapping("/any/posts/")
    public String postsListView(Model model,
                                @PageableDefault(sort = "id",
                                        direction = Sort.Direction.DESC,
                                        size = 6) Pageable pageable) {
        Page<Posts> posts = postsService.postPageListRead(pageable);
        model.addAttribute("postsList",posts);
        model.addAttribute("previous",pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        /* 다음 페이지 여부 ->true 존재 */
        model.addAttribute("hasNext",posts.hasNext());
        /* 이전 페이지 여부 ->true 존재 */
        model.addAttribute("hasPrevious",posts.hasPrevious());

        return "board/list";
    }

    /* 게시판 글 상세보기 */
    @GetMapping("/any/posts/read/{id}")
    public String postReadView(@PathVariable("id") Long id, Model model) {
        PostsDto.Response dto = postsService.postRead(id);
        List<CommentDto.Response> commentList = dto.getComments();
        /* 댓글이 존재 했을떄 */
        if (!CollectionUtils.isEmpty(commentList)) {
            /** 나중에 확인 */
            model.addAttribute("commentList", commentList);
        }
        System.out.println("여기까지는 됨");

        /** 조회수 카운팅 */
        postsService.updateView(id);
        model.addAttribute("post", dto);

        return "board/detail";

    }

    /* 게시판 글작성폼으로 이동 */
    @GetMapping("/any/posts/write")
    public String writeView(Model model) {
        return "board/write";
    }

}
