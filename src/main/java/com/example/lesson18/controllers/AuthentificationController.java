package com.example.lesson18.controllers;

import com.example.lesson18.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthentificationController {
    @GetMapping("/logIn")
    public String login(){
        return "logIn";
    }
    // это первый способ работы с моделью
//    @GetMapping("/registration")
//    public String registration(Model model){ //добавляем объект модели
//        model.addAttribute("newUser", new Person());// атрибут - newUser, в качестве значения атрибута - создадим объект модели персон
//        return "registration";
//    }
//@GetMapping("/registration")
//    public String registration(@ModelAttribute("newUser") Person person){ // при отправке формы Спринг сам положит объект в модель
//        // если атрибута не будет в шаблоне, или объект не придёт, то Спринг положит пустой объект в Персон
//        return "registration";
//    }
}
