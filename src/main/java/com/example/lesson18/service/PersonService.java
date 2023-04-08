package com.example.lesson18.service;

import com.example.lesson18.models.Person;
import com.example.lesson18.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {
    //внедряем репозиторий
    public final PersonRepository personRepository;
    public final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // создадим 2 метода : проверка по логину, сохранение пользователя

    public Person findByLogin(Person person){
        Optional<Person> personDB = personRepository.findByLogin(person.getLogin());
        return personDB.orElse(null);
    }
@Transactional
    public void registerUser(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword())); // при регистрации объекту пользователя задается пароль - через БиКрипт - то есть хэшированный
        person.setRole("ROLE_USER"); // указываем, что по умолчанию все пользователи - не админы
        personRepository.save(person);
    }
}
