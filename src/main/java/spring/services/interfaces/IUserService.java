package spring.services.interfaces;


import spring.models.User;
import java.util.List;

/**
 * Created by nshkarupa on 11.12.2015.
 */
public interface IUserService {
    User saveEntity(User User) throws Exception;
    User getEntityByLogin(String login) throws Exception;
    List<User> getEntityList() throws Exception;
    void deleteEntity(String login) throws Exception;
}
