package tn.esprit.interfaces;

import java.util.List;

public interface IUserService<T> {
    void addUser(T t);
    void removeUser(int id);
    void updateUser(T t);
    T getUserById(int id);
    List<T> getAllUsers();
}
