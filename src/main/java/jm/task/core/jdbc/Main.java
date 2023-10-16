package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        User user = new User("Ivan", "Ivanov", (byte) 14);
        User user1 = new User("Ivan1", "Ivanov1", (byte) 20);
        User user2 = new User("Ivan2", "Ivanov2", (byte) 30);
        User user3 = new User("Ivan3", "Ivanov3", (byte) 99);
        List<User> userList = Arrays.asList(user, user1, user2, user3);
        for (User element : userList) {
            userService.saveUser(element.getName(), element.getLastName(), element.getAge());
        }
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
