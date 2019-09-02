package com.hhtc.service;

import com.hhtc.entity.Address;
import com.hhtc.entity.ResultData;
import com.hhtc.entity.User;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/10 10:28
 * @Version 1.0
 */
public interface IUserService {
    ResultData<User> login(String username, String password);

    boolean queryByUsername(String username);

    Integer addUser(User user);

    List<User> queryAllUsers(Integer pageSize,Integer currentPage );

    Integer queryCount();

    Integer updateUser(User user);

    Integer deleteUser(Integer id);

    Integer batchDel(List<Integer> ids);

    boolean adminLogin(String username, String password);

    Integer updateAdmin(String password);

    List<Address> queryAllAddress(int pageSize, int currentPage);

    Integer queryAddressCount();

    User getUserById(Integer id);

    List<Address> queryAddressByUserId(Integer id);

    Address addAddress(Address address);

    Address queryAddressById(Integer aid);

    Integer updateAddress(Address address);

    Integer deleteAddress(Integer id);
}
