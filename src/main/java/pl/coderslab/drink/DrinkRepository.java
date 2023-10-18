package pl.coderslab.drink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.FillIngredient;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    @Override
    Optional<Drink> findById(Long id);

    Optional<Drink> getDrinkByName(String name);

    @Query("select ai from AlcoholIngredient ai join ai.drinks d where d.id =:drinkId")
    public List<AlcoholIngredient> findAllAlcoholIngredientsForDrink(@Param("drinkId") Long drinkId);

    @Query("select fi from FillIngredient fi join fi.drinkList d where d.id =:drinkId")
    public List<FillIngredient> findAllFillerIngredientForDrink(@Param("drinkId") Long drinkId);

}
