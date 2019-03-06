package technicolor.travel.service;

import technicolor.travel.pojo.User;

public interface UserService {

    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    void register(User user) throws Exception;

    /**
     * 注册用户激活
     * @param code
     * @return
     */
    Boolean active(String code);

    User login(User user) throws Exception;

}
