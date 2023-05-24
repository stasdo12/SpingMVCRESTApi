package ua.donetc.project2boot.controllers;

import com.binance.api.client.domain.market.TickerPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ua.donetc.project2boot.security.UserDetails;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping()
    public String index(Model model, Authentication authentication) {
        assert authentication != null;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Если пользователь с ролью "ADMIN", отдаем страницу администратора
            return "redirect:/admin";
        } else {
            // В противном случае, отдаем страницу для обычных пользователей
            return "redirect:/user";
        }

    }


    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(personDetails.getUsers());
        return personDetails.getUsername() + personDetails.getAuthorities() + personDetails.getUsers();
    }
}
