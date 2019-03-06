package technicolor.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import technicolor.travel.mapper.CategoryMapper;
import technicolor.travel.pojo.Category;
import technicolor.travel.service.ICategoryService;
import technicolor.travel.utils.JedisUtil;

import java.util.List;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/28 10:29
 * @description TODO
 **/

@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String queryCategoryList()throws JsonProcessingException {

        String jsonData=null;

        //从缓存中命中
        Jedis jedis = JedisUtil.getJedis();
       jsonData=(String)redisTemplate.opsForValue().get("categotyList");
       //若redis没有缓存，存入进去
        if(StringUtils.isBlank(jsonData)){

            //从数据库中查找数据
           List<Category> list= categoryMapper.queryCategoryList();
           //将查询出来的数据转化成字符串
            jsonData= new ObjectMapper().writeValueAsString(list);
            //将数据存入Redis中
            redisTemplate.opsForValue().set("categoryList" ,jsonData);
        }
        return jsonData;
    }
}
