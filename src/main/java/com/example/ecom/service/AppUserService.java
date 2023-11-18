package com.example.ecom.service;

import com.example.ecom.entity.AppUser;
import com.example.ecom.exceptions.AppUserException;
import com.example.ecom.exceptions.Exceptions;
import com.example.ecom.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByName(username).orElseThrow(() -> new AppUserException(Exceptions.NO_USER_FOUND.getMessage() + username, HttpStatus.NOT_FOUND));
        return User.builder().username(username).password(user.getPassword()).authorities(user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())).build();
    }

    public AppUser addUser(AppUser appUser) {
        appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> findByName(String userName) {
        return appUserRepository.findByName(userName);
    }
}
