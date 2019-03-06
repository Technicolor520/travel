package technicolor.travel.mapper;

import org.apache.ibatis.annotations.Param;
import technicolor.travel.pojo.Favorite;

import java.util.List;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/3/1 21:53
 * @description TODO
 **/


public interface FavoriteMapper {

    /**
     * 根据rid和uid查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    Favorite isFavoriteByRid(@Param("rid") Integer rid,@Param("uid") Integer uid);

    /**
     * 根据rid和uid添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(@Param("rid") Integer rid,@Param("uid") int uid);

    /**
     * 根据rid更新收藏数量
     * @param rid
     */
    void updateRouteFavoriteNum(@Param("rid") Integer rid);

    /**
     * 统计个人收藏总记录数
     * @param uid
     * @return
     */
    int queryFavoriteCount(@Param("uid") int uid);

    /**
     * 分页查询个人收藏的路线
     * @param firstResult
     * @param curPage
     * @param uid
     * @return
     */
    List<Favorite> queryFavoriteListByPage(@Param("firstResult") int firstResult, @Param("curPage") Integer curPage,@Param("uid") int uid,@Param("pageSize") Integer pageSize);
}
