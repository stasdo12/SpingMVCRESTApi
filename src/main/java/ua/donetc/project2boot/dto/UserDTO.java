package ua.donetc.project2boot.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserDTO {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;



}
