package study.noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class TestController {

    /* 부트스트랩 테스트 */
    @GetMapping("/boottest")
    public String loginView(Model model) {
        return "boottest";
    }

}
