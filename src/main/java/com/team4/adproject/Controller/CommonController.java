package com.team4.adproject.Controller;

import com.team4.adproject.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public class CommonController {



    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        session.setAttribute("username", username);


        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(@SessionAttribute(name = "username", required = false) String username, Model model) {
        if (username != null) {
            model.addAttribute("username", username);
            return "welcome";
        } else {

            return "redirect:/login";
        }
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        return "register";
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
