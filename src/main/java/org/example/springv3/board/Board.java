package org.example.springv3.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.springv3.reply.Reply;
import org.example.springv3.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

/*
@Builder
@AllArgsConstructor

Reply가 추가되면서 이를 추가해서 모든 필드를 생성자로 추가해서 빌더를 만들어줄 수 있지만
빌더에는 컬렉션이 들어갈 수 없기 때문에 돌아가다가 터진다.
빌더패턴에는 컬렉션이 들어갈 수 없기 때문에
수동으로 전체 필드를 추가해서 생성자를 만들어주는데 이때 컬렉션을 제외하고 추가해서
해당 생성자에 @Builder를 추가해준다.

*/


@NoArgsConstructor // 빈 생성자 (하이버네이트가 om 할때 필요)
@Setter
@Getter
@Table(name = "board_tb")
@Entity // DB에서 조회하면 자동 매핑이됨
public class Board {
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_incremnt 설정, 시퀀스 설정
    @Id // PK 설정
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private Timestamp createdAt;

    // fk
    //@JsonIgnoreProperties({"password"})  // 게시판 작성자 user정보의 password를 제외. 알아만 두자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // reply

    //@JsonIgnoreProperties({"board", "createdAt"})  // Json으로 만들 때 얘는 converting 하지 마라는 것
    @OneToMany(mappedBy = "board")
    private List<Reply> replies;




    @Builder
    public Board(Integer id, String title, String content, Timestamp createdAt, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}