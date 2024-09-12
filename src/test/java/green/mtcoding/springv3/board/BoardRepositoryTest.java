package green.mtcoding.springv3.board;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void mFindAll_test2() throws Exception {
        //given
        String title = "제목";

        //when
        Pageable pageable = PageRequest.of(0, 3);
        Page<Board> boardPG = boardRepository.mFindAll(title, pageable);

        //eye
        //데이터를 Json으로 바꾸는 것.
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(boardPG);
        System.out.println(responseBody);

        // 참고. Json을 자바 객체로 바꾸는 방법.
        // Json 데이터를 Board 객체로 리턴
        // ObjectMapper om = new ObjectMapper();
        // om.readValue(responseBody, Board.class);


    }


    @Test
    public void mfindByIdWithReply_test() {
        Board board = boardRepository.mFindByIdWithReply(5).get();
        System.out.println(board.getReplies());
    }


    @Test
    public void mFindAll_test() {
        //given
        String title = "제";


        //when
        List<Board> boardList = boardRepository.mFindAll(title);


        //then
        System.out.println(boardList.size());
        System.out.println(boardList.get(0).getTitle());
    }


}
