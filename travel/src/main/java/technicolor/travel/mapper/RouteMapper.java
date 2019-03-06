package technicolor.travel.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import technicolor.travel.pojo.Route;

import java.util.List;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/27 22:17
 * @description TODO
 **/


public interface RouteMapper {

    /**
     * 查询人气最高的旅游
     * @return
     */
    List<Route> queryPopularityRouteList();

    /**
     * 查询最新的旅游
     * @return
     */
    List<Route> queryNewsRouteList();

    /**
     * 查询主题旅游
     * @return
     */
    List<Route> queryThemeRouteList();

    /**
     * 根据cid查询线路数
     * @param cid
     * @param rname
     * @return
     */
    int queryRouteCount(@Param("cid") Integer cid, @Param("rname")String rname);

    /**
     * 分页
     * @param cid
     * @param fistResult
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> queryRouteListPage(@Param("cid")Integer cid, @Param("firstResult")int fistResult,  @Param("pageSize")int pageSize, @Param("rname")String rname);

    /**
     * 通过rid查询route
     * @param rid
     * @return
     */
    Route queryRouteByRid(@RequestParam("rid") Integer rid);

    /**
     * 查询线路收藏数量
     * @param rid
     * @return
     */
    Integer queryRouteFavoriteNum(@RequestParam("rid") Integer rid);
}
