package technicolor.travel.service;

import org.springframework.web.bind.annotation.RequestParam;
import technicolor.travel.pojo.Favorite;
import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.User;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/3/1 21:41
 * @description TODO
 **/


public interface FavoriteService {

    /**
     * 检测该路线是否被用户收藏
     * @param rid
     * @param uid
     * @return
     */

    Boolean isFavoriteByRid(Integer rid,Integer uid);

    /**
     * 添加收藏
     * @param rid
     * @param user
     */
    void addFavorite(Integer rid, User user);

    /**
     * 分页查询个人收藏列表
     * @param curPage
     * @param uid
     * @return
     */
    PageBean queryFavoriteByPage(Integer curPage, int uid);
}
