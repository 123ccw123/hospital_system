package com.ccw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * name:offcnpe_parent
 * author: xiaoming
 **/
//开启web应用的安全框架
@EnableWebSecurity
//是在控制器方法上所添加的@PreAuthorize(添加能访问这个方法的权限),@Secured(能访问这个方法的角色)生效
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户名与密码的匹配
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 请求地址的处理(认证通过的处理方式;没有认证成功的处理方式;未被授权的处理方式)
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf防护 跨站请求防护
        http.csrf().disable()
                //表单登录
                .formLogin()
                //登录页面
                .loginPage("/login.html")
                //登录访问路径，与页面表单提交路径一致
                .loginProcessingUrl("/login")
                //登录成功后访问路径
                .defaultSuccessUrl("/index.html")
                .permitAll()
                //登录失败操作
                .failureHandler(authenticationFailureHandler)
                .and()
                //认证配置
                .authorizeRequests()
                .antMatchers("/login.html", "/login").permitAll()
                //配置静态页面可以访问
                .antMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()
                //任何请求
                .anyRequest()
                //都需要身份验证
                .authenticated();
        //配置无权限访问页面
        //http.exceptionHandling().accessDeniedPage("/uanuth.html");
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
        //配置退出
        http.logout()
        //退出路径
        .logoutUrl("/logout")
        //退出后跳转页面
        .logoutSuccessUrl("/login.html");
    }
}
