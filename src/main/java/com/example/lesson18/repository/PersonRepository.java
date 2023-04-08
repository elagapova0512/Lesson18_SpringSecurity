package com.example.lesson18.repository;

import com.example.lesson18.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    // сначала проверяется логин, пароль, роль
    // ищем запись в таблице Персон по логину
    Optional<Person> findByLogin(String login);

}
