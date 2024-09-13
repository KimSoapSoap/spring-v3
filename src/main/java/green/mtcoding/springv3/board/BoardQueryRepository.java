package green.mtcoding.springv3.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private final EntityManager em;

    public List<BoardResponse.BoardListDTO> selectV1() {
        String sql = """
               
               select id, title, (select count(id) from reply_tb where board_id  = bt.id) reply_count
               from board_tb bt;
                """;
        Query query = em.createNativeQuery(sql);

        JpaResultMapper mapper = new JpaResultMapper();
        List<BoardResponse.BoardListDTO> boardList = mapper.list(query, BoardResponse.BoardListDTO.class);
        return boardList;
    }
}
