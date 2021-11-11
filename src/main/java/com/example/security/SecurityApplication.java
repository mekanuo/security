package com.example.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.example.security.mapper")

/**
 * security auth2.0 角色权限注解使用
 */
//* securedEnabled = true用户具有某个角色时，才可以访问这个方法，可以用在类和方法上；与@Secured标签配合使用；
//* prePostEnabled = true 配置权限过滤，可以用在类和方法上；
// 与@PreAuthorize（方法执行前验证）标签或@PostAuthorize（方法执行后验证）
// 与@PostFilter（对返回值进行过滤）或 @PreFilter（对入参数进行过滤）配合使用；
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
}
