package spring.services;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import spring.dao.interfaces.IUserDao;
import spring.models.User;
import spring.services.interfaces.IUserService;

import java.util.List;

/**
 * Created by nshkarupa on 09.12.2015.
 */

public class UserServiceImpl implements IUserService {
    final static Logger log = Logger.getLogger(UserServiceImpl.class);


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

    public User updateUser(User user) throws Exception {
        User u = userDao.findByLogin(user.getLogin());
        if (u == null) {
            log.info("User " + user.getLogin() + " does not exist!");
            log.info("User " + user.getLogin() + " will be created!");
            userDao.save(user);
            return user;
        }
        userDao.update(user);
        return user;
    }

    public void deleteEntity(String login) throws Exception {
        userDao.delete(userDao.findByLogin(login));
    }

    public void deleteAll() throws Exception {
        userDao.deleteAll();
    }
}
