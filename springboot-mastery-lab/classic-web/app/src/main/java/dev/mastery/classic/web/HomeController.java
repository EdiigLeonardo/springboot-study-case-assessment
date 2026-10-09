package dev.mastery.classic.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
  @GetMapping("/") String home(Model model) {
    model.addAttribute("message", "Starter running. Implement the missions.");
    return "home";
  }
}
