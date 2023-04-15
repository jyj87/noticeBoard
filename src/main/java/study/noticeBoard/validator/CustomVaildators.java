package study.noticeBoard.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.repository.UserRepository;

/**
 * 중복 확인 유효성 검증(Email,Username,Nickname)
 */
@RequiredArgsConstructor
@Component
public class CustomVaildators {

    @RequiredArgsConstructor
    @Component
    public static class EmailValidator extends AbstractValidator<UserDto.Request> {
        private final UserRepository userRepository;

        @Override
        public void doValidate(UserDto.Request dto, Errors errors) {
            if (userRepository.existsByEmail(dto.toEntity().getEmail())) {
                errors.rejectValue("email",
                        "이메일 중복 오류",
                        "이미 사용중인 이메일 입니다.");
            }
        }
    }

    @RequiredArgsConstructor
    @Component
    public static class NicknameValidator extends AbstractValidator<UserDto.Request> {
        private final UserRepository userRepository;
        @Override
        public void doValidate(UserDto.Request dto, Errors errors) {
            if (userRepository.existsByEmail(dto.toEntity().getNickname())) {
                errors.rejectValue("nickname",
                        "닉네임 중복 오류",
                        "이미 사용중인 닉네임 입니다.");
            }
        }
    }

    @RequiredArgsConstructor
    @Component
    public static class UsernameValidator extends AbstractValidator<UserDto.Request> {
        private final UserRepository userRepository;
        @Override
        public void doValidate(UserDto.Request dto, Errors errors) {
            if (userRepository.existsByEmail(dto.toEntity().getUsername())) {
                errors.rejectValue("username",
                        "아이디 중복 오류",
                        "이미 사용중인 아이디 입니다.");
            }
        }
    }
}