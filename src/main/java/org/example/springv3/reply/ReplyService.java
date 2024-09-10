package org.example.springv3.reply;

import lombok.RequiredArgsConstructor;
import org.example.springv3.board.Board;
import org.example.springv3.board.BoardRepository;
import org.example.springv3.core.error.ex.ExceptionApi400;
import org.example.springv3.core.error.ex.ExceptionApi403;
import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 읽기 최적화를 위해 붙여놓고 내부에 쓰기 메서드에 @Transactional 붙여주자.
// JPA Repository를 사용할 때 이렇게 해준다.
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service  // IoC 등록
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void 댓글삭제(Integer id, User sessionUser) {
        //삭제 전 존재하는지 조회
        //db에서 조회된 객체인지 그냥 객체인지 확인해줌. ~PS 붙여준다. -> 회사마다 다름
        Reply replyPs = replyRepository.findById(id).orElseThrow(
          () -> new ExceptionApi404("해당 댓글을 찾을 수 없습니다.")
        );

        //LAZY로딩이지만 외래키의 pk값인 id는 이미 가지고 있기 때문에 따로 조회 안 한다.
        //만약 getUserName()로 요청을 한다면 그때 쿼리를 날려서 조회한다. (LAZY)
        //그렇기 때문에 지금은 그냥 쓰지만 조회 쿼리가 많이 날아갈 때는 Repository에서 mFindById()만들어서
        // fetch join으로 한 번에 가져오도록 튜닝해준다.
        if(replyPs.getUser().getId() != sessionUser.getId()) {
            throw new ExceptionApi403("댓글 삭제 권한이 없습니다.");
        }

        replyRepository.deleteById(id);
    }

    @Transactional
    public ReplyResponse.DTO 댓글쓰기(ReplyRequest.SaveDTO saveDTO, User sessionUser) {
        System.out.println(2);
        // 1.게시글 존재 유무 확인
        Board boardPS = boardRepository.findById(saveDTO.getBoardId())
                .orElseThrow(() -> new ExceptionApi404("게시글을 찾을 수 없습니다."));
        //AJAX로 API요청이 왔으므로 Api예외를 던진다.

        System.out.println(3);
        // 2. 비영속 댓글 객체 만들기.
        // boardPS는 어차피 게시글 존재 유무를 먼저 확인해야 하므로 이때 조회한 board를 boardPS로 받아서 전달
        // SaveDTO에서 유저 정보 따로 받는 게 아니라 sessionUser 정보 사용.
        Reply reply = saveDTO.toEntity(sessionUser, boardPS);

        System.out.println(4);
        // 3. 게시글 저장
        // db에 넣고 id값이 있어야 등록한 댓글에 id값을 부여할 수 있다.
        // 그렇기 때문에 등록한 댓글정보인 reply 정보를 전달 해줘야 한다.
        // 처음에 비영속 객체를 만들었을 때는 id값이 없지만 save하면서 영속성 컨텍스트에 들어가면
        // insert 쿼리가 나가고 db에 들어가서 pk(id)값이 생긴다.
        // 이때 id값을 가진 reply 정보를 전달 해줄 때 Reply타입(엔티티)으로 그대로 전달해주면
        // DTO로 응답하는 1차적인 목적은 화면에 필요한 정보를 제공해주기 위함.
        // 또한 엔티티를 그대로 반환하기 때문에 API 스펙이 노출되고 또한 JSON으로 파싱할 때
        // 필요한 정보가 계속 반복돼서 무한루프 걸려서 터진다.(예를들면 게시글에 연관된 댓글정보. 댓글정보에 연관된 게시글 정보 서로 반복)
        // 그렇기 때문에 DTO를 만들어서 응답을 해준다. DTO 만들 때 생성자에 Entity를 전달해서 만들어 주면 된다.

        replyRepository.save(reply);
        // JPA Repository의 save()를 reply를 전달하면서 사용해서
        // 비영속 객체인 reply를 db에 넣고 영속객체로 만들었다.
        // JPA Repository가 save에서드로 persist를 대신 사용해준 것.
        // reply가 db에 들어갔기 때문에 영속성 컨텍스트에 저장되고(1차 캐시에 저장)
        // 아래에 new ReplyResponse.DTO(reply)로 reply를 사용할 때는 1차캐시에 저장된
        // 영속 객체(pk값이 존재 -> db에 insert되진 않았지만 jpa가 임의로 부여한)인 reply가 전달됨
        // 댓글쓰기 요청해온 프론트에 reply id값을 전달 해줄 수 있다.(삭제나 수정시 id값 필요)

        //단, @GeneratedValue(strategy = GenerationType.IDENTITY)경우에는 persist(entity) 하면
        //즉시 쿼리가 날아간다. 이 경우엔 즉시 insert 쿼리가 날아가므로 바로 db에 쿼리가 날아간다.
        //같은 트랜잭션 내부에서 쓰기 지연 SQL 저장소에 쿼리 몇개 모아놨다 날리느냐 아니냐의 차이라서
        // IDENTITY 전략이 다른 전략에 비해 네트워크 성능이 그리 차이나진 않는다.


        System.out.println("reply  id : " +  reply.getId());


        System.out.println(5);
        return new ReplyResponse.DTO(reply);
    }

}
