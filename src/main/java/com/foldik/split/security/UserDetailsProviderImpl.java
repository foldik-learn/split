package com.foldik.split.security;

import com.foldik.split.persistence.UserRepository;
import com.foldik.split.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class UserDetailsProviderImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsProviderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }
}
