package technicolor.travel.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import technicolor.travel.exception.UserExistsException;
import technicolor.travel.exception.UserNameOrPasswordErrorException;
import technicolor.travel.exception.UserNoActiveException;
import technicolor.travel.pojo.ResultInfo;
import technicolor.travel.pojo.User;
import technicolor.travel.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/22 23:05
 * @description  用户模块业务处理
 **/

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户注册
     * @param user
     * @param check
     * @param session
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public ResultInfo register(User user, @RequestParam("check")String check, HttpSession session) {
       //实例返回结果对象
        ResultInfo resultInfo=null;
        try {
            //验证验证码
            //获取用户输入的验证码
            //获取服务器端生成的验证码
            String checkCode = (String) session.getAttribute("check");
            if(!check.equalsIgnoreCase(checkCode)){
                //验证失败
                resultInfo = new ResultInfo(false, null, "验证码错误");
            }else {
                //调用业务逻辑方法实现注册功能
                userService.register(user);
                //获取注册结果，封装返回结果对象ResultInfo
                resultInfo = new ResultInfo(true, null, null);
            }
        } catch (UserExistsException e) {
            e.printStackTrace();
            //如果用户名已存在
            resultInfo = new ResultInfo(false, null, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试");
        }
        return resultInfo;
    }

    /**
     * 注册用户激活
     */
    @RequestMapping("active")
    public String active(@RequestParam("code")String code){
        try {
            //调用业务进行激活
            Boolean flag = userService.active(code);
            //int x = 1 / 0;
            if (flag) {
                //如果激活成功，跳转到登陆页面
                return "redirect:/login.html";
            }else{
                //激活失败，跳转到500页面
                return "redirect:/error/500.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            //激活失败，跳转到500页面
            return "redirect:/error/500.html";
        }
    }


    /**
     * 用户登录
     * @param user
     * @param session
     * @param check
     * @return
     */

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(User user,HttpSession
                            session,@RequestParam("check") String check){
        ResultInfo resultInfo=null;
        try {
            //判断验证码是否正确
            String checkCode = (String) session.getAttribute("check");
            if(!checkCode.equalsIgnoreCase(check)){
               resultInfo= new ResultInfo(false,null,"验证码错误");
            }else {
               User queryUser = userService.login(user);
               //将用户信息存入session中
               session.setAttribute("user", queryUser);
               resultInfo=new ResultInfo(true,null,null);
            }
        } catch (UserNameOrPasswordErrorException e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,e.getMessage());
        }catch (UserNoActiveException e){
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器繁忙，请稍后再试");
        }
        return resultInfo;
    }

    /**
     * 获取登陆信息并响应给前台
     * @param session
     * @return
     */

    @RequestMapping("queryInfoByLoginUser")
    @ResponseBody
    public ResultInfo queryInfoByLoginUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        ResultInfo resultInfo=null;
        if(null!=user){
            //若用户已登陆，响应用户名用以显示登陆用户的信息
            resultInfo=new ResultInfo(true,user.getName(),null);
        }else {
            resultInfo=new ResultInfo(false,null,null);
        }
        return resultInfo;
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        //销毁session
        session.invalidate();
        return "redirect:/login.html";
    }

}
