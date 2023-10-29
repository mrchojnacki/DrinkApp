package pl.coderslab.ingredient;

import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileFilter;
import java.util.List;

@Repository
@Transactional
public class FillerIngredientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public List<FillIngredient> findAllFillerIngredient(){
       return (List<FillIngredient>) entityManager.createQuery("select fi from FillIngredient fi").getResultList();
    }
    public FillIngredient findFillerIngredientById(Long id) {
        return entityManager.find(FillIngredient.class, id);
    }
    public List<FillIngredient> findFillerIngredientByName(String name) {
        return (List<FillIngredient>) entityManager.createQuery("select fi from FillIngredient fi where fi.fill =:name")
                .setParameter("name", name)
                .getResultList();
    }
    public List<FillIngredient> findDistinctFillerIngredientNames() {
        return (List<FillIngredient>) entityManager.createQuery("select distinct fi.fill from FillIngredient fi").getResultList();
    }
    public FillIngredient createFillerIngredient(FillIngredient fillIngredient) {
        return entityManager.merge(fillIngredient);
    }
    public FillIngredient updateFillerIngredient(FillIngredient fillIngredient) {
        return entityManager.merge(fillIngredient);
    }
    public void deleteFillerIngredient(Long id) {
        entityManager.remove(findFillerIngredientById(id));
    }

}
