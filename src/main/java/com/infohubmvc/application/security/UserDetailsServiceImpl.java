package com.infohubmvc.application.security;


import com.infohubmvc.application.data.entity.User;
import com.infohubmvc.application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  {

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public List<UserDetails> loadUserByUsername() throws UsernameNotFoundException {
        List<User> usersList = userRepository.findAll();
        List<UserDetails> userDetailsList = new ArrayList<>();
        for(User user: usersList){
            userDetailsList.add(UserDetailsImpl.build(user));
        }
        return userDetailsList;
    }


}
