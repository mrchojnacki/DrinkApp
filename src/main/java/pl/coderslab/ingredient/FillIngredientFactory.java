package pl.coderslab.ingredient;

import org.springframework.util.AutoPopulatingList;

public class FillIngredientFactory implements AutoPopulatingList.ElementFactory<FillIngredient>{
    @Override
    public FillIngredient createElement(int i) throws AutoPopulatingList.ElementInstantiationException {
        FillIngredient fillIngredient = new FillIngredient();
        return fillIngredient;
    }
}
