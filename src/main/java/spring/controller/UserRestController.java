package spring.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.models.User;
import spring.services.interfaces.IUserService;


import java.util.List;

/**
 * Created by nshkarupa on 10.12.2015.
 */
@Controller

public class UserRestController {
    final static Logger log = Logger.getLogger(UserRestController.class);
    @Autowired
    IUserService userService;  //Service which will do all data retrieval/manipulation work


    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    public List<User> listAllUsers() throws Exception {
        List<User> users = userService.getEntityList();
        return users;
    }


    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("login") String login) throws Exception {
        log.info("Fetching User with id " + login);
        User user = userService.getEntityByLogin(login);
        if (user == null) {
            log.error("User with id " + login + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }



    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) throws Exception {
        System.out.println("Creating User " + user.getLogin());

        if (userService.getEntityByLogin(user.getLogin())!=null) {
            log.error("A User with name " + user.getLogin() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveEntity(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{login}").buildAndExpand(user.getLogin()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{login}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("login") String login, @RequestBody User user) throws Exception {
        log.info("Updating User " + login);

        User currentUser = userService.getEntityByLogin(login);

        if (currentUser==null) {
            log.error("User with login " + login + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setLogin(user.getLogin());
        currentUser.setPassword(user.getPassword());

        //userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("login") String login) throws Exception {
        log.info("Fetching & Deleting User with login " + login);

        User user = userService.getEntityByLogin(login);
        if (user == null) {
            System.out.println("Unable to delete. User with login " + login + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteEntity(login);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    /*@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/


}



