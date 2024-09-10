package org.example.springv3.board;

import org.example.springv3.user.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("select b from Board b where b.title like %:title% order by b.title desc")
    List<Board> mFindAll(@Param("title") String title);




    @Query("select b from Board b join fetch b.user left join fetch b.replies r left join fetch r.user where b.id=:id")
    Optional<Board> mFindByIdWithReply(@Param("id")int id);



    //네이티브 쿼리는 @Query(value = "select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id=?, nativeQuery = true)
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> mFindById(@Param("id") int id);

/* 이제 안 쓰고 위에 새로 만든다

    //내가 만든 메서드는 앞에 m을 붙인다. 하는 식으로 컨벤션으로 정한다
    //JPA Repository에서 만들어져 있는 건 정렬 하려면 Sort를 써야 하는데 이렇게 쿼리를 짜면 직접 해줄 수 있다.
    @Query("select b from Board b order by b.id desc")
    List<Board> mFindAll();

*/

}

