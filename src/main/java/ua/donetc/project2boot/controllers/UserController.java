package ua.donetc.project2boot.controllers;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import ua.donetc.project2boot.dto.UserDTO;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.services.CodeService;
import ua.donetc.project2boot.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BinanceApiRestClient binanceAPI;

    private final CodeService codeService;

    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, BinanceApiRestClient binanceAPI, CodeService codeService, ModelMapper modelMapper) {
        this.userService = userService;
        this.binanceAPI = binanceAPI;
        this.codeService = codeService;

        this.modelMapper = modelMapper;
    }



    @GetMapping()
    public String userHelloPage(Model model){
        List<TickerPrice> allPrice = binanceAPI.getAllPrices();
        List<TickerPrice> firstTwentyPrices = allPrice.subList(0, 20);
        model.addAttribute("binance", firstTwentyPrices);
        return "user/index";
    }

    @GetMapping("/all-pull")
    public String allPull(Model model){
        model.addAttribute("pools", userService.findUsersByRole("ROLE_ADMIN"));
        return "user/all-pull";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("user", userService.findOneUser(id));
        return "admin/show-user-info";
    }

    @GetMapping("/findCode")
    public String findCode(){
        return "user/findCode";
    }



    @Transactional
    @PostMapping("/findCode")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("codes", codeService.getCodeByNameStartingWithEE(query));
        return "user/findCode";
    }


    @GetMapping("/all_code")
    public String allCodeWithPagination(Model model,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "codePerPage", defaultValue = "20") Integer codePerPage) {


        Page<Code> codePage = codeService.findAllCodeWithPagination(page, codePerPage);

        int totalPages = codePage.getTotalPages();
        int currentPage = codePage.getNumber();

        model.addAttribute("codes", codePage.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        return "user/all-code";
    }


    @GetMapping("/about")
    public String aboutPage(){
        return "user/about";
    }
    @GetMapping("/contact")
    public String contactPage(){
        return "user/contact";
    }



    private Users convertToUser(UserDTO userDTO){
        Users users = modelMapper.map(userDTO, Users.class);
        enrichUsers(users);
        return users;
    }

    private void enrichUsers(Users users) {
        users.setRole("ROLE_USER");
    }


}
