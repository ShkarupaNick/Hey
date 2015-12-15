package spring.dao.interfaces;


import spring.models.User;

import java.util.List;

/**
 * Created by nshkarupa on 10.12.2015.
 */
public interface IUserDao {

    public List findAll() ;
    public User findByLogin(String login) ;
    public User save(User user) ;
    public void delete(User user) ;
}
