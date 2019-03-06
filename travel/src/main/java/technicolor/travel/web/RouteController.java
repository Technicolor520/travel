package technicolor.travel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.ResultInfo;
import technicolor.travel.pojo.Route;
import technicolor.travel.service.IRouteService;

import java.util.List;
import java.util.Map;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/27 21:49
 * @description 处理旅游线路
 **/

@Controller
@RequestMapping("route")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @RequestMapping("routeCareChoose")
    @ResponseBody
    public ResultInfo routeCareChoose() {

        ResultInfo resultInfo = null;

        try {
            //将获取黑马精选数据封装到map集合中
            Map<String, List<Route>> map = routeService.routeCareChoose();
            resultInfo = new ResultInfo(true, map, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }
        return resultInfo;
    }

    @RequestMapping("queryPageBean")
    @ResponseBody
    public ResultInfo queryPageBean(@RequestParam(value = "cid", required = false) Integer cid,
                                    @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
                                    @RequestParam(value = "rname", required = false) String rname) {

        ResultInfo resultInfo = null;
        try {
            //调用service层获取国内游分页数据

            PageBean pageBean= routeService.queryPageBean(cid,curPage,rname);
            resultInfo=new ResultInfo(true,pageBean,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器正在维护。。");
        }
        return resultInfo;
    }

    @RequestMapping("queryRouteByRid")
    @ResponseBody
    public ResultInfo queryRouteByRid(@RequestParam("rid")Integer rid){

        ResultInfo resultInfo=null;

        try {
            Route route=routeService.queryRouteByRid(rid);
            resultInfo=new ResultInfo(true ,route,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器正在维护。。。");
        }
        return resultInfo;
    }

}
