package ua.donetc.project2boot.controllers;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.donetc.project2boot.entity.Code;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.services.CodeService;
import ua.donetc.project2boot.services.UserService;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BinanceApiRestClient binanceApi;

    @MockBean
    private CodeService codeService;

    @Test
    @WithMockUser
    public void testUserHelloPage() throws Exception {
        List<TickerPrice> allPrice = Arrays.asList(
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice(),
                new TickerPrice()
        );
        when(binanceApi.getAllPrices()).thenReturn(allPrice);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/index"))
                .andExpect(model().attributeExists("binance"));
    }

    @Test
    @WithMockUser
    public void testAllPull() throws Exception {
        mockMvc.perform(get("/user/all-pull"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/all-pull"));
    }

    @Test
    @WithMockUser
    public void testShow() throws Exception {
        int userId = 1;

        when(userService.findOneUser(userId)).thenReturn(new Users());

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/show-user-info"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void testFindCodePage() throws Exception {
        mockMvc.perform(get("/user/findCode"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/findCode"));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"ROLE_USER"})
    public void testMakeSearch() throws Exception {
        String query = "testQuery";
        List<Code> codes = Arrays.asList(new Code(),new Code());

        when(codeService.getCodeByNameStartingWithEE(query)).thenReturn(codes);

        mockMvc.perform(post("/user/findCode")
                        .param("query" , query ).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/findCode"))
                .andExpect(model().attributeExists("codes"))
                .andExpect(model().attribute("codes", codes));
        //TODO will finish this method
    }

    @Test
    @WithMockUser
    public void testAllCodeWithPagination() throws Exception {
        int page = 0;
        int codePerPage = 20;
        Page<Code> codePage = new PageImpl<>(Arrays.asList(new Code(), new Code()));

        when(codeService.findAllCodeWithPagination(page, codePerPage)).thenReturn(codePage);

        mockMvc.perform(get("/user/all_code")
                        .param("page", String.valueOf(page))
                        .param("codePerPage", String.valueOf(codePerPage)))
                .andExpect(status().isOk())
                .andExpect(view().name("user/all-code"))
                .andExpect(model().attributeExists("codes"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attribute("codes", codePage.getContent()))
                .andExpect(model().attribute("totalPages", codePage.getTotalPages()))
                .andExpect(model().attribute("currentPage", codePage.getNumber()));
    }

    @Test
    @WithMockUser
    public void testAboutPage() throws Exception {
        mockMvc.perform(get("/user/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/about"));
    }

    @Test
    @WithMockUser
    public void testContactPage() throws Exception {
        mockMvc.perform(get("/user/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/contact"));
    }

}