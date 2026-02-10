package com.github.amangusss.sustainable_voting_system.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AdminProperties.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/", "/register", "/login", "/results", "/candidates",
                                "/admin/login",
                                "/api/auth/**",
                                "/api/results/**",
                                "/api/candidates/**",
                                "/css/**", "/js/**", "/images/**",
                                "/uploads/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .defaultSuccessUrl("/admin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/")
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(AdminProperties adminProperties, PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername(adminProperties.username())
                .password(passwordEncoder.encode(adminProperties.password()))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
