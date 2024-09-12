package green.mtcoding.springv3.board;

import lombok.Data;
import green.mtcoding.springv3.reply.Reply;
import green.mtcoding.springv3.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Data
    public static class PageDTO {
        private Integer number; //현재 페이지
        private Integer totalPage; // 전체페이지 개수
        private Integer size; // 한 페이지의 아이템 개수
        private Integer first;
        private Integer last;
        private Integer prev; //현재 페이지 -1
        private Integer next; //현재 페이지 +1
        private List<Content> contents = new ArrayList<>(); //일단 빈 객체를 만들어서 초기화 해둔다.

        @Data
        class Content {
            private Integer id;
            private String title;
        }
    }



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
