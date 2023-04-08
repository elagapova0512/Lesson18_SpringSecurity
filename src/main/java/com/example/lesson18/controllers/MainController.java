package com.example.lesson18.controllers;

import com.example.lesson18.models.Person;
import com.example.lesson18.security.PersonDetails;
import com.example.lesson18.service.PersonService;
import com.example.lesson18.util.PersonValidation;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    // внедряем Валидатор + конструктор:
    public final PersonValidation personValidation;
    public final PersonService personService;

    public MainController(PersonValidation personValidation, PersonService personService) {
        this.personValidation = personValidation;
        this.personService = personService;
    }


    @GetMapping("/index")
    public String index(){
        // получаем объект аутентификации --> с помощью SpringContextHolder обращаемся к контексту, на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, кот. был положен  туда после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // далее этот полученный объект можем преобразовать в объект PersonDetails который позволяет нам работать с пользователем
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // можем получить ссылку на объект (используя метод) и данные
        System.out.println(personDetails.getPerson());
        System.out.println("Id пользователя - " + personDetails.getPerson().getId());
        System.out.println("Логин пользователя " + personDetails.getPerson().getLogin());
        System.out.println("Пароль пользователя " + personDetails.getPerson().getPassword());
        return "index";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("newUser") Person person){ // при отправке формы Спринг сам положит объект в модель
        // если атрибута не будет в шаблоне, или объект не придёт, то Спринг положит пустой объект в Персон
        return "registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute("newUser") @Valid Person person, BindingResult bindingResult){
        // у нас есть кастомный валидатор
        personValidation.validate(person,bindingResult);
 if (bindingResult.hasErrors()){
     return "registration";
 }
     personService.registerUser(person);
        return "redirect:/index";
    }
}
