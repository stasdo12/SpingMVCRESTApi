package ua.donetc.project2boot.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CodeDTO {

    @NotEmpty
    private String name;
}
