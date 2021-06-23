package dev.lochness.library.config;

import dev.lochness.library.domain.User;
import dev.lochness.library.repository.UserRepository;
import dev.lochness.library.security.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsersRegistration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersRegistration(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        doRegisterUsers();
    }

    private void doRegisterUsers() {

        userRepository.save(User.builder()
                .username("piterpen64")
                .email("piterpen64@gmail.com")
                .password(passwordEncoder.encode("piterpen64"))
                .role(Role.GUEST.name())
                .build());

        userRepository.save(User.builder()
                .username("handsome_guy")
                .email("handsome_guy2012@mail.ru")
                .password(passwordEncoder.encode("handsome_guy"))
                .role(Role.USER.name())
                .build());

        userRepository.save(User.builder()
                .username("satan666")
                .email("owner@hell.com")
                .password(passwordEncoder.encode("satan666"))
                .role(Role.ADMIN.name())
                .build());
    }
}
