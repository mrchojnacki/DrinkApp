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
        return entityManager.createQuery("select ai from AlcoholIngredient ai", AlcoholIngredient.class).getResultList();
    }
    public AlcoholIngredient findAlcoholIngredientById(Long id){
        return entityManager.find(AlcoholIngredient.class, id);
    }
    public List<AlcoholIngredient> findAlcoholIngredientByName(String name) {
        return (List<AlcoholIngredient>) entityManager.createQuery("select ai from AlcoholIngredient ai where upper(ai.alcoholType) = upper(:name)")
                .setParameter("name", name)
                .getResultList();
    }


}
