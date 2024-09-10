package org.example.springv3.reply;

import lombok.Data;

//요청시에 필요한 정보를 OORequest클래스를 만들고 OODTO를 만들어서 사용.
//또한 응답시에 전달할 정보를 Entity로 그대로 전달하지 말고 OOResponse클래스를 만들고 OODTO를 만들어서 사용
public class ReplyResponse {

    //getter, setter 깜빡하지 않을 것.
    @Data
    public static class DTO {

        private Integer id;
        private String comment;
        private String username;


        public DTO(Reply reply) {
            this.id = reply.getId();
            System.out.println("--1");
            this.comment = reply.getComment();
            System.out.println("--2");
            //만약 User정보가 없다 해도 getter 호출하니까 LAZY로딩 일어나서 정보를 가져 온다.
            this.username = reply.getUser().getUsername();
            System.out.println("--3");


        }

    }
}
