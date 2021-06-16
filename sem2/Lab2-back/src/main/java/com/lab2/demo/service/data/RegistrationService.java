package com.lab2.demo.service.data;

import com.lab2.demo.repository.UserRepository;
import com.lab2.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User currentUser) {
        Optional<User> oldUser = userRepository.findByEmail(currentUser.getEmail());
        return oldUser.orElseGet(() -> userRepository.save(currentUser));
    }
}
