package com.example.demo.Web;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotNull(message = "Name обов'язковий.")
    @Size(min = 3, max = 50, message = "Name має бути від 3 до 50 символів.")
    private String name;


    @NotNull(message = "Username обов'язковий.")
    @Size(min = 3, max = 50, message = "Username має бути від 3 до 50 символів.")
    private String username;

    @NotNull(message = "Password обов'язковий для заповнення.")
    @Size(min = 8, message = "Password має містити мінімум 8 символів.")
    private String password;

    @NotBlank(message = "Підтвердження пароля обов'язкове")
    private String confirmPassword;

    @NotNull(message = "Email обов'язковий для заповнення.")
    @Email(message = "Введи коректну email-адресу.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$",
            message = "Email має закінчуватись на @gmail.com"
    )
    private String email;

}
