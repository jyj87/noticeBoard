package study.noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.noticeBoard.dto.UserDto;
import study.noticeBoard.entity.User;
import study.noticeBoard.service.UserService;
import study.noticeBoard.validator.CustomVaildators;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final CustomVaildators.EmailValidator emailValidator;
    private final CustomVaildators.NicknameValidator nicknameValidator;
    private final CustomVaildators.UsernameValidator usernameValidator;

    /* UserController 진입시 먼서 실행되여 유효성 검사를 함 */
    @InitBinder
    public void validatorBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(emailValidator);
        dataBinder.addValidators(nicknameValidator);
        dataBinder.addValidators(usernameValidator);
    }

    /* 로그인 화면으로 이동 */
    @GetMapping("/login")
    public String loginView(Model model) {
        return "board/login";
    }

    /* 회원가입 화면으로 이동 */
    @GetMapping("/any/join")
    public String joinView(Model model) {
        return "board/join";
    }

    /* 회원가입 진행 <등록버튼 클릭시> */
    @PostMapping("/any/joinProc")
    public String joinProc(@Valid UserDto.Request dto, Errors errors, Model model) {

        /* 유효성, 중복검사에 통과 못한경우 */
        if (errors.hasErrors()) {

            /* 회원가입 실패시 기존에 입력한 데이터 값을 유지 */
            model.addAttribute("userDto", dto);

            /* 유효성, 중복검사에 통과 못할 경우 화면에 표시해줄 메세지 셋팅 */
            Map<String, String> validatorResult =
                    userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 다시 회원가입 화면으로 리턴 */
            return "board/join";
        }

        userService.userJoin(dto);
        /* 회원가입이 성공하면 로그인 화면으로 이동 */
        return "redirect:/login";

    }

}
