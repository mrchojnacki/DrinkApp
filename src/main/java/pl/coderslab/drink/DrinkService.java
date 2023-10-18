package pl.coderslab.drink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.coderslab.ingredient.AlcoholIngredient;
import pl.coderslab.ingredient.AlcoholIngredientRepository;
import pl.coderslab.ingredient.FillIngredient;
import pl.coderslab.ingredient.FillerIngredientRepository;
import pl.coderslab.rating.RatingEntity;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
/*        ratingEntity.setRatingCount(0);
        ratingEntity.setSumOfRatings(0L);
        newDrink.setRating(ratingEntity);*/
        //userId
        List<AlcoholIngredient> existingAlcoholList = searchForAlcoholIngredientsFromGivenIds(drinkRequestDTOFromController.getExistingAlcoholIngredients());
        List<AlcoholIngredient> alcoholIngredientList = drinkRequestDTOFromController.getAlcoholIngredients();
        alcoholIngredientList.addAll(existingAlcoholList);
        newDrink.setAlcoholIngredients(alcoholIngredientList);

        List<FillIngredient> existingFillList = searchForFillIngredientsFromGivenIds(drinkRequestDTOFromController.getExistingFillIngredientsIds());
        List<FillIngredient> fillIngredientList = drinkRequestDTOFromController.getFillIngredients();
        fillIngredientList.addAll(existingFillList);
        newDrink.setFillIngredients(fillIngredientList);

        drinkRepository.save(newDrink);
        Drink persistedDrink = newDrink;
        return persistedDrink;
    }

    public void saveImageToDirectory(Part image, String name) {
        Long id = drinkRepository.getDrinkByName(name).orElse(null).getId();
        String uploadPath = "/images/" + id.toString();
        try (InputStream input = image.getInputStream()) {
            Files.copy(input, new File(uploadPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return drinkRepository.findAll()
                .stream()
                .map(n -> new DrinkResponseDTO(n.getId(), n.getName(), n.getMethod(), n.getAlcoholIngredients(), n.getFillIngredients()))
                .collect(Collectors.toList());
    }


    public Page<DrinkResponseDTO> getPagedDrinkResponseDTOList(String noOfPage) {
        Pageable pageRequest = createPageRequestUsing(Integer.parseInt(noOfPage)-1, 5);
        List<DrinkResponseDTO> drinkResponseDTOListList = getDrinkResponseDTOList();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), drinkResponseDTOListList.size());
        List<DrinkResponseDTO> pageContent = drinkResponseDTOListList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, drinkResponseDTOListList.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }


}
