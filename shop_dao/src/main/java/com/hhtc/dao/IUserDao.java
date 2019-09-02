package com.hhtc.dao;

import com.hhtc.entity.Address;
import com.hhtc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/10 10:31
 * @Version 1.0
 */
public interface IUserDao {
    User queryByUsername(String username);

    Integer addUser(User user);

    List<User> queryAllUsers(Integer currentPage, Integer pageSize);

    Integer queryCount();

    Integer updateUser(User user);

    Integer delUser(Integer id);

    Integer batchDel(@Param("ids")List<Integer> ids);

    Integer adminLogin(String username, String password);
    Integer updateAdminPwd(String password);

    List<Address> queryAllAddress(int currentPage, int pageSize);

    Integer queryAddressCount();

    User getUserById(Integer id);

    List<Address> queryAddressByUserId(Integer id);

    Integer addAddress(Address address);

    Address queryAddressById(Integer aid);

    Integer updateAddress(Address address);

    Integer deleteAddress(Integer id);
}
