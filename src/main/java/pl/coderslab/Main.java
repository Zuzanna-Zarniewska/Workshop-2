package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class Main {
    public static void main(String[] args) {
        UserDao dao = new UserDao();
//        User u1 = dao.create(new User("user1", "user1@mail.com", "password1"));
//        User u2 = dao.create(new User("user2", "user2@mail.com", "password2"));
//        User u3 = dao.create(new User("user3", "user3@somemail.com", "password3"));
//        User u4 = dao.create(new User("user4", "user4@gmail.com", "password4"));
//        User u5 = dao.create(new User("user5", "user5@gmail.com", "password5"));

        User userRead = dao.read(23);
        userRead.printInfo();
        userRead.setUserName("newUser");
        userRead.setEmail("newuser@mail.com");
        userRead.setPassword("newPassword");
        dao.update(userRead);
//        dao.delete(24);

        User[] allUsers = dao.findAll();
        System.out.println();
        for (User user : allUsers) {
            user.printInfo();
        }
    }
}