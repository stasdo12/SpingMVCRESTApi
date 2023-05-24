package ua.donetc.project2boot.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.services.UserDetailService;
import ua.donetc.project2boot.services.UserService;

@Component

public class UserValidator implements Validator {

    private final UserService userService;
    private final UserDetailService userDetailService;

    @Autowired
    public UserValidator(UserService userService, UserDetailService userDetailService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Users users = (Users) target;

        try {
            userDetailService.loadUserByUsername(users.getUsername());
        }catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("username", "", "Person with this name not available");
    }
}
