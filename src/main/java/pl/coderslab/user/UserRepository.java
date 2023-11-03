package pl.coderslab.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.drink.Drink;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id =:id")
     User findUserById(@Param("id") Long id);

    @Query("select u from User u where u.userName =:userName")
    User findUserByUsername(@Param("userName") String userName);

    @Query("select u from User u where u.email =:email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email = :loggingMethod OR u.userName = :loggingMethod")
     User findUserToAuthenticate(@Param("loggingMethod") String loggingMethod);

    @Query("select d from Drink d join d.user u where u.id =:userId")
     List<Drink> findUserMadeDrinkList(@Param("userId") Long userId);

    @Query("select d from Drink d join d.userFavList uf where uf.id =:userId")
    List<Drink> getFavoriteDrinksOfUser(@Param("userId") Long userId);

}
