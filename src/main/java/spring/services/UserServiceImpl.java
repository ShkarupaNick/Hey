package spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import spring.dao.interfaces.IUserDao;
import spring.models.User;
import spring.services.interfaces.IUserService;

import java.util.List;

/**
 * Created by nshkarupa on 09.12.2015.
 */

public class UserServiceImpl implements IUserService {


    @Autowired
    IUserDao userDao;


    public User saveEntity(User user) throws Exception {
        return userDao.save(user);
    }

    public User getEntityByLogin(String login) throws Exception {
        return userDao.findByLogin(login);
    }

    public List<User> getEntityList() throws Exception {
        return userDao.findAll();
    }

    public void deleteEntity(String login) throws Exception {
        userDao.delete(userDao.findByLogin(login));
    }
}
