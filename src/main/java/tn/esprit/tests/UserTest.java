package tn.esprit.tests;

import tn.esprit.entites.Role;
import tn.esprit.entites.User;
import tn.esprit.services.UserServices;
import tn.esprit.tools.MyConnection;

;


public class UserTest {
    public static void main(String[] args) {
        // USER CRUD & GET BY ID :
             UserServices us = new UserServices();
       // Connexion Test :
             MyConnection mc = new MyConnection();

       // Add User :
             User user1 = new User("Aziz", "Bouzidi", "Bouzidi@Aziz.com", "123456", 147852, "ZizouPhoto", "Zizou", Role.ADMIN);
             us.addUser(user1);

        // List Users :
            System.out.println(us.getAllUsers());

        // Update User :
            int userId = 7;
            User userToUpdate = us.getUserById(userId);
            if (userToUpdate != null) {
                int userIdToUpdate = userToUpdate.getId();
                System.out.println("User ID: " + userIdToUpdate);

                userToUpdate.setEmail("Update@Test.com");
                us.updateUser(userToUpdate);

                System.out.println(userToUpdate);
            } else {
                System.out.println("Pas d'utilisateur ayant l'ID : " + userId + ".");
            }

        // Delete User :
            us.removeUser(9);

    }
}
