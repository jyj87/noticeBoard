package study.noticeBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30,unique = true)
    private String username;
    @Column(nullable = false, length = 30,unique = true)
    private String nickname;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 100,unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /* 회원정보 수정 */
    public void modify(String nickname,String password){
        this.nickname = nickname;
        this.password =password;
    }


}
