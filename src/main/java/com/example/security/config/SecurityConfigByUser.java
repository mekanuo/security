package com.example.security.config;

import com.example.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Auther: YDUI01
 * @Date: 2021/10/28 10:58
 * @Description: SecurityConfigByUser
 * @Version 1.0.0
 */
@Configuration
public class SecurityConfigByUser extends WebSecurityConfigurerAdapter {

    /**
     * 这里原本需要注入UserDetailsService原生的类，
     * 但是我们继承并重写了其中的方法，
     * 这里注入我们自定义的类进行测试；
     */
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //配置无权限跳转页面
        httpSecurity.exceptionHandling().accessDeniedPage("/unAuth.html");
        httpSecurity.formLogin()    //自定义自己编写的登录页面
                .loginPage("/login.html")    //登录页面设置
                .loginProcessingUrl("/user/login")   //security默认的登录访问路径
                .defaultSuccessUrl("/index").permitAll()    //登录成功后跳转路径
                .and().authorizeRequests()
//                .antMatchers("/hello").hasAuthority("12312312312")
                .antMatchers("/user/login").permitAll() //配置白名单
                .anyRequest().authenticated()
                .and().csrf().disable();    //关闭CSRF防护
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
