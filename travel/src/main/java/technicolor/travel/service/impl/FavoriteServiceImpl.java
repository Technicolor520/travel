package technicolor.travel.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicolor.travel.mapper.FavoriteMapper;
import technicolor.travel.mapper.RouteMapper;
import technicolor.travel.pojo.Favorite;
import technicolor.travel.pojo.PageBean;
import technicolor.travel.pojo.User;
import technicolor.travel.service.FavoriteService;

import java.util.List;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/3/1 21:43
 * @description TODO
 **/

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    //查询是否被收藏
    @Override
    public Boolean isFavoriteByRid(Integer rid,Integer uid) {
        Favorite favorite = favoriteMapper.isFavoriteByRid(rid,uid);
        System.out.println(favorite);
        //如果不为null就返回true,否则返回false
        return favorite!=null;
    }

    //添加收藏
    @Override
    public void addFavorite(Integer rid, User user) {

        favoriteMapper.addFavorite(rid,user.getUid());
        favoriteMapper.updateRouteFavoriteNum(rid);
    }

    //分页查询个人收藏
    @Override
    public PageBean queryFavoriteByPage(Integer curPage, int uid) {

        //查询总记录数
            int count=favoriteMapper.queryFavoriteCount(uid);
        //设置页面大小
            int pageSize=4;
        //定义limit x , y 中的x ,即分页查询的起始记录数
            int firstResult=(curPage-1)*pageSize;
        //查询个人收藏路线
        List<Favorite> list=favoriteMapper.queryFavoriteListByPage(firstResult,curPage,uid,pageSize);

        //创建分页信息对象，通过该对象可以获取总记录数和分页信息等数据
        PageInfo<Favorite> pageInfo = new PageInfo<>(list);
        //实例PageBean
        PageBean<Favorite> pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);
        pageBean.setCount((int) pageInfo.getTotal());
        pageBean.setPageSize(pageSize);
        pageBean.setData(pageInfo.getList());

        return pageBean;
    }
}
