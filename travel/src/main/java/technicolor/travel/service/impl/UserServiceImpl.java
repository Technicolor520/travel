package technicolor.travel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicolor.travel.exception.UserExistsException;
import technicolor.travel.exception.UserNameNotNullException;
import technicolor.travel.exception.UserNameOrPasswordErrorException;
import technicolor.travel.exception.UserNoActiveException;
import technicolor.travel.mapper.UserMapper;
import technicolor.travel.pojo.User;
import technicolor.travel.service.UserService;
import technicolor.travel.utils.MailUtil;
import technicolor.travel.utils.Md5Util;
import technicolor.travel.utils.UuidUtil;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/22 23:22
 * @description 处理service层
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public void register(User user)throws Exception {

        //数据验证，用户名不能为空
        //查询用户是否已存在
//       User queryUser= userMapper.queryUserByUserName(user.getUsername());

        //使用通用mapper改造
        User commonUser = new User();
        commonUser.setUsername(user.getUsername());
        User queryUser=userMapper.selectOne(commonUser);

        if(user.getUsername()==null || "".equals(user.getUsername())){
           throw new UserNameNotNullException("用户名不能为空");
       }
       //判断用户名是否被注册
       if(null !=queryUser){
           throw new UserExistsException("用户名已存在");
       }

       //封装业务字段-激活状态为未激活
       user.setStatus("N");
       //封装业务字段-激活码（唯一，uuid）
       user.setCode(UuidUtil.getUuid());
       //密码加密，使用md5加密，md5号称不可逆加密算法
       user.setPassword(Md5Util.encodeByMd5(user.getPassword()));

        //发送邮件给注册用户，让其进行账号的激活
        MailUtil.sendMail(user.getEmail(),"<h2>恭喜您！注册成功！</h2><a href='http://localhost:8080/user/active?code="+user.getCode()+"'>用户激活</a>");
        //注册用户添加用户
//        userMapper.addUser(user);

        //使用通用mapper改造
        userMapper.insert(commonUser);

    }

    @Override
    public Boolean active(String code) {


        //使用通用mapper改造
        User commonUser = new User();
        commonUser.setCode(code);
        //通过激活码查询出需要激活的账户
        User queryUser=userMapper.selectOne(commonUser);

        //设置激活状态后执行修改
        queryUser.setStatus("Y");
        int count =userMapper.updateByPrimaryKey(queryUser);



//
//        int count = userMapper.active(code);
        if (count==1){
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     * @param user
     * @return
     */

    @Override
    public User login(User user) throws Exception{
        //对密码进行加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        //查询登录账户
//        User queryUser=userMapper.login(user);
        //使用通用mapper改造
        User queryUser=userMapper.selectOne(user);

        //判断用户是否存在
        if (queryUser==null){
            throw new UserNameOrPasswordErrorException("用户名或密码错误");
        }
        //判断用户是否激活
        if(queryUser.getStatus().equals("N")){
            throw new UserNoActiveException("用户为激活");
        }
        return queryUser;
    }
}
