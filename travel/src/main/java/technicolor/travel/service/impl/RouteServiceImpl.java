package technicolor.travel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicolor.travel.mapper.RouteMapper;
import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.Route;
import technicolor.travel.service.IRouteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/27 22:04
 * @description TODO
 **/

@Service
public class RouteServiceImpl implements IRouteService {

    @Autowired
    private RouteMapper routeMapper;


    @Override
    public Map<String, List<Route>> routeCareChoose() {

        //查询人气旅游
       List<Route> popularityRouteList= routeMapper.queryPopularityRouteList();
       //查询最新旅游
        List<Route> newsRouteList= routeMapper.queryNewsRouteList();
        //查询主题旅游
        List<Route> themeRouteList=routeMapper.queryThemeRouteList();

        //查询出来的三个数据存入map中
        Map<String,List<Route>> map =new HashMap<>();
        //和index.html页面中的三个精选数据的key一一对应

        map.put("popularity",popularityRouteList);
        map.put("news",newsRouteList);
        map.put("themes",themeRouteList);

        return map;
    }

    @Override
    public PageBean queryPageBean(Integer cid, Integer curPage, String rname) {

        //定义页面大小
        int pageSize=4;
        //定义从第几条记录开始查询
        int fistResult=(curPage-1)*pageSize;
        //查询记录数
        int count=routeMapper.queryRouteCount(cid,rname);
        System.out.println(count);
        //查询所有数据
        List<Route> list=routeMapper.queryRouteListPage(cid,fistResult,pageSize,rname);
        //将查询出来的数据封装到pageBean中
        PageBean pageBean = new PageBean();
        pageBean.setCurPage(curPage);
        pageBean.setPageSize(pageSize);
        pageBean.setCount(count);
        pageBean.setData(list);
        return pageBean;
    }

    @Override
    public Route queryRouteByRid(Integer rid) {
        return routeMapper.queryRouteByRid(rid);
    }

    @Override
    public Integer queryRouteFavoriteNum(Integer rid) {
        return routeMapper.queryRouteFavoriteNum(rid);
    }
}
