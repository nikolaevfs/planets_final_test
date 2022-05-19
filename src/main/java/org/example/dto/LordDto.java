package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LordDto {
    @NotBlank(message = "shouldn't be blank")
    private String name;
    @NotNull(message= "shouldn't be empty")
    @Max(value = 150, message = "shouldn't be more than 150")
    @Min(value = 0, message = "shouldn't be less than 0")
    private Integer age;
}
