package technicolor.travel.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import technicolor.travel.pojo.ResultInfo;
import technicolor.travel.service.ICategoryService;

/**
 * @author Technicolor
 * @version v1.0
 * @date 2019/2/28 10:19
 * @description TODO
 **/

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
   private ICategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("queryCategoryList")
    @ResponseBody
    public String queryCategoryList(){
        ResultInfo resultInfo=null;
        String jsonData = null;

        try {
            jsonData=  categoryService.queryCategoryList();
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo=new ResultInfo(false,null,"服务器正忙，请稍后再试");

            try {
                jsonData=new ObjectMapper().writeValueAsString(resultInfo);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return jsonData;
    }

}
