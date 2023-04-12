package study.noticeBoard.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.entity.User;
import study.noticeBoard.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final HttpSession session;

    /* username이 DB에 있는지 확인 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));

        session.setAttribute("user", new UserDto.Response(user));

        Enumeration<String> attributeNames = session.getAttributeNames();
        /* 시큐리티 세션에 유저 정보 저장 */
        return new CustomUserDetails(user);
    }
}
