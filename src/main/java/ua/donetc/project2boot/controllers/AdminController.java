package ua.donetc.project2boot.controllers;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.services.OrderService;
import ua.donetc.project2boot.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BinanceApiRestClient binanceAPI;
    private final UserService userService;
    private final OrderService orderService;

    public AdminController(BinanceApiRestClient binanceAPI, UserService userService, OrderService orderService) {
        this.binanceAPI = binanceAPI;
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/api-resources")
    public String apiPage(){
        return "/admin/api-page";
    }

    @GetMapping()
    public String adminPage(){
       //HEre we need to add all orders from this admin
        return "/admin/index";
    }

    @GetMapping("/show-all-users")
    public String show(Model model){
        model.addAttribute("users", userService.findUsersByRole("ROLE_USER"));
        return "admin/show-all-users";
    }

    @GetMapping("/show-all-users/{id}")
    public String showUserInfo(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.findOneUser(id));
        return "admin/show-user-info";
    }

    @DeleteMapping("/show-all-users/{id}")
    public String delete(@PathVariable("id")int id){
        userService.deleteUser(id);
        return "redirect:/admin/show-all-users";
    }

    @GetMapping("/show-all-users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.findOneUser(id));
        return "admin/edit-user";
    }

    @PatchMapping("/show-all-users/{id}")
    public String update(@ModelAttribute("user") @Valid Users users, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "admin/edit-user";

        userService.updateUser(id, users);
        return "redirect:/admin/show-all-users";
    }



    @GetMapping("/orders")
    public String allAdminOrders(Model model){
        OrderBook firstPair  = binanceAPI.getOrderBook("BNBUSDT", 10);
        List<OrderBookEntry> bidsFirstPair = firstPair.getBids();
        List<OrderBookEntry> asksFirstPair = firstPair.getAsks();
        List<Candlestick>candlesticks = binanceAPI.getCandlestickBars("BTCUSDT", CandlestickInterval.ONE_MINUTE);
        List<Candlestick> candlesticks1 = candlesticks.subList(0,20);
        model.addAttribute("bids", bidsFirstPair);
        model.addAttribute("ask", asksFirstPair);
        model.addAttribute("ord", orderService.findOrderById(1));
        model.addAttribute("candle", candlesticks1);
        return "admin/orders-admin";
    }

    @GetMapping("/planet")
    public String planetPage(){
        return "auth/loading-page";
    }





}
