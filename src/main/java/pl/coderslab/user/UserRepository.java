package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }
    public void createNewUser(User user){
        entityManager.persist(user);
    }
    public User updateUser(User user) {
        return entityManager.merge(user);
    }
    public void deleteUser(Long id){
        entityManager.remove(findUserById(id));
    }
    public User authenticate(String loginMethod, String password) {
        User authenticatedUser = entityManager.createQuery("select u from User u where email =:loginMethod or userName =:loginMethod", User.class)
                .setParameter("loginMethod", loginMethod)
                .getSingleResult();
        String hashedPassword = authenticatedUser.getPassword();
        if (BCrypt.checkpw(password, hashedPassword)) {
            return authenticatedUser;
        }
        return null;
    }
}
