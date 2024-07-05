package com.example.superproject1.user;


import com.example.superproject1.repository.entity.User;
import com.example.superproject1.user.dto.LoginRequest;
import com.example.superproject1.user.dto.SignupRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Boolean signup(SignupRequest signupRequest) {
        try {

            Optional<User> user = userRepository.findByEmail(signupRequest.getEmail());

            if(user.isPresent()){
                throw new RuntimeException("이미 이 이메일의 사용자가 있습니다.");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User userFound = User.builder()
                    .email(signupRequest.getEmail())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .build();

            userRepository.save(userFound);
            return true;

        } catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<User> login(LoginRequest request){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return user;
        }

        return Optional.empty();
    }

    // userId로 user 조회
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 userId가 없습니다."));
    }
}
