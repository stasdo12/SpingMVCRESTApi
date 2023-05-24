package ua.donetc.project2boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.repositories.UserRepo;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {


    private final UserRepo userRepo;

    @Autowired
    public UserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> users = userRepo.findUsersByUsername(username);
    if (users.isEmpty())
        throw new UsernameNotFoundException("User not found");

    return new ua.donetc.project2boot.security.UserDetails(users.get());
    }
}
