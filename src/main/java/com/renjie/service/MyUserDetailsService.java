package com.renjie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjie.entity.Users;
import com.renjie.mapper.UsersMapper;
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

/**
 * @Author Fan
 * @Date 2020/11/16
 * @Description:
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中查到username对应的password
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        Users users = usersMapper.selectOne(wrapper);
        //判断是否为空
        if(users == null){
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            //权限设置
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+users.getRole());
            //从数据库中返回users对象，得到用户名密码，返回
            return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),authorities);
        }


    }
}
