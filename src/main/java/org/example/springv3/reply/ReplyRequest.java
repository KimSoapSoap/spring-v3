package org.example.springv3.reply;

import lombok.Data;
import org.example.springv3.board.Board;
import org.example.springv3.user.User;


//요청시에 필요한 정보를 OORequest클래스를 만들고 OODTO를 만들어서 사용.
//또한 응답시에 전달할 정보를 Entity로 그대로 전달하지 말고 OOResponse클래스를 만들고 OODTO를 만들어서 사용
public class ReplyRequest {

    //getter, setter 깜빡하지 않을 것.
    @Data
    public static class SaveDTO {
        private Integer boardId;
        private String comment;
        
        //댓글 쓸 때 댓글쓴 유저 정보를 따로 SaveDTO에서 받지 않는 이유는
        //sessionUser 정보를 사용할 것이기 때문이다. 
        // -> toEntity()로 id빼고 필요한 정보 다 넣어서 객체 만들어서 persist할 때 sessionUser 정보 넣어줌
        

        //insert 할 때는 persist 해줄 것이므로 toEntity()를 만들어서 사용하자.
        //지금은 JPA Repository 사용중이므로 우리가 직접 persist 해줄 필요 없이 JPA Repository의 save 메서드 사용하면 된다
        // toEntity() 메서드 이름은 그냥 우리 강의에서의 컨벤션이다.
        //id값만 빼고 필요한 거 다 넣으면 된다. id는 persist 해서 영속성 컨텍스트로 넣어주면 db에 들어가면서 부여받는다.
        //toEntity로 필요한 정보를 넣어줄 때 builder가 편하므로 Reply에 가서 생성자 만들고 @Builder 붙여준다.
        

        //insert into reply_tb(comment, board_id, user_id, created_at) values('댓글', 5, 1, now())
        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment)
                    .user(sessionUser)
                    .board(board)
                    .build();
        }

    }
}
