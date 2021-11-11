package com.example.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.security.entity.Users;
import com.example.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: YDUI01
 * @Date: 2021/10/28 11:04
 * @Description: MyUserDetailsService
 * @Version 1.0.0
 */

/**
 * 这里覆盖下原生的校验service，类加载时使用我们自定义的；
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("name",s);
        Users users = usersMapper.selectOne(usersQueryWrapper);
        if (Objects.isNull(users)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //角色列表
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        //返回用户信息
        return new User(users.getName(),new BCryptPasswordEncoder().encode(users.getPassword()),authorityList);
    }
}
