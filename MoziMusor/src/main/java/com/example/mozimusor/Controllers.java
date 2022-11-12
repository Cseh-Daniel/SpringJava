package com.example.mozimusor;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;


@Controller
public class Controllers {
    User mainUser;
    static  String url="jdbc:mysql://localhost/mozimusor?user=root";
    UserDAO uDao=new UserDAO(url);
    MessageDAO mDao=new MessageDAO(url);

    DatabaseDAO dDao=new DatabaseDAO(url);

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
            mainUser.setRole(uDao.getRole(mainUser));

            model.addAttribute("CurrentUser",mainUser);
            return "redirect:/";
        }else{
            mainUser=null;

            model.addAttribute("CurrentUser",mainUser);
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
        model.addAttribute("serverInfo",null);
        model.addAttribute("contactor",new Message());
        return "Contact";

    }



    @PostMapping("/contact")
    public String sendMessage(@ModelAttribute Message mes, Model model){
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("contactor",new Message());

        if(mainUser!=null){
        mes.setName(mainUser.getName());
        }

        String serverMessage=mDao.sendMessage(mes);
        model.addAttribute("serverInfo",serverMessage);
        return "Contact";
    }


    @GetMapping("/messages")
    public String readMessages(Model model){
        ArrayList<Message> msgList=mDao.getAll();
        Collections.reverse(msgList);

        if(!(uDao.getRole(mainUser).equals("Admin"))){return "redirect:/";}
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("Messages",msgList);
        return "Messages";
    }
    @GetMapping("/database")
    public String readDatabase(Model model){
        ArrayList<Database> databasesList=dDao.getAll();

        if(!(uDao.getRole(mainUser).equals("Admin") || uDao.getRole(mainUser).equals("User") )){return "redirect:/";}
        model.addAttribute("CurrentUser",mainUser);
        model.addAttribute("Databases",databasesList);
        return "Database";
    }



    @GetMapping("/api/{id}")
    @ResponseBody
    public String restAPI(@PathVariable int id){
        MoviesDAO movieDao=new MoviesDAO(url);

    return movieDao.getMoviebyID(id);
    }


}
