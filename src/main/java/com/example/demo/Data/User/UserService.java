package com.example.demo.Data.User;

import com.example.demo.Data.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends CrudService<User, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<User, Long> repo() {
        return userRepository;
    }

    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Користувача не знайдено"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Невірний пароль");
        }
        return user;
    }

    public User createUser(String name, String username, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, String email) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(name);
        user.setEmail(email);
        return userRepository.save(user);
    }
}
