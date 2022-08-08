package com.infohubmvc.application.security;

import org.springframework.security.core.context.SecurityContextHolder;

//@Service
//@RequiredArgsConstructor
public class BaseService {

    public UserDetailsImpl getLoggedInUser() {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            throw new UserNotLoggedInException(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }

        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    }

}
