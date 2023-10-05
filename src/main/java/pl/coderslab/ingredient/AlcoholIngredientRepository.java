package pl.coderslab.ingredient;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AlcoholIngredientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<AlcoholIngredient> findAllAlcoholIngredients() {
        return (List<AlcoholIngredient>) entityManager.createQuery("select ai from AlcoholIngredient ai", AlcoholIngredient.class);
    }
    public AlcoholIngredient findAlcoholIngredientById(Long id){
        return entityManager.find(AlcoholIngredient.class, id);
    }
    public AlcoholIngredient createAlcoholIngredient(AlcoholIngredient alcoholIngredient) {
        return entityManager.merge(alcoholIngredient);
    }
    public AlcoholIngredient updateAlcoholIngredient(AlcoholIngredient alcoholIngredient) {
        return entityManager.merge(alcoholIngredient);
    }
    public void deleteAlcoholIngredient(Long id) {
        entityManager.remove(findAlcoholIngredientById(id));
    }
    public AlcoholIngredient findAlcoholIngredientByName(String name) {
        return (AlcoholIngredient) entityManager.createQuery("select ai from AlcoholIngredient ai where ai.alcoholType =:name")
                .setParameter("name", name)
                .getSingleResult();
    }

}
