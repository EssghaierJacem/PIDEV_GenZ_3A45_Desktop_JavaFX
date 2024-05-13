package tn.esprit.services;



import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entites.Role;
import tn.esprit.entites.User;
import tn.esprit.interfaces.IUserService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServices implements IUserService<User> {

    @Override
    public void addUser(User user) {
        String query = "INSERT INTO user(nom, prenom, email, password, cin, photo, username, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setInt(5, user.getCin());
            pst.setString(6, user.getPhoto());
            pst.setString(7, user.getUsername());
            pst.setString(8, user.getRole().toString());
            pst.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
        }
    }

    @Override
    public void removeUser(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE user SET nom = ?, prenom = ?, email = ?, password = ?, cin = ?, photo = ?, username = ?, role = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setInt(5, user.getCin());
            pst.setString(6, user.getPhoto());
            pst.setString(7, user.getUsername());
            pst.setString(8, user.getRole().toString());
            pst.setInt(9, user.getId());
            pst.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCin(rs.getInt("cin"));
                    user.setPhoto(rs.getString("photo"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(Role.valueOf(rs.getString("role")));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCin(rs.getInt("cin"));
                    user.setPhoto(rs.getString("photo"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(Role.valueOf(rs.getString("role")));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
        }
        return userList;
    }
    public User authenticate(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setNom(rs.getString("nom"));
                        user.setPrenom(rs.getString("prenom"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setCin(rs.getInt("cin"));
                        user.setPhoto(rs.getString("photo"));
                        user.setUsername(rs.getString("username"));
                        user.setRole(Role.valueOf(rs.getString("role")));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during user authentication: " + e.getMessage());
        }
        return null;
    }

    /*
    // In userService.java

    public String generateVerificationCode() {
        // Generate a random 6-digit verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
*/
    public void ResetPassword(String email, String password) {
        try {

            String req = "UPDATE user SET password = ? WHERE email = ?";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(req);

            ps.setString(1, password);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("Password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

    public int ChercherMail(String email) {

        try {
            String req = "SELECT * from user WHERE Email ='" + email + "'  ";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                return 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

/*
    public void modifierByEmail(User user,String email) throws SQLException {

        String sql = "UPDATE user SET FirstName = ?,LastName = ?,Email = ?,Address = ?,Role = ?,Number = ?,Rating = ?,Password = ? WHERE email = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getAddress());
        statement.setString(5, user.getRole());
        statement.setInt(6, user.getNumber());
        statement.setDouble(7, user.getRating());
        statement.setString(8, user.getPassword());
        statement.setString(9, email);
        statement.executeUpdate();


    }









    public boolean checkEmailExists(String email) {

        boolean result = false;

        try {
            String req = "SELECT * FROM user WHERE Email = ?";
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }




*/

  public List<User> searchUsersByEmailStartingWithLetter(String searchAttribute,String startingLetter) throws SQLException{
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE " + searchAttribute + " LIKE ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(sql)) {
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCin(rs.getInt("cin"));
                user.setPhoto(rs.getString("photo"));
                user.setUsername(rs.getString("username"));
                user.setRole(Role.valueOf(rs.getString("role")));





                users.add(user);

            }

        } catch (SQLException ex) {
            System.out.println("Error while searching for users by email: " + ex.getMessage());
        }

        return users;
    }




}
