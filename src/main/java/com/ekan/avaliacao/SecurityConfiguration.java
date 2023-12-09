package com.ekan.avaliacao;

//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
public class SecurityConfiguration { 
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ekan")
                .password(passwordEncoder().encode("backendjava"))
                .authorities("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */
}