package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.drink.Drink;

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
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email =:loginMethod or u.userName =:loginMethod")
    public User findUserToAuthenticate(@Param("loginMethod") String loginMethod);

    @Query("select d from Drink d join d.user u where u.id =:userId")
    public List<Drink> findFavoriteDrinkListOfUser(@Param("userId") Long userId);

    @Query(value = "insert into users_drinks (drink_id, user_id) values (:drinkId, :userId)", nativeQuery = true)
    public void addDrinkToFavorites(@Param("drinkId") Long drinkId, @Param("userId") Long userId);

    @Query(value = "delete from users_drinks ud where ud.drink_id =:drinkId", nativeQuery = true)
    public void removeDrinkFromFavorites(@Param("drinkId") Long drinkId);

    @Query(value = "select * from drinks where user_id = ?1", nativeQuery = true)
    public List<Drink> findAllDrinksMadeByUser(Long userId);
}
