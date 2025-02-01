import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final String filePath = "D:/Faculty/Second Year/1st Semester/OOP/Note Taking App/LoggedInUsers.txt";
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    private void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<User>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException f) {
            System.out.println("file not found");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    public void addUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
        currentUser = user;
    }

    public boolean checkValidityOfUser(User user) {  // check that username matches its password
        List<User> users = loadUsers();

        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }

    public boolean checkExistanceOfUser(User user) {
        List<User> users = loadUsers();
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }
}
