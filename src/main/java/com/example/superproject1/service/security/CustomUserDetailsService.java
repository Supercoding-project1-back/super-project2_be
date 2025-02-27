package com.example.superproject1.service.security;

import com.example.superproject1.repository.users.User;
import com.example.superproject1.repository.users.UserRepository;
import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Primary
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(email).orElseThrow(()->
                 new UsernameNotFoundException("(토큰에러) 해당 이메일을 찾을 수 없습니다."));

         return CustomUserDetails.builder()
                 .id(user.getId())
                 .email(user.getEmail())
                 .password(user.getPassword())
                 .name(user.getName())
                 .phoneNumber(user.getPhoneNumber())
                 .address(user.getAddress())
                 .gender(user.getGender())
                 .birthDate(user.getBirthDate())
                 .createAt(user.getCreateAt())
                 .authorities(user.getUserRoles()
                         .stream().map(u->u.getRoles())
                         .map(r->r.getName())
                         .collect(Collectors.toList()))
                 .build();
    }
}
