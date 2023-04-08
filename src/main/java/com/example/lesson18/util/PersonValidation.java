package com.example.lesson18.util;

import com.example.lesson18.models.Person;
import com.example.lesson18.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidation implements Validator {
    public final PersonService personService;



    public PersonValidation(PersonService personService) {
        this.personService = personService;
    }

    // для какой модели предназначен валидатор:
    @Override
    public boolean supports(Class<?> clazz) {
        //return false;
        return Person.class.equals(clazz);
    }
// прописывается вся валидация - приходит объект ошибок и объект валидации
    @Override
    public void validate(Object target, Errors errors) {
        // Object target - как объект валидации - должны даункастить до объекта Персон
        Person person = (Person) target;
        if(personService.findByLogin(person) != null){ // если пользователь по логину найден, значит, он есть в системе
            errors.rejectValue("login", "", "Данный логин уже занят"); // создаем объект ошибки
        }
    }
}
