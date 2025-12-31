package com.example.demo.Web;

import com.example.demo.Data.Auto.Auto;
import com.example.demo.Data.Auto.AutoService;
import com.example.demo.Data.MarketService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.Data.User.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final AutoService autoService;
    private final MarketService marketService;
    private final HttpSession session;

    @GetMapping
    public String market(Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("autos", autoService.getMarket());
        return "market";
    }

    @GetMapping("/{id}")
    public String carDetails(@PathVariable Long id, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        Auto car = autoService.findById(id).orElseThrow(() -> new RuntimeException("Auto not found"));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("car", car);
        return "market_car";
    }

    @PostMapping("/{id}/buy")
    public String buy(@PathVariable Long id, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            ra.addFlashAttribute("error", "Спочатку увійди в акаунт, щоб купити авто.");
            return "redirect:/sign_in";
        }

        try {
            marketService.buy(id, currentUser.getId());
            ra.addFlashAttribute("message", "Покупка успішна ✅");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/market/" + id;
    }
}

