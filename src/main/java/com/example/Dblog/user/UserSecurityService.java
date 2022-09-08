package com.example.Dblog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity principal = userRepository.findByusername(username)
                .orElseThrow(() ->{
                    return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.:" + username);
                });
        return new PrincipalDetails(principal);
    }
}
