package green.mtcoding.springv3.User;

import green.mtcoding.springv3.user.User;
import green.mtcoding.springv3.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;



}
