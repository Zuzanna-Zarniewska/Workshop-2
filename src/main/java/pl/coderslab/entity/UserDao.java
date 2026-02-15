package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.InputMismatchException;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    private static final Field[] userAttributeNames = User.class.getDeclaredFields();

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt =
                    conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.executeUpdate();

            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Inserted ID: " + id);
                user.setId(id);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt =
                    conn.prepareStatement(READ_USER_QUERY);
            preStmt.setString(1, Integer.toString(userId));
            preStmt.executeQuery();

            ResultSet rs = preStmt.getResultSet();
            String[] userParameters = new String[userAttributeNames.length];
            while (rs.next()) {
                for (int i = 0; i < userAttributeNames.length; i++) {
                    userParameters[i] = rs.getString(userAttributeNames[i].getName());
                }
            }
            return new User(userParameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.setString(4, Integer.toString(user.getId()));
            if (user.getId() == 0) throw new InputMismatchException("This user has not been added to the database");

            preStmt.executeUpdate();
            System.out.println("Updated user with id: " + user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);
            preStmt.setString(1, Integer.toString(userId));
            preStmt.executeUpdate();
            System.out.println("Deleted user with id: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            User[] allUsers = new User[0];
            PreparedStatement preStmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = preStmt.executeQuery();

            String[] userParameters = new String[userAttributeNames.length];
            while (rs.next()) {
                for (int i=0; i < userAttributeNames.length; i++){
                    userParameters[i] = rs.getString(userAttributeNames[i].getName());
                }
                allUsers = addToArray(allUsers, new User(userParameters));
            }
            return allUsers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User[] addToArray(User[] users, User user) {
        users = Arrays.copyOf(users, users.length + 1);
        users[users.length - 1] = user;
        return users;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
