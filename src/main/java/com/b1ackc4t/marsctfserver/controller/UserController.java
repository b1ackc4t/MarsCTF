package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.Constant;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.dao.UserMapper;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserChaMapService;
import com.b1ackc4t.marsctfserver.service.UserService;
import com.b1ackc4t.marsctfserver.util.generator.ShortUUID;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    final UserService userService;
    final UserChaMapService userChaMapService;
    final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserChaMapService userChaMapService, UserMapper userMapper) {

        this.userService = userService;
        this.userChaMapService = userChaMapService;
        this.userMapper = userMapper;
    }

    @GetMapping("/userInfo/{id:\\d+}")  // 废弃
    public ReturnRes queryOneById(@PathVariable int id) {
        User user = userMapper.selectUserByIdForUser(id);
        if (user != null) {
            briefUserView(user);
            user.setRankTitle();
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "id not found");
        }
    }

    @GetMapping("/user")
    public ReturnRes query(@RequestParam(required = false) String name) {
        if (name != null) {
            return queryOneByName(name);
        }
        List<User> userList = userMapper.selectAllUser();
        if (userList != null) {
            return new ReturnRes(true, userList);
        } else {
            return new ReturnRes(false);
        }
    }

    @GetMapping("/user/{id:\\d+}")
    public ReturnRes queryOneByIdForUser(@PathVariable int id) {
        User user = userMapper.selectUserByIdForUser(id);
        if (user != null) {
            user.setRankTitle();
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "id not found");
        }
    }

    @GetMapping("/user/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPage(@PathVariable int pageSize,
                                 @PathVariable int pageNum) {
        PageInfo<User> pageInfo = userService.getAllUserByPage(pageNum, pageSize);
        if (pageInfo != null) {
            return new ReturnRes(true, pageInfo);
        }
        return new ReturnRes(false);
    }

    public ReturnRes queryOneByName(@RequestParam String name) {
        User user = userService.getByUserName(name);
        if (user != null) {
//            briefUserView(user);
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "name not found");
        }
    }

    /**
     *
     * @param action 对用户的操作名称
     * @param userJson
     * @param authentication 用于鉴权
     * @param response
     * @return
     */
    @PostMapping("/user")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String userJson,
                             Authentication authentication,
                             HttpServletResponse response) {
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
        User user = JSONObject.parseObject(userJson, User.class);

        if (action.equals("save")) {
            if (!securityExpressionRoot.hasRole("admin")) {
                response.setStatus(403);
                return new ReturnRes(false, "Permission is not allowed.");
            }
            return save(user);
        } if (action.equals("remove")) {
            if (!securityExpressionRoot.hasRole("admin")) {
                response.setStatus(403);
                return new ReturnRes(false, "Permission is not allowed.");
            }
            return remove(user);
        } if (action.equals("update")) {
            if (!securityExpressionRoot.hasRole("admin")) {
                if (!checkPermission(user, authentication.getName())) {
                    response.setStatus(403);
                    return new ReturnRes(false, "Permission is not allowed.");
                }
                filterDangerOperation(user); // 普通用户操作限制
            }
            return update(user);
        } else {
            return new ReturnRes(false, "action value error");
        }
    }

    @RequestMapping("/user/getMe")
    public ReturnRes getMe(Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        User user = userMapper.selectUserByIdForUser(uid);
        if (user != null) {
            user.setRankTitle();
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "error, please repeat");
        }
    }

    /**
     * 所有人都能用的注册接口
     * @param username
     * @param password
     * @param passwordR
     * @return
     */
    @PostMapping("/register")
    public ReturnRes register(@RequestParam String username,
                              @RequestParam(defaultValue = "") String password,
                              @RequestParam(defaultValue = "") String passwordR) {
        if (!password.equals(passwordR)) {
            return new ReturnRes(false, "The two passwords are different.");
        }
        User user = new User(username, password);
        user.setRole(Constant.ROLE_USER);
        return save(user);

    }

    public ReturnRes save(User user) {

        if (userService.usernameIsRepeat(user.getUname())) {
            return new ReturnRes(false, "Duplicate name");
        }

        user.setUpassword(new BCryptPasswordEncoder().encode(user.getUpassword()));
        user.setUid(ShortUUID.generateUid());
        user.setRegTime(new Date());
        setAllScoreZero(user);

        try {
            if (userService.save(user)) {
                briefUserView(user);
                return new ReturnRes(true, user);
            } else {
                return new ReturnRes(false);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return new ReturnRes(false, "repeat oper");
        }
    }

    public ReturnRes remove(User user) {
        userChaMapService.removeByUid(user.getUid());   // 先删除用户的做题信息


        if (userService.removeById(user)) {
            briefUserView(user);
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "id not found");
        }
    }

    public ReturnRes update(User user) {
        if (userService.updateById(user)) {
            briefUserView(user);
            return new ReturnRes(true, user);
        } else {
            return new ReturnRes(false, "id not found");
        }
    }

    /**
     * 过滤返回给前端的数据
     * @param user
     */
    public void briefUserView(User user) {
        user.setRegTime(null);
        user.setWeb(null);
        user.setPwn(null);
        user.setRe(null);
        user.setCrypto(null);
        user.setMisc(null);
        user.setOther(null);
        user.setRank(null);
        user.setLevel(null);
        user.setTitle(null);
    }

    public void filterDangerOperation(User user) {
        user.setRole(null);
        user.setRegTime(null);
    }

    /**
     * 将新用户的分数置为0
     * @param user
     */
    public void setAllScoreZero(User user) {
        user.setWeb(0);
        user.setPwn(0);
        user.setRe(0);
        user.setCrypto(0);
        user.setMisc(0);
        user.setOther(0);
        user.setScore(0);
    }

    /**
     * 检查用户与操作对象是否匹配
     * @param operedUser
     * @param username
     * @return
     */
    public boolean checkPermission(User operedUser, String username) {
        int uid = userService.getIdByUserName(username);
        return uid == operedUser.getUid();
    }
}
