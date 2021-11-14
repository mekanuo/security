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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    /**
     * 注入数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 配置对象
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //设置自动创建自动登录表，不设置的话需要手动创建表(创建后再次创建代码会报错--沙雕)；
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

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
//                .defaultSuccessUrl("/index").permitAll()    //登录成功后跳转路径
                .defaultSuccessUrl("/success.html").permitAll()    //登录成功后跳转页面
                .and().authorizeRequests()
//                .antMatchers("/hello").hasAuthority("12312312312")
                .antMatchers("/user/login").permitAll() //配置白名单
                .anyRequest().authenticated()
                //配置开启自动登录-token有效期60S-自动登录配置的server是userDetailsService
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60).userDetailsService(userDetailsService())
                .and().csrf().disable();    //关闭CSRF防护

        //配置退出成功后页面
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
