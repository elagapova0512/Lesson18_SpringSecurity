package com.example.lesson18.security;

import com.example.lesson18.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person; // хранит объект модели Персон

    public PersonDetails(Person person) {
        this.person = person;
    }

    public Person getPerson(){
        return this.person;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// вернет роль пользователя
       return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
        //return null;
    }

    @Override
    public String getPassword() {
        //return null;
        // метод будет возвращать пароль конкретного пользователя
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        //return null; - возврат логина пользователя
        return this.person.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true; // ставим везде правда, считаем, что все пользователи у нас существуют и не заблокированы
        // return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return false;
        return  true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return false;
        return  true;
    }

    @Override
    public boolean isEnabled() {
        //return false;
        return  true;
    }
}
