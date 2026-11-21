package musiclessonservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {

    @Bean 
    PasswordEncoder getBcryptEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService getUserDetails(PasswordEncoder encoder){
        UserDetails tutor =  User.withUsername("3ricCl4pt0n").password(encoder.encode("Layla")).roles("TUTOR").build();
        return new InMemoryUserDetailsManager(tutor);
    }

    @Bean 
    SecurityFilterChain securityDetails(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/tutor/**").hasRole("TUTOR").anyRequest().permitAll());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(auth -> auth.disable());
        http.cors(Customizer.withDefaults());
        return http.build();
    }
}
