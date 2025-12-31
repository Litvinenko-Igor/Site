package com.example.demo.Web;

import com.example.demo.Data.User.User;
import com.example.demo.Data.User.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "main";
    }

    @GetMapping("/sign_up")
    public String showRegisterForm(Model model) {
        model.addAttribute("name", "");
        model.addAttribute("username", "");
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        model.addAttribute("confirm", "");
        model.addAttribute("message", "");
        model.addAttribute("error", "");
        return "sign_up";
    }

    @GetMapping("/sign_in")
    public String showSignForm(Model model) {
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String handleSignIn(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("password", password);

        if (!email.endsWith("@gmail.com")) {
            model.addAttribute("error", "Пошта має закінчуватись на @gmail.com ❌");
            return "sign_in";
        }

        if (password.length() < 8) {
            model.addAttribute("error", "Пароль має містити щонайменше 8 символів ❌");
            return "sign_in";
        }

        try {
            User current = userService.login(email, password);
            model.addAttribute("currentUser", current);
            session.setAttribute("currentUser", current);
            return "main";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "sign_in";
        }
    }


    @PostMapping("/sign_up")
    private String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return "sign_up";
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match ❌");
            return "sign_up";
        }

        userService.createUser(userDTO.getName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        model.addAttribute("message", "Користувач успішно зареєстрований ✅");
        return "sign_in";
    }

    @GetMapping("/exit")
    public String logout(RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Ви вийшли з акаунту");
        return "redirect:/sign_in";
    }
}
