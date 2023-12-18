package hse.coursework.socialnetworkthoughts.security.service;

import hse.coursework.socialnetworkthoughts.security.mapper.UserMapper;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
