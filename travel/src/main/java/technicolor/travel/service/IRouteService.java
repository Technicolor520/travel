package technicolor.travel.service;

import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.Route;

import java.util.List;
import java.util.Map;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/27 22:01
 * @description 处理旅游线路service
 **/


public interface IRouteService {

    Map<String,List<Route>> routeCareChoose();

    PageBean queryPageBean(Integer cid, Integer curPage, String rname);

    Route queryRouteByRid(Integer rid);

    Integer queryRouteFavoriteNum(Integer rid);
}
