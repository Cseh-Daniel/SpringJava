package com.example.mozimusor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class Controllers {
    User mainUser;
    @GetMapping("/")
    public String aboutUs(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        return "AboutUsPage";

    }



    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("user",new User());
        return "Login";

    }
    @PostMapping("/login")
    public String LoginSubmit(@ModelAttribute User user, Model model){

        model.addAttribute("CurrentUser",user);
        mainUser=user;
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("newuser",new User());
        return "SignUp";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute User user,Model model)
    {
        model.addAttribute("newuser",user);
        //adatbazis
        user=null;
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        mainUser=null;
        return "redirect:/";

    }

    @GetMapping("/contact")
    public String Contact(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("contactor",new Message());
        return "Contact";

    }



    @PostMapping("/contact")
    public String sendmessage(@ModelAttribute Message mes, Model model){
        model.addAttribute("CurrentUser",mainUser);
        //adatb
        model.addAttribute("contactor",mes);
        return "redirect:/contact";
    }




}
