package pl.coderslab.drink;

import org.springframework.stereotype.Service;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.AlcoholIngredientRepository;
import pl.coderslab.ingredient.FillIngredient;
import pl.coderslab.ingredient.FillerIngredientRepository;
import pl.coderslab.rating.RatingEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinkService {
    private DrinkRepository drinkRepository;
    private AlcoholIngredientRepository alcoholIngredientRepository;
    private FillerIngredientRepository fillerIngredientRepository;

    public DrinkService(DrinkRepository drinkRepository,
                        AlcoholIngredientRepository alcoholIngredientRepository,
                        FillerIngredientRepository fillerIngredientRepository) {
        this.drinkRepository = drinkRepository;
        this.alcoholIngredientRepository = alcoholIngredientRepository;
        this.fillerIngredientRepository = fillerIngredientRepository;
    }

    public Drink addDrinkToDb(DrinkRequestDTO drinkRequestDTOFromController) {
        Drink newDrink = new Drink();
        RatingEntity ratingEntity = new RatingEntity();
        newDrink.setName(drinkRequestDTOFromController.getName());
        newDrink.setMethod(drinkRequestDTOFromController.getMethod());
        ratingEntity.setCount(0);
        ratingEntity.setSumOfRatings(0L);
        newDrink.setRating(ratingEntity);
        //userId
        List<AlcoholIngredient> existingAlcoholList = searchForAlcoholIngredientsFromGivenIds(drinkRequestDTOFromController.getExistingAlcoholIngredients());
        List<AlcoholIngredient> alcoholIngredientList = drinkRequestDTOFromController.getAlcoholIngredients();
        alcoholIngredientList.addAll(existingAlcoholList);
        newDrink.setAlcoholIngredients(alcoholIngredientList);

        List<FillIngredient> existingFillList = searchForFillIngredientsFromGivenIds(drinkRequestDTOFromController.getExistingFillIngredientsIds());
        List<FillIngredient> fillIngredientList = drinkRequestDTOFromController.getFillIngredients();
        fillIngredientList.addAll(existingFillList);
        newDrink.setFillIngredients(fillIngredientList);

        drinkRepository.createDrink(newDrink);
        Drink persistedDrink = newDrink;
        return persistedDrink;
    }
    private List<AlcoholIngredient> searchForAlcoholIngredientsFromGivenIds (String[] alcoholIds) {
        List<AlcoholIngredient> alcoholIngredientList = Arrays.stream(alcoholIds)
                .map(n -> alcoholIngredientRepository.findAlcoholIngredientById(Long.parseLong(n)))
                .collect(Collectors.toList());
        return alcoholIngredientList;
    }

    private List<FillIngredient> searchForFillIngredientsFromGivenIds(String[] fillIds) {
        List<FillIngredient> fillIngredientList = Arrays.stream(fillIds)
                .map(n -> fillerIngredientRepository.findFillerIngredientById(Long.parseLong(n)))
                .collect(Collectors.toList());
        return fillIngredientList;
    }

    public List<DrinkResponseDTO> getDrinkResponseDTOList() {
        return drinkRepository.findAllDrinks()
                .stream()
                .map(n -> new DrinkResponseDTO(n.getId(), n.getName(), n.getMethod(), n.getAlcoholIngredients(), n.getFillIngredients()))
                .collect(Collectors.toList());
    }


}
