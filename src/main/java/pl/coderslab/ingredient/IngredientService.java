package pl.coderslab.ingredient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final AlcoholIngredientRepository alcoholIngredientRepository;
    private final FillerIngredientRepository fillerIngredientRepository;

    public IngredientService(AlcoholIngredientRepository alcoholIngredientRepository, FillerIngredientRepository fillerIngredientRepository) {
        this.alcoholIngredientRepository = alcoholIngredientRepository;
        this.fillerIngredientRepository = fillerIngredientRepository;
    }
    public FillIngredient saveNewFillIngredient(FillIngredient fillIngredient) {
        fillerIngredientRepository.createFillerIngredient(fillIngredient);
        FillIngredient persistedFillIngredient = fillerIngredientRepository.updateFillerIngredient(fillIngredient);
        return persistedFillIngredient;
    }
    public FillIngredient updateFillIngredient(FillIngredient fillIngredient) {
        FillIngredient updatedFillIngredient = fillerIngredientRepository.updateFillerIngredient(fillIngredient);
        return updatedFillIngredient;
    }
    public FillIngredient getFillIngredientById(Long id) {
        return fillerIngredientRepository.findFillerIngredientById(id);
    }
    public FillIngredient getFillIngredientByName(String name) {
        return fillerIngredientRepository.findFillerIngredientByName(name);
    }

    public AlcoholIngredient getAlcoholIngredientById(Long id) {
        return alcoholIngredientRepository.findAlcoholIngredientById(id);
    }
    public AlcoholIngredient getAlcoholIngredientByName(String name) {
        return alcoholIngredientRepository.findAlcoholIngredientByName(name);
    }

    public List<AlcoholIngredient> getAllAlcoholIngredients(){
        return alcoholIngredientRepository.findAllAlcoholIngredients();
    }

    public List<FillIngredient> getAllFillIngredients() {
        return fillerIngredientRepository.findAllFillerIngredient();
    }


}
