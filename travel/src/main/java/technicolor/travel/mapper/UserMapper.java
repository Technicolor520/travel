package technicolor.travel.mapper;

import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import technicolor.travel.pojo.User;

public interface UserMapper extends Mapper<User> {
    User queryUserByUserName(@Param("username")String username);

    void addUser(User user);

    int active(@Param("code")String code);

    User login(User user);

}
