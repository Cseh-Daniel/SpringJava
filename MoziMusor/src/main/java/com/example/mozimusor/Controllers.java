package com.example.mozimusor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class Controllers {
    User mainUser;
    static  String url="jdbc:mysql://localhost/mozimusor?user=root";
    UserDAO uDao=new UserDAO(url);

    @GetMapping("/")
    public String aboutUs(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        return "AboutUsPage";

    }



    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("BadLogin","");
        model.addAttribute("user",new User());
        return "Login";

    }
    @PostMapping("/login")
    public String LoginSubmit(@ModelAttribute User user, Model model){


        model.addAttribute("CurrentUser",user);


        if(uDao.checkPass(user.getPass(),user.getEmail())){
            System.out.println("Sikeres bejelentkezés");
            model.addAttribute("BadLogin",null);
            mainUser=user;
            mainUser.setName(uDao.getName(user.getEmail()));
            System.out.println(mainUser.getName());
            model.addAttribute("CurrentUser",mainUser);
            return "redirect:/";
        }else{
            mainUser=null;

            model.addAttribute("CurrentUser",mainUser);
            //  <div class="alert alert-danger" role="alert" th:text="${BadLogin}">Rossz e-mail és jelszó páros!</div>
            model.addAttribute("BadLogin","Rossz e-mail és jelszó páros!");
            System.out.println("Bad Login");
            return "login";
        }

    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("newuser",new User());
        model.addAttribute("serverInfo",null);
        return "SignUp";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute User user,Model model)
    {
        mainUser=null;
        //model.addAttribute("CurrentUser",mainUser);
        //model.addAttribute("newuser",user);
        //adatbazis
        //System.out.println(user.getEmail()+"\n"+user.getName());

        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("newuser",user);
        String serverMessage=uDao.registerUser(user);
        model.addAttribute("serverInfo",serverMessage);
        user=null;
        return "Signup";
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
