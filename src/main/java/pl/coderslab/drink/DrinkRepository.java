package pl.coderslab.drink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    @Override
    Optional<Drink> findById(Long id);

    @Query("select d from Drink d where d.name =:name")
    Optional<Drink> getDrinkByName(@Param("name") String name);

    @Query("select d from Drink d where d.user.id =:userId")
    List<Drink> findDrinkOfUser(@Param("userId") Long userId);

    @Query("select d from Drink d where d.user.id =:userId")
    List<Drink> getAllUserMadeDrinks(@Param("userId") Long userId);

    @Query("select count(fi) from FillIngredient fi join fi.drinkList d where d.id =:drinkId")
    Integer getCountOfFillerIngredientsOfDrink(@Param("drinkId") Long drinkId);

    @Query("select count(ai) from AlcoholIngredient ai join ai.drinks d where d.id =:drinkId")
    Integer getCountOfAlcoholIngredientsOfDrink(@Param("drinkId") Long drinkId);

}
