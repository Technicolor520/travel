package technicolor.travel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import technicolor.travel.pojo.Favorite;
import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.ResultInfo;
import technicolor.travel.pojo.User;
import technicolor.travel.service.FavoriteService;
import technicolor.travel.service.IRouteService;

import javax.servlet.http.HttpSession;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/3/1 21:07
 * @description TODO
 **/

@Controller
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private IRouteService routeService;

    /**
     * 判断用户是否收藏
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("isFavoriteByRid")
    @ResponseBody
    public ResultInfo isFavoriteByRid(@RequestParam("rid") Integer rid, HttpSession session){

        ResultInfo resultInfo=null;

        try {
            //判断用户是否登录
            User user= (User) session.getAttribute("user");
            if(null==user){
                //第一个参数,代表正常处理结果
                //第二个参数,代表没有收藏
                resultInfo =new ResultInfo(true,false,null);
            }else {
                //查询该用户是否已经收藏
                boolean flag= favoriteService.isFavoriteByRid(rid,user.getUid());
                //如果flag为true表示已经收藏，false表示没有收藏
                resultInfo=new ResultInfo(true, flag, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器正在维护。。");
        }
        return  resultInfo;
    }

    /**
     * 添加收藏 ：
     *          1、添加收藏
     *          2、查询收藏室数量返回给前台显示收藏次数
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("addFavorite")
    @ResponseBody
    public ResultInfo addFavorite(@RequestParam("rid")Integer rid,HttpSession session){

        ResultInfo resultInfo=null;

        try {
            //判断用户是否登录
            User user = (User) session.getAttribute("user");
            if(null==user){
                //没有登录返回0
                resultInfo=new ResultInfo(true,0,null);
            }else {

                //登录成功根据rid和uid实现添加收藏业务
                favoriteService.addFavorite(rid,user);
                //根据rid获取线路最新收藏数量
                Integer count=routeService.queryRouteFavoriteNum(rid);
                //封装收藏数量返回给前台用于显示收藏次数
                resultInfo=new ResultInfo(true,count,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器正在维护。。。");
        }
        return resultInfo;
    }

    /**
     * 查询个人收藏列表
     * @param curPage 当前页数据，默认为1
     * @param session 获取session中的用户登录信息
     * @return
     */

    @RequestMapping("queryFavoriteByPage")
    @ResponseBody
    public ResultInfo queryFavoriteByPage(@RequestParam(value = "curPage", defaultValue = "1")Integer curPage ,HttpSession session){

        ResultInfo resultInfo=null;

        try {
            // 获取当前登录的用户
            User user = (User) session.getAttribute("user");
            // 根据curPage和user调用业务获取收藏数据的PageBean
            PageBean<Favorite> pageBean=favoriteService.queryFavoriteByPage(curPage,user.getUid());
            // 封装结果
            resultInfo=new ResultInfo(true,pageBean,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo =new ResultInfo(false,null,"服务器正在维护。。。");
        }

        return resultInfo;
    }



}
