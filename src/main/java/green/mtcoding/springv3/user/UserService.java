package green.mtcoding.springv3.user;

import lombok.RequiredArgsConstructor;
import green.mtcoding.springv3.core.error.ex.Exception401;
import green.mtcoding.springv3.core.error.ex.Exception400;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다"));

        return user;
    }

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {

        //회원가입시 중복체크니까 존재하지 않으면 정상 로직이다.
       Optional<User> userOP = userRepository.findByUsername(joinDTO.getUsername());

        if(userOP.isPresent()) {
            throw new Exception400("이미 존재하는 유저입니다.");
        }
        userRepository.save(joinDTO.toEntity());

    }

    public boolean 유저네임중복되었니(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);
        //중복유무 판별하기 때문에 true or false를 무조건 날려야 하기 때문에 throw 던지면 안 된다.

        if(userOp.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
