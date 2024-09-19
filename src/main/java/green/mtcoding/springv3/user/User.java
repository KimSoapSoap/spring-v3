package green.mtcoding.springv3.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@Table(name = "user_tb")
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String username; // 아이디
    @Column(nullable = false)
    private String password; // hash해서 저장.
    @Column(nullable = false)
    private String email;
    // email 인증 여부 boolean.
    // 19세 이상 여부

    @CreationTimestamp
    private Timestamp createdAt;


    //로그인 시도 5번 이상 틀렸는지. 로그인 시도 틀린 횟수
    //잠김 여부
    //활성화 여부(1년 지나면 비활성화) -> 재활성화 시킬 때 이메일 확인이나 본인확인 등 검증
    //탈퇴 여부. db에서 지우지 않고 5년간(정책에 따라) 보관하는데 boolean값으로 탈퇴여부. 탈퇴시간과 함께.
    //로그인시에 device 장비 (window, mac, 휴대폰, 컴퓨터)
    // ip 저장



    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}