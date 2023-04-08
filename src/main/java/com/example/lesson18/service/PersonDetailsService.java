package com.example.lesson18.service;

import com.example.lesson18.models.Person;
import com.example.lesson18.repository.PersonRepository;
import com.example.lesson18.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    public final PersonRepository personRepository;
@Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
// метод интерфейса (получает на вход логин)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return null;
        Optional<Person> person = personRepository.findByLogin(username);
        if(person.isEmpty()){
            // Спринг Секьюрити поймает исключение и выведет его на страницу аутентификации
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
}
