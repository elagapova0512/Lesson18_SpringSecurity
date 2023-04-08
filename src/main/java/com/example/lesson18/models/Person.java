package com.example.lesson18.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity // что будет подключаться к БД
@Table(name = "person")//что подключается к существующей таблице

public class Person {
    @Id
    @Column(name = "id")// подключаемся к существующему полю в таблице
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    @NotEmpty(message = "Поле логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    private String login;
    @Column(name = "password")
    @NotEmpty(message = "Пароль не может быть пустым")
        private String password;
    @Column(name = "role")
    private String role;// две роли. По умолчанию Юзер, и 1 админ

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person(String role) {
        this.role = role;
    }

    public Person(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Person() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
