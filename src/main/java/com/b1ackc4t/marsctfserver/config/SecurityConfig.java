package com.b1ackc4t.marsctfserver.config;

import com.b1ackc4t.marsctfserver.config.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    final MyLogoutSuccessHandler myLogoutSuccessHandler;

    final MyUserDetailsService myUserDetailsService;

    final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    final PersistentTokenRepository persistentTokenRepository;

    final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    public SecurityConfig(MyAuthenticationSuccessHandler myAuthenticationSuccessHandler, MyAuthenticationFailureHandler myAuthenticationFailureHandler, MyLogoutSuccessHandler myLogoutSuccessHandler, MyUserDetailsService myUserDetailsService, MyAuthenticationEntryPoint myAuthenticationEntryPoint, PersistentTokenRepository persistentTokenRepository, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myLogoutSuccessHandler = myLogoutSuccessHandler;
        this.myUserDetailsService = myUserDetailsService;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.persistentTokenRepository = persistentTokenRepository;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
//                .loginProcessingUrl("/api/login")
                .disable()
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailureHandler)
//                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .userDetailsService(myUserDetailsService)
                .tokenValiditySeconds(3600 * 24 * 7)
                .and()
                // 定义哪些URL需要被保护、哪些不需要被保护
                .authorizeRequests() //不需要保护的URL
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/login").permitAll()  // 开放访问的目录
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/getCaptchaImage").permitAll()
                .antMatchers("/api/user/downloadCTFFile/**").permitAll()
                .antMatchers("/api/userinfo/*").permitAll()
                .antMatchers("/api/users/**").authenticated()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/admin/**").hasAnyRole("admin")
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/api/logout").logoutSuccessHandler(myLogoutSuccessHandler) // 登出
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter MyUserProcessFilterBean() throws Exception {
//        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
//        usernamePasswordAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
//        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
//        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
//        return usernamePasswordAuthenticationFilter;
//    }


}
