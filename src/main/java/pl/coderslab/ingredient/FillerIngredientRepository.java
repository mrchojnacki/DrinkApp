package pl.coderslab.ingredient;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FillerIngredientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public List<FillIngredient> findAllFillerIngredient(){
       return entityManager.createQuery("select fi from FillIngredient fi").getResultList();
    }
    public FillIngredient findFillerIngredientById(Long id) {
        return entityManager.find(FillIngredient.class, id);
    }
    public List<FillIngredient> findFillerIngredientByName(String name) {
        return (List<FillIngredient>) entityManager.createQuery("select fi from FillIngredient fi where upper(fi.fill)  = upper(:name)")
                .setParameter("name", name)
                .getResultList();
    }
    public FillIngredient createFillerIngredient(FillIngredient fillIngredient) {
        return entityManager.merge(fillIngredient);
    }
    public FillIngredient updateFillerIngredient(FillIngredient fillIngredient) {
        return entityManager.merge(fillIngredient);
    }
}
