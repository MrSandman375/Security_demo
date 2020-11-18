package com.renjie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * @Author Fan
 * @Date 2020/11/16
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class DBSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityDataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //自定义用户登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //没有访问权限跳转的页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin() //自定义自己编写的登录页面
            .loginPage("/login.html") //登录页面设置
            .loginProcessingUrl("/user/login") //登录访问路径
            .defaultSuccessUrl("/success.html").permitAll() //登录成功后跳转的路径
            .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() //那些路径可以直接访问不需要认证
                //当前登录用户，只有具有相应的权限才可以访问这个路径
                //.antMatchers("/test/index").hasAuthority("admin")
                //当前登录用户，只有具有其中任意的的权限都可以访问这个路径
                //.antMatchers("/test/index").hasAnyAuthority("admin","user")
                //使用这个hasRole需要在用户service那边查到的权限前拼接一个ROLE_，除此之外和hasAuthority一致，hasAnyRole同上
                .antMatchers("/test/index").hasRole("admin")
                .anyRequest().authenticated()
            .and().rememberMe().tokenRepository(securityDataSource.persistentTokenRepository())
            .tokenValiditySeconds(60) //设置有效时长 单位秒
            .userDetailsService(userDetailsService);
            //.and().csrf().disable(); //关闭CSRF防护

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

    }
}
