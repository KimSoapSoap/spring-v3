package org.example.springv3.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardService boardService;






    //localhost:8080?title=제목  -> 'title=제목'이 쿼리 스트링이다.
    @GetMapping("/")
    public String list(@RequestParam(name="title", required = false)String title, HttpServletRequest request) {
        System.out.println(title);
        List<Board> boardList = boardService.게시글목록보기(title);
        request.setAttribute("models", boardList);
        return "board/list";
    }


    @PostMapping("/api/board/{id}/delete")
    public String removeBoard(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글삭제하기(id, sessionUser);
        System.out.println(id);
        return "redirect:/";
    }


    @GetMapping("/api/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }


    @PostMapping("/api/board/save")
    public String save(@Valid BoardRequest.SaveDTO saveDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.게시글쓰기(saveDTO, sessionUser);

        return "redirect:/";
    }


    @GetMapping("/api/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.게시글수정화면(id, sessionUser);
        request.setAttribute("model", board);
        return "board/update-form";
    }

    @PostMapping("/api/board/{id}/update")
    public String update(@PathVariable("id") int id, @Valid BoardRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글수정(id, updateDTO, sessionUser);
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO model = boardService.게시글상세보기(sessionUser, id);
        request.setAttribute("model", model);

        return "board/detail";


    }

    //테스트
    @ResponseBody
    @GetMapping("/v2/board/{id}")
    public BoardResponse.DetailDTO detailV2(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO model = boardService.게시글상세보기(sessionUser, id);

        return model;
    }

    //테스트2
    @GetMapping("/v3/board/{id}")
    public @ResponseBody Board detailV3(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Board model = boardService.게시글상세보기V3(sessionUser, id);

        return model;
    }


    //로그인 하고 작성한 게시글 조회하면 JSON
    @GetMapping("/v2/api/board/{id}/update-form")
    public @ResponseBody BoardResponse.DTO updateForm(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO model = boardService.게시글수정화면V2(id, sessionUser);
        return model;
    }





    @ResponseBody
    @GetMapping("/test/v1")
    public Resp restV1() {
        User u = new User();
        u.setId(1);
        u.setUsername("ssar");
        u.setPassword("1234");
        u.setEmail("ssar@nate.com");

        return Resp.ok(u);
    }

    @ResponseBody
    @GetMapping("/test/v2")
    public Resp restV2() {
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("ssar");
        u1.setPassword("1234");
        u1.setEmail("ssar@nate.com");

        User u2 = new User();
        u2.setId(2);
        u2.setUsername("cos");
        u2.setPassword("1234");
        u2.setEmail("cos@nate.com");


        List<User> users = Arrays.asList(u1, u2);
        return Resp.ok(users);
    }


    //터뜨리기 테스트 메서드
    @ResponseBody
    @GetMapping("/test/v3")
    public Resp restV3() {
        //@ResponseBody로 요청을 받기 때문에 데이터로 응답해줘야 한다. 아래는 우리가 만든 자바스크립트 코드가 응답으로 나간다.
        //throw new Exception404("유저를 찾을 수 없습니다.");

        //Ajax로 요청을 받았으면 데이터로 응답해줘야 한다.
        return Resp.fail(404, "유저를 찾을 수 없습니다.");

    }


    @ResponseBody
    @GetMapping("/test/v4")
    public Resp restV4(HttpServletResponse response) {
        //이거 없이 Resp.fail하면 실제로 코드는 200이 전달된다. 그래서 응답에 확실하게 응답 코드를 넣어준다.
        response.setStatus(404);
        return Resp.fail(404, "유저를 찾을 수 없습니다.");
    }



    @GetMapping("/test/v5")
    public ResponseEntity<?> restV5() {
        // @ResponseBody 생략. 상태 코드를 넣을 수 있다.
        //v4에서 처럼 숫자를 넣는다면 실수를 할 수도 있다.
        //실수를 덜 하기 위해서, 그리고 편하게 사용하기 위해서 ResponseEntity를 사용한다.
        //에러코드 전달할 때는 HttpStatus에 만들어져 있는 상수를 사용한다.
        return new ResponseEntity<>(Resp.fail(404, "유저를 찾을 수 없습니다."), HttpStatus.NOT_FOUND);

    }


    @GetMapping("/test/v6")
    public ResponseEntity<?> restV6() {
        // @ResponseBody 생략. 상태 코드를 넣을 수 있다.
       throw new ExceptionApi404("페이지를 찾을 수 없습니다.");

    }


}
