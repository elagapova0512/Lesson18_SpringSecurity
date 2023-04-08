package com.example.lesson18.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.lesson18.util") // всё, что в пакете util будет сканироваться
// пометим ПерсонВалидейшн  аннотацией Компонент
public class SpringConfig {
}
