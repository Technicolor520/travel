package technicolor.travel.mapper;

import com.github.abel533.entity.Example;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import technicolor.travel.pojo.User;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext*.xml"})
public class NewUserMapperTest {

    @Autowired
    private NewUserMapper userMapper;

    /**
     * 由于该方法时查询一条记录的方法，返回值只有一个user。
     * 因此在设置查询条件的时候，只能设置一个唯一的不会重复的查询条件。
     */

    @Test
    public void test1(){
        User user = new User();
        user.setUsername("Technicolor");//设置一个唯一的属性用来查询
        User queryUser = userMapper.selectOne(user);
        System.out.println(queryUser);
    }

    /**
     * 如果条件为null或者是一个空的new User():那么会查询所有数据
     * 如果user中设置了数据：就按user中封装的数据进行条件查询
     * 条件只能按照=来查询，不能按照>或者<等方式来查询。
     */

    @Test
    public void test2(){
        //查询所有条件
        //按条件查询
        List<User> list = userMapper.select(null);
        for (User user : list) {
            System.out.println(user);
        }
    }

    /**
     * 统计查询：
     * 1、赋值为null或者new User()：统计所有记录数
     */

    @Test
    public void test3(){
        User user = new User();
        user.setSex("男");
       int count = userMapper.selectCount(user);
        System.out.println(count);
    }


    /**
     * 通过主键进行查询：
     * 1、参数类型一定要主键一致
     * 2、必须在对象中通过@Id指定主键
     */

    @Test
    public void test6(){
        System.out.println(userMapper.selectByPrimaryKey(4));
    }



    /**
     * 新增方法：按全属性插入数据，如果不设置属性，自动设置为null
     */
    @Test
    public void test4(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setName("用户1");
        user.setBirthday("2222-12-22");
        user.setTelephone("12222222222");
        user.setCode("asdf");
        user.setSex("男");
        user.setEmail("admin@technicolor.cn");
        user.setStatus("Y");
        userMapper.insert(user);
    }

    /**
     * 按属性插入数据：如果不设置属性，自动设置为null
     */
    @Test
    public void test5(){
        User user = new User();
        user.setUsername("admin4");
        user.setPassword("1234");
        user.setName("用户4");
        user.setCode("assq");
        user.setSex("男");
        user.setStatus("Y");
        userMapper.insertSelective(user);
    }

    /**
     * 条件删除：
     * 1、如果条件为null或者new User(),删除全部数据
     * 注意：如果有外键约束，并且主键被某个外键所引用，那就无法删除
     */

    @Test
    public void test7(){
        User user = new User();
        user.setUsername("admin");
        userMapper.delete(user);
    }

    /**
     * 按主键删除
     */
    @Test
    public void test8(){
        userMapper.deleteByPrimaryKey(8);
    }

    /**
     * 通过主键更新数据：
     * 将设置的属性进行更新，如果不设置属性，自动更新为null。
     * 必须设置主键
     */

    @Test
    public void test9(){
        User user = new User();
        user.setUsername("admin100");
        user.setPassword("1234");
        user.setName("用户100");
        user.setBirthday("2012-10-22");
        user.setTelephone("12333101099");
        user.setCode("safe");
        user.setSex("男");
        user.setEmail("admin100@itcast.cn");
        user.setStatus("Y");
        user.setUid(9);
        userMapper.updateByPrimaryKey(user);
    }


    /**
     * 通过主键按属性进行选择更新：
     * 必须设置主键
     * 将设置的属性进行更新，没有设置的属性还是原来的值
     */

    @Test
    public void test10(){
        User user = new User();
        user.setUsername("admin200");
        user.setPassword("1234");
        user.setName("用户200");
        user.setBirthday("2012-10-20");
        user.setTelephone("12222222222");
        user.setCode("werw");
        user.setSex("男");
        user.setStatus("Y");
        user.setUid(9);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 通过条件统计记录数
     * 1、需要初始化Example对象
     * 2、通过example获取criteria来设置条件
     */

    @Test
    public void test11(){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andBetween("uid",1,10);
        int count = userMapper.selectCountByExample(example);
        System.out.println(count);
    }

    /**
     * 通过条件进行查询：
     * 查询uid在1-30之间的并且用户名中有zhang的用户，或性别是男的用户。
     * 显示的时候通过birthday倒序排序，如果birthday一样那么根据uid正序执行。
     * <p>
     * 1、初始化Example对象
     * 2、通过Example对象获取Criteria来设置查询条件
     * 3、可以通过example设置并集查询-or
     * 4、可以通过example设置排序查询
     */

    @Test
    public void test12(){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //添加年龄在1-30之间的条件
        criteria.andBetween("uid",1,30);

        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("sex","男");
        //添加或性别是男的条件
        example.or(criteria1);
        //排序
        example.setOrderByClause("birthday desc,uid asc");

        List<User> list = userMapper.selectByExample(example);
        for (User user : list) {
            System.out.println(user);
        }
    }

    /**
     * 条件删除
     */
    @Test
    public void test13(){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("username","admin2");
        userMapper.deleteByExample(example);
    }

    /**
     * 按条件选择更新：没设置的属性就不做修改
     * 将姓名为admin3的用户的性别改为女
     */

    @Test
    public void test14(){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username","admin3");

        User user = new User();
        user.setSex("女");
        userMapper.updateByExampleSelective(user,example);
    }

    /**
     * 将uid为10的用户，修改除电话和邮件以外的所有属性
     * 条件更新：没有设置属性的都更新为null.
     */
    @Test
    public void test15(){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //添加条件
        criteria.andEqualTo("uid",10);

        //修改处电话和邮件以为的所有的属性
        User user = new User();
        user.setUsername("admin2");
        user.setPassword("1234");
        user.setName("用户2");
        user.setBirthday("2012-10-22");
        user.setCode("tyhe");
        user.setSex("男");
        user.setStatus("Y");
        userMapper.updateByExample(user,example);
    }

}