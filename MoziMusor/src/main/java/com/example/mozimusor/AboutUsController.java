package com.example.mozimusor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

    @GetMapping("/")
    public String aboutUs() {
        return "AboutUsPage";
    }

}
