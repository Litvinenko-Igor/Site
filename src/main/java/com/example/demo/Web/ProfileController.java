package com.example.demo.Web;

import com.example.demo.Data.Auto.AutoService;
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
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AutoService autoService;
    private final UserService userService;

    @GetMapping
    public String profile(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        model.addAttribute("user", currentUser);
        return "profile";
    }

    @GetMapping("/make_orders")
    public String showCreateAutoForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        model.addAttribute("user", currentUser);
        model.addAttribute("auto", new AutoDTO());
        return "make_orders";
    }

    @GetMapping("/my_orders")
    public String showMyAutos(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        model.addAttribute("user", currentUser);
        model.addAttribute("autos", autoService.getMyAuto(currentUser.getId()));
        return "my_orders";
    }

    @PostMapping("/make_orders")
    public String createAuto(@Valid @ModelAttribute("auto") AutoDTO autoDTO,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", currentUser);
            return "make_orders";
        }

        autoService.createAutoForUser(
                currentUser.getId(),
                autoDTO.getBrand(),
                autoDTO.getModel(),
                autoDTO.getPrice(),
                autoDTO.getTopSpeed(),
                autoDTO.getImage(),
                autoDTO.getFuelType(),
                autoDTO.getDescription()
        );

        return "redirect:/profile";
    }

    @GetMapping("/topup")
    public String topupPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        model.addAttribute("user", currentUser);
        return "topup";
    }

    @PostMapping("/topup")
    public String topup(@RequestParam double amount,
                        HttpSession session,
                        RedirectAttributes ra) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/sign_in";

        try {
            User updated = userService.addBudget(currentUser.getId(), amount);
            session.setAttribute("currentUser", updated);
            ra.addFlashAttribute("message", "Баланс поповнено ✅");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }

}