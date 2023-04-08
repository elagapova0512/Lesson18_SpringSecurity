package com.example.lesson18.config;

import com.example.lesson18.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity // сообщает, что данный класс будет конфигурировать все настройки секьюрити
@Configuration
public class SecurityConfig{
        //public class SecurityConfig extends WebMvcSecurityConfiguration {
    private final PersonDetailsService personDetailsService;
@Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    @Bean
    // отключаем бикрипт (чтобы обойти защиту от того, что в БД пароли хранятся в явном виде, а Спринг выдает ошибку - There is no PasswordEncoder mapped for the id "null")
    public PasswordEncoder getPasswordEncode(){
        //return NoOpPasswordEncoder.getInstance();// пароли никаким образом шифроваться не будут
         return new BCryptPasswordEncoder(); // позволит хэшировать пароли с помощью БиКрипт
    }
    // метод, в котором все настройки аутентификации
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpS) throws Exception {
// HttpSecurity httpS - отвечает за всю аутентификацию
        // конфигурирует работу Спринг Секьюрити
        httpS// подключение токена
        //httpS.csrf().disable() // отключаем защиту от межсайтовой подделки запросов
                .authorizeHttpRequests()//указываем, что все страницы должны быть защищены аутентификацией
                .requestMatchers("/admin").hasRole("ADMIN")// указываем, что страница админ доступна только для посетителей с ролью Админ. ROLE_ можно не указывать.
                .requestMatchers("/logIn", "error", "/registration","/resources/**", "/static/**").permitAll() //указываем, что неаутентифицированные пользователи попадают на страницу Аутентификации и объект ошибок(первоначально), и далее добавляем страницы
                // если в папке статик будем добавлять какие-то папки, то их тоже надо будет сюда прописывать (к logIn, error)
                .anyRequest().hasAnyRole("USER","ADMIN")//это настройка для доступа на другие страницы для пользователей
                //.anyRequest().authenticated()//указываем, что для всех остальных страниц необходимо вызвать метод authenticated()
                .and() // указываем, что дальше настраивается аутентификация и соединяем её с настройкой доступа
                .formLogin().loginPage("/logIn")//указываем, какой url запрос будет отправлен при заходе на защищенные страницы, замена стандартной спринг страницы аутентификации
                .loginProcessingUrl("/process_logIn")// указывается, по какому url-адресу отправятся данные с формы со страницы logIn.В контроллере не надо будет писать метод и обрабатывать его. Спринг Секьюрити по умолчанию возьмет  данные из этого адреса и обработает их. Спринг Секрьюрити будет ждать объект с формы аутентификации и сверять их с данными в БД
                .defaultSuccessUrl("/index", true)// куда надо перенаправить пользователя после успешной аутентификации, второй параметр - правда- что это делается всегда, в любом случае
                .failureUrl("/logIn?error")//куда перенаправить пользователя в случае проваленной аутентификации, В запрос будет передан объект error , который будет проверяться на форме, и в случае если ошибки есть - выводится сообщение, неправильный пароль или логин
        // добавляем по ? ошибки, чтобы они отражались в форме - так как у нас в шаблоне - th:if="${param.error}"
               .and()
                .logout().logoutUrl("/logOut").logoutSuccessUrl("/logIn");
             return httpS.build();
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncode());
    }

//    public SecurityConfig(PersonDetailsService personDetailsService) {
//        this.personDetailsService = personDetailsService;
//    }

//    private final AuthenticationProvider authenticationProvider;

//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
     // создаем метод конфигурации - отвечающий за настройку аутентификации
    // проверка будет проходить на основе класса АутентификейшнПровайдер и его методами
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);{
        // Spring Security сам сравнит всё (вместо кода Аутентификейшн Провайдер)
//
//            authenticationManagerBuilder.userDetailsService(personDetailsService);}


}
