package org.example.springv3.board;

import lombok.Data;
import org.example.springv3.reply.Reply;
import org.example.springv3.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Data
    public static class DetailDTO {

        private Integer id;
        private String title;
        private String content;
        private Boolean isOwner;

        private UserDTO user;

        //댓글들
        private List<ReplyDTO> replies=new ArrayList<ReplyDTO>();


        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = false;

            if (sessionUser != null) {
                if (board.getUser().getId() == sessionUser.getId()) {
                    isOwner = true; // 권한체크
                }
            }

            this.user = new UserDTO(board.getUser());

            for (Reply reply : board.getReplies()) {
                replies.add(new ReplyDTO(reply, sessionUser));

            }
        }

        @Data
        public class UserDTO {
            private Integer id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String comment;
            private String username;
            private Boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.username = reply.getUser().getUsername();
                this.isOwner = false;

                if (sessionUser != null) {
                    if (reply.getUser().getId() == sessionUser.getId()) {
                        isOwner = true; // 권한체크
                    }
                }
            }
        }

    }


    //DTO의 핵심은 Entity를 이용해서 필요한 정보만 담는 객체(DTO)를 새로 만들어서 사용하는 것이다.
    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String content;

        public DTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
}
