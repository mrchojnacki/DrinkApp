package pl.coderslab.drink;

import org.springframework.stereotype.Repository;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.FillIngredient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DrinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Drink> findAllDrinks() {
        Query query = entityManager.createQuery("select d from Drink d");
        return query.getResultList();
    }
    public Drink findById(Long id) {
        return entityManager.find(Drink.class, id);
    }
    public void createDrink(Drink drink) {
        entityManager.persist(drink);
    }
    public Drink updateDrink(Drink drink) {
        return entityManager.merge(drink);
    }
    public void deleteDrink(Long id) {
        entityManager.remove(findById(id));
    }

    public List<AlcoholIngredient> findAllAlcoholIngredientsForDrink(Drink drink) {
        Query query = entityManager.createQuery
                ("select ai from AlcoholIngredient ai join ai.drinks d where d.id =:drinkId");
        query.setParameter("drinkId", drink.getId());
        return query.getResultList();
    }
    public List<FillIngredient> findAllFillerIngredientForDrink(Drink drink) {
        Query query = entityManager.createQuery
                ("select fi from FillIngredient fi join fi.drinkList d where d.id =:drinkId");
        query.setParameter("drinkId", drink.getId());
        return query.getResultList();
    }
}
