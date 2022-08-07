package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.common.RedisCache;
import com.b1ackc4t.marsctfserver.config.Constant;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 16:22
 */
@Service
public class TokenServiceImpl {
    // 令牌自定义标识
    @Value("${marsctf.token.header}")
    private String header;

    // 令牌秘钥
    @Value("${marsctf.token.secret}")
    private String secret;

    // 令牌有效期
    @Value("${marsctf.token.expireTime}")
    private int expireTime;

    protected final long MILLIS_SECOND = 1000;

    protected final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final Long MILLIS_MINUTE_TEN = (expireTime / 2) * MILLIS_MINUTE;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.hasLength(token) && token.startsWith(Constant.TOKEN_PREFIX))
        {
            token = token.replace(Constant.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public MyUserDetails getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.hasLength(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constant.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                MyUserDetails user = redisCache.getCacheObject(userKey);
                return user;
            }
            catch (Exception e)
            {
            }
        }
        return null;
    }

    /**
     * 验证令牌有效期，相差不足一半的过期时间，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(MyUserDetails loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    public String createToken(MyUserDetails myUserDetails)
    {
        String tokenId = UUID.randomUUID().toString();
        myUserDetails.setTokenId(tokenId);
        refreshToken(myUserDetails);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.LOGIN_USER_KEY, tokenId);
        return createToken(claims);
    }

    /**
     * 刷新令牌有效期
     *
     * @param myUserDetails 登录信息
     */
    public void refreshToken(MyUserDetails myUserDetails)
    {
        myUserDetails.setExpireTime(System.currentTimeMillis() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(myUserDetails.getTokenId());
        redisCache.setCacheObject(userKey, myUserDetails, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid)
    {
        return Constant.LOGIN_USER_KEY + uuid;
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }


}
