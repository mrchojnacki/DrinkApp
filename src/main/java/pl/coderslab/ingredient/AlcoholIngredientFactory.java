package pl.coderslab.ingredient;

import org.springframework.util.AutoPopulatingList;

public class AlcoholIngredientFactory implements AutoPopulatingList.ElementFactory<AlcoholIngredient> {
    public AlcoholIngredient createElement(int index) {
        AlcoholIngredient alcoholIngredient = new AlcoholIngredient();
        return alcoholIngredient;
    }
}
