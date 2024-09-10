package org.example.springv3.reply;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;

    private final HttpSession session;



    //JSON타입을 받으려면 매개변수에 @RequestBody를 해준다.
    @PostMapping("/api/reply")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println(1);
        ReplyResponse.DTO replyDTO = replyService.댓글쓰기(saveDTO, sessionUser);
        System.out.println(6);
        System.out.println(replyDTO);
        return ResponseEntity.ok(Resp.ok(replyDTO));

    }


    @DeleteMapping("/api/reply/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        // 1. 인증 체크
        //      -> 인터셉터로 만들어둔 핸들러에서 인증체크를 위해 /api/를 앞에 붙여줌
        // 2. 서비스 호출 -> 댓글 삭제
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(id, sessionUser);

        // 3. 응답

        // static으로 이미 만들어져 있어서 new 할 필요 없다
        // Resp로 응답 해야 한다.
        // 삭제 했으므로 보내줄 게 없으므로 null을 보내준다.
        return ResponseEntity.ok(Resp.ok(null));

        //이렇게 리턴 테스트도 해보고
        //throw new ExceptionApi404("ㅅㄱㅇ");

        // @DeleteMapping("/api/reply/8") 이렇게 존재하지 않는 게시글로 테스트도 해본다.
    }
}
