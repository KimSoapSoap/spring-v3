package org.example.springv3.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

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
