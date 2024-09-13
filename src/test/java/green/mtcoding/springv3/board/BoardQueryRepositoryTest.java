package green.mtcoding.springv3.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(BoardQueryRepository.class)
@DataJpaTest
class BoardQueryRepositoryTest {
    @Autowired
    private BoardQueryRepository boardQueryRepository;

    @Test
    void selectV1_test() {
        // given


        // when
        List<BoardResponse.BoardListDTO> boardList = boardQueryRepository.selectV1();

        // eye
        System.out.println(boardList);
    }
}