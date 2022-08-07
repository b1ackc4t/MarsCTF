package com.b1ackc4t.marsctfserver.util;

import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 19:31
 */
public class SecurityUtils
{
    /**
     * 用户ID
     **/
    public static Integer getUserId() {
        return getLoginUser().getUid();

    }


    /**
     * 获取用户账户
     **/
    public static String getUsername() {
       return getLoginUser().getUsername();
    }

    /**
     * 获取用户
     **/
    public static MyUserDetails getLoginUser() {
            return (MyUserDetails) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}