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


    public List<FillIngredient> getFillIngredientByName(String name) {
        return fillerIngredientRepository.findFillerIngredientByName(name);
    }

    public List<AlcoholIngredient> getAlcoholIngredientByName(String name) {
        return alcoholIngredientRepository.findAlcoholIngredientByName(name);
    }

    public List<AlcoholIngredient> getAllAlcoholIngredients(){
        return alcoholIngredientRepository.findAllAlcoholIngredients();
    }

    public List<FillIngredient> getAllFillIngredients() {
        return fillerIngredientRepository.findAllFillerIngredient();
    }


}
