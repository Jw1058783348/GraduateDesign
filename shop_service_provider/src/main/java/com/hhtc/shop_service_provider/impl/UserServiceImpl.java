package com.hhtc.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhtc.dao.IUserDao;
import com.hhtc.entity.Address;
import com.hhtc.entity.ResultData;
import com.hhtc.entity.User;
import com.hhtc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/10 10:30
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public ResultData<User> login(String username, String password) {
        User user = userDao.queryByUsername(username);
        ResultData<User> resultData=new ResultData<>();
        if(user!=null){
            if(user.getPassword().equals(password)){
                resultData.setCode(100);
                resultData.setMsg("登录成功");
                resultData.setData(user);
            }else{
                resultData.setCode(102);
                resultData.setMsg("密码错误");
            }
        }else{
            resultData.setCode(101);
            resultData.setMsg("用户名不存在");
        }
        return resultData;
    }

    @Override
    public boolean queryByUsername(String username) {
        User user = userDao.queryByUsername(username);
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> queryAllUsers(Integer pageSize, Integer currentPage) {
        return userDao.queryAllUsers(currentPage,pageSize);
    }

    @Override
    public Integer queryCount() {
        return userDao.queryCount();
    }

    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userDao.delUser(id);
    }

    @Override
    public Integer batchDel(List<Integer> ids) {
        return userDao.batchDel(ids);
    }

    @Override
    public boolean adminLogin(String username, String password) {
        Integer result = userDao.adminLogin(username, password);
        System.out.println("impl======="+result);
        if(result==1 && result!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Integer updateAdmin(String password) {
        return userDao.updateAdminPwd(password);
    }

    @Override
    public List<Address> queryAllAddress(int pageSize, int currentPage) {
        return userDao.queryAllAddress(currentPage,pageSize);
    }

    @Override
    public Integer queryAddressCount() {
        return userDao.queryAddressCount();
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<Address> queryAddressByUserId(Integer id) {
        return userDao.queryAddressByUserId(id);
    }

    @Override
    public Address addAddress(Address address) {
        userDao.addAddress(address);
        return address;
    }

    @Override
    public Address queryAddressById(Integer aid) {
        return userDao.queryAddressById(aid);
    }

    @Override
    public Integer updateAddress(Address address) {
        return userDao.updateAddress(address);
    }

    @Override
    public Integer deleteAddress(Integer id) {
        return userDao.deleteAddress(id);
    }


}
