package study.noticeBoard.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import study.noticeBoard.entity.Comment;
import study.noticeBoard.entity.Posts;
import study.noticeBoard.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class CommentDto {

    /** 댓글 Service 요청을 위한 DTO 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long id;
        private String comment;
        private String createdDate = localDateNow();
        private String modifiedDate= localDateNow();
        private Posts posts;
        private User user;
        /* Dto -> Entity */
        public Comment toEntity(){
            Comment comments = Comment.builder()
                    .id(id)
                    .comment(comment)
                    .createdDate(createdDate)
                    .modifiedDate(modifiedDate)
                    .posts(posts)
                    .user(user)
                    .build();

            return comments;
        }
    }

    /**
     * 댓글 정보를 리턴할 응답(Response) 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */
    @Getter
    public static class Response{

        private Long id;
        private String comment;
        private String createdDate = localDateNow();
        private String modifiedDate= localDateNow();
        private String nickname;
        private Long userId;
        private Long postId;

        /* Entity -> Dto*/
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.nickname = comment.getUser().getNickname();
            this.userId = comment.getUser().getId();
            this.postId = comment.getPosts().getId();
        }
    }

    /* 현재 Date 취득 */
    private static String localDateNow(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}


