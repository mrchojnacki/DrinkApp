package pl.coderslab.drink;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DrinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Drink> findAllDrinks() {
        return (List<Drink>) entityManager.createQuery("select d from Drink d", Drink.class);
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
}
