package pl.pwsztar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by Lapek on 07.05.2017.
 */
@Controller
public class KotyContoller {

    @RequestMapping(value = "/")
    public String homePage() {
        return "home";
    }

}