package green.mtcoding.springv3.user;

import green.mtcoding.springv3.core.util.Resp;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final HttpSession session;
    private final UserService userService;

    //http://localhost:8080/luser/samecheck?username=hello
    @GetMapping("/user/samecheck")
    public ResponseEntity<?> sameCheck(@RequestParam("username") String username) {
        boolean isSameUsername = userService.유저네임중복되었니(username);
        // 이름이 명확해야 한다. 유저이름중복체크 이렇게 이름 지었으면 true면 통과인가? 중복인가?
        // 헷갈리기 때문에 명확하게 해야한다.

        return ResponseEntity.ok(Resp.ok(isSameUsername, isSameUsername? "중복되었어요" : "중복되지 않았어요"));
    }


    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        User sessionUser = userService.로그인(loginDTO);
        System.out.println("12312323123213" + sessionUser.getUsername());
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}
