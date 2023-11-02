package pl.coderslab.drink;

import org.springframework.util.AutoPopulatingList;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.AlcoholIngredientFactory;
import pl.coderslab.ingredient.FillIngredient;
import pl.coderslab.ingredient.FillIngredientFactory;

import java.util.List;

public class DrinkRequestDTO {
    private String name;
    private String method;
    private List<AlcoholIngredient> alcoholIngredients =  new AutoPopulatingList<AlcoholIngredient>(new AlcoholIngredientFactory());
    private List<FillIngredient> fillIngredients = new AutoPopulatingList<FillIngredient>(new FillIngredientFactory());
    private String[] existingAlcoholIngredientsIds;
    private String[] existingFillIngredientsIds;

    public DrinkRequestDTO() {
    }

    public DrinkRequestDTO(String name, String method, List<AlcoholIngredient> alcoholIngredients, List<FillIngredient> fillIngredients, String[] existingAlcoholIngredientsIds, String[] existingFillIngredientsIds) {
        this.name = name;
        this.method = method;
        this.alcoholIngredients = alcoholIngredients;
        this.fillIngredients = fillIngredients;
        this.existingAlcoholIngredientsIds = existingAlcoholIngredientsIds;
        this.existingFillIngredientsIds = existingFillIngredientsIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<AlcoholIngredient> getAlcoholIngredients() {
        return alcoholIngredients;
    }

    public void setAlcoholIngredients(List<AlcoholIngredient> alcoholIngredients) {
        this.alcoholIngredients = alcoholIngredients;
    }

    public List<FillIngredient> getFillIngredients() {
        return fillIngredients;
    }

    public void setFillIngredients(List<FillIngredient> fillIngredients) {
        this.fillIngredients = fillIngredients;
    }

    public String[] getExistingAlcoholIngredients() {
        return existingAlcoholIngredientsIds;
    }

    public void setExistingAlcoholIngredients(String[] existingAlcoholIngredients) {
        this.existingAlcoholIngredientsIds = existingAlcoholIngredients;
    }

    public String[] getExistingFillIngredientsIds() {
        return existingFillIngredientsIds;
    }

    public void setExistingFillIngredientsIds(String[] existingFillIngredientsIds) {
        this.existingFillIngredientsIds = existingFillIngredientsIds;
    }
}
