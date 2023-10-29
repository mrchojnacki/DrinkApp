package pl.coderslab.drink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.ingredient.*;
import pl.coderslab.rating.RatingEntity;
import pl.coderslab.rating.RatingRepository;
import pl.coderslab.rating.RatingService;
import pl.coderslab.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinkService {
    private DrinkRepository drinkRepository;
    private AlcoholIngredientRepository alcoholIngredientRepository;
    private FillerIngredientRepository fillerIngredientRepository;
    private UserRepository userRepository;
    private IngredientService ingredientService;

    private RatingService ratingService;

    public DrinkService(DrinkRepository drinkRepository, AlcoholIngredientRepository alcoholIngredientRepository, FillerIngredientRepository fillerIngredientRepository, UserRepository userRepository, IngredientService ingredientService, RatingService ratingService) {
        this.drinkRepository = drinkRepository;
        this.alcoholIngredientRepository = alcoholIngredientRepository;
        this.fillerIngredientRepository = fillerIngredientRepository;
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
        this.ratingService = ratingService;
    }

    public boolean checkIfDrinkNameAlreadyInDb(String name) {
        boolean nameTaken = false;
        for (Drink d : drinkRepository.findAll()) {
            if (d.getName().toLowerCase().equals(name.toLowerCase())) {
                nameTaken = true;
            }
        }
        return nameTaken;
    }

    public Drink addDrinkToDb(DrinkRequestDTO drinkRequestDTOFromController, HttpSession sess) {
        Drink newDrink = new Drink();
        Drink drinkToAdd = manageDrinkFromAddEdit(newDrink, drinkRequestDTOFromController, sess);
        drinkRepository.save(drinkToAdd);
        return  drinkToAdd;
    }

    public Drink editDrinkToDb(DrinkRequestDTO drinkRequestDTOFromController, HttpSession sess, String drinkId) {
        Drink drinkToEdit = drinkRepository.findById(Long.parseLong(drinkId)).orElse(null);
        Drink editedDrink = manageDrinkFromAddEdit(drinkToEdit, drinkRequestDTOFromController, sess);
        drinkRepository.save(editedDrink);
        return editedDrink;
    }

    private Drink manageDrinkFromAddEdit(Drink managedDrink, DrinkRequestDTO drinkRequestDTO, HttpSession sess) {
        managedDrink.setName(drinkRequestDTO.getName());
        managedDrink.setMethod(drinkRequestDTO.getMethod());
        managedDrink.setUser(userRepository.findUserById((Long)sess.getAttribute("authenticatedUserId")));
        List<AlcoholIngredient> existingAlcoholList = new ArrayList<>();
        if (drinkRequestDTO.getExistingAlcoholIngredients()!=null) {
            existingAlcoholList = searchForAlcoholIngredientsFromGivenIds(drinkRequestDTO.getExistingAlcoholIngredients());
        }
        List<AlcoholIngredient> alcoholIngredientList = drinkRequestDTO.getAlcoholIngredients();
        alcoholIngredientList.addAll(existingAlcoholList);
        managedDrink.setAlcoholIngredients(alcoholIngredientList);
        List<FillIngredient> existingFillList = new ArrayList<>();
        if (drinkRequestDTO.getExistingFillIngredientsIds()!=null) {
            existingFillList = searchForFillIngredientsFromGivenIds(drinkRequestDTO.getExistingFillIngredientsIds());
        }
        List<FillIngredient> fillIngredientList = drinkRequestDTO.getFillIngredients();
        fillIngredientList.addAll(existingFillList);
        managedDrink.setFillIngredients(fillIngredientList);
        return managedDrink;
    }

    public void saveImageToDirectory(MultipartFile image, String name) {
        Long id = drinkRepository.getDrinkByName(name).map(Drink::getId).orElse(null);
        String uploadPath = "/home/micha/Projekty/DrinkApp/src/main/webapp/images/";
        try {
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String imagePath = uploadPath + id.toString();
            FileOutputStream fos = new FileOutputStream(imagePath);
            FileCopyUtils.copy(image.getInputStream(), fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] removeEmptyStrings(String[] array) {
        return Arrays.stream(array)
                .filter(str -> !str.isEmpty())
                .toArray(String[]::new);
    }

    public boolean isMadeByLoggedUser (String drinkId, HttpSession sess) {
        return drinkRepository.findById(Long.parseLong(drinkId)).orElse(null).getUser().getId() == sess.getAttribute("authenticatedUserId");
    }

    public DrinkResponseDTO getRandomDrinkResponseDTO() {
        Random r = new Random();
        int randomNumber = r.nextInt(findAllDrink().size() - 1) + 1;
        return getDrinkResponseDTOFromId(String.valueOf(randomNumber));
    }

    public Long getDrinkIdFromName(String name) {
        return drinkRepository.getDrinkByName(name).orElse(null).getId();
    }
    private List<Drink> findAllDrink() {
        return drinkRepository.findAll();
    }

    public List<DrinkResponseDTO> getDrinkUserCanMake(String[] alcoholNames,
                                                      String[] fillerNames) {
        List<Drink> allDrinkInDb = drinkRepository.findAll();
        List<DrinkResponseDTO> listOfDrinkUserCanMake = new ArrayList<>();
        List<FillIngredient> fillIngredientListFromForm = new ArrayList<>();
        Arrays.stream(alcoholNames).map(n -> fillIngredientListFromForm.addAll(ingredientService.getFillIngredientByName(n)));

        List<AlcoholIngredient> alcoholIngredientListFromForm = new ArrayList<>();
        Arrays.stream(fillerNames).map(n -> alcoholIngredientListFromForm.addAll(alcoholIngredientRepository.findAlcoholIngredientByName(n)));

        for (Drink d : allDrinkInDb) {
            int noOfIngredientsOfDrink = drinkRepository.getCountOfAlcoholIngredientsOfDrink(d.getId()) + drinkRepository.getCountOfFillerIngredientsOfDrink(d.getId());
            int counter= getCounterOfMatchIngredients(d, fillIngredientListFromForm, alcoholIngredientListFromForm);
            if (counter==noOfIngredientsOfDrink) {
                listOfDrinkUserCanMake.add(getDrinkResponseDTOFromId(d.getId().toString()));
            }
        }
        return listOfDrinkUserCanMake;
    }

    public List<DrinkResponseDTO> getDrinkUserCanMakeMinusOne(String[] alcoholNames,
                                                      String[] fillerNames) {
        List<Drink> allDrinkInDb = drinkRepository.findAll();
        List<DrinkResponseDTO> listOfDrinkUserCanMakeMinusOne = new ArrayList<>();
        List<FillIngredient> fillIngredientListFromForm = new ArrayList<>();
        Arrays.stream(alcoholNames).map(n -> fillIngredientListFromForm.addAll(ingredientService.getFillIngredientByName(n)));

        List<AlcoholIngredient> alcoholIngredientListFromForm = new ArrayList<>();
        Arrays.stream(fillerNames).map(n -> alcoholIngredientListFromForm.addAll(alcoholIngredientRepository.findAlcoholIngredientByName(n)));

        for (Drink d : allDrinkInDb) {
            int noOfIngredientsOfDrink = drinkRepository.getCountOfAlcoholIngredientsOfDrink(d.getId()) + drinkRepository.getCountOfFillerIngredientsOfDrink(d.getId());
            int counter = getCounterOfMatchIngredients(d, fillIngredientListFromForm, alcoholIngredientListFromForm);
            if (counter==noOfIngredientsOfDrink-1) {
                listOfDrinkUserCanMakeMinusOne.add(getDrinkResponseDTOFromId(d.getId().toString()));
            }
        }
        return listOfDrinkUserCanMakeMinusOne;
    }

    public List<DrinkResponseDTO> getDrinkUserCanMakeMinusTwo(String[] alcoholNames,
                                                              String[] fillerNames) {
        List<Drink> allDrinkInDb = drinkRepository.findAll();
        List<DrinkResponseDTO> listOfDrinkUserCanMakeMinusTwo = new ArrayList<>();
        List<FillIngredient> fillIngredientListFromForm = new ArrayList<>();
        Arrays.stream(alcoholNames).map(n -> fillIngredientListFromForm.addAll(ingredientService.getFillIngredientByName(n)));

        List<AlcoholIngredient> alcoholIngredientListFromForm = new ArrayList<>();
        Arrays.stream(fillerNames).map(n -> alcoholIngredientListFromForm.addAll(alcoholIngredientRepository.findAlcoholIngredientByName(n)));

        for (Drink d : allDrinkInDb) {
            int noOfIngredientsOfDrink = drinkRepository.getCountOfAlcoholIngredientsOfDrink(d.getId()) + drinkRepository.getCountOfFillerIngredientsOfDrink(d.getId());
            int counter = getCounterOfMatchIngredients(d, fillIngredientListFromForm, alcoholIngredientListFromForm);
            if (counter==noOfIngredientsOfDrink-2) {
                listOfDrinkUserCanMakeMinusTwo.add(getDrinkResponseDTOFromId(d.getId().toString()));
            }
        }
        return listOfDrinkUserCanMakeMinusTwo;
    }

    private Integer getCounterOfMatchIngredients(Drink drink,
                                                 List<FillIngredient> fillIngredientListFromForm,
                                                 List<AlcoholIngredient> alcoholIngredientListFromForm) {
        int counter=0;
        for (FillIngredient fi : drink.getFillIngredients()) {
            for (FillIngredient fiFromForm : fillIngredientListFromForm) {
                if (fiFromForm.equals(fi)) {
                    counter++;
                }
            }
        }
        for(AlcoholIngredient ai : drink.getAlcoholIngredients()) {
            for(AlcoholIngredient aiFromForm : alcoholIngredientListFromForm) {
                if (aiFromForm.equals(ai)){
                    counter++;
                }
            }
        }
        return counter;
    }

    private List<AlcoholIngredient> searchForAlcoholIngredientsFromGivenIds (String[] alcoholIds) {
        return Arrays.stream(alcoholIds)
                .map(n -> alcoholIngredientRepository.findAlcoholIngredientById(Long.parseLong(n)))
                .collect(Collectors.toList());
    }

    private List<FillIngredient> searchForFillIngredientsFromGivenIds(String[] fillIds) {
        return Arrays.stream(fillIds)
                .map(n -> fillerIngredientRepository.findFillerIngredientById(Long.parseLong(n)))
                .collect(Collectors.toList());
    }

    private List<DrinkResponseDTO> getDrinkResponseDTOList() {
        return drinkRepository.findAll()
                .stream()
                .map(n -> new DrinkResponseDTO(n.getId(), n.getName(), n.getMethod(), n.getAlcoholIngredients(), n.getFillIngredients()))
                .collect(Collectors.toList());
    }

    private List<DrinkResponseDTO> getUserDrinkResponseDTOList(Long userId) {
        return drinkRepository.getAllUserMadeDrinks(userId)
                .stream()
                .map(n -> new DrinkResponseDTO(n.getId(), n.getName(), n.getMethod(), n.getAlcoholIngredients(), n.getFillIngredients()))
                .collect(Collectors.toList());
    }

    private List<DrinkResponseDTO> getFavoriteDrinkResponseDTOList(Long userId) {
        return userRepository.getFavoriteDrinksOfUser(userId)
                .stream()
                .map(n -> new DrinkResponseDTO(n.getId(), n.getName(), n.getMethod(), n.getAlcoholIngredients(), n.getFillIngredients()))
                .collect(Collectors.toList());
    }


    public DrinkResponseDTO getDrinkResponseDTOFromId(String drinkId) {
        List<DrinkResponseDTO> drinkResponseDTOList = getDrinkResponseDTOList();
        DrinkResponseDTO drinkResponseDTOToController = new DrinkResponseDTO();
        for (DrinkResponseDTO drinkResponseDTO : drinkResponseDTOList) {
            if (drinkResponseDTO.getId()==Long.parseLong(drinkId)) {
                drinkResponseDTOToController = drinkResponseDTO;
            }
        }
        return drinkResponseDTOToController;
    }

    public void toggleDrinkToFavorites(Long drinkId, HttpSession sess) {
        Long userId = (Long)sess.getAttribute("authenticatedUserId");
        List<Drink> listOfFavDrinksOfUser = userRepository.getFavoriteDrinksOfUser(userId);
        if (isFavorite(drinkId, sess)) {
            listOfFavDrinksOfUser.remove(drinkRepository.findById(drinkId).orElse(null));
            userRepository.findUserById(userId).setFavDrinks(listOfFavDrinksOfUser);
        } else {
            listOfFavDrinksOfUser.add(drinkRepository.findById(drinkId).orElse(null));
            userRepository.findUserById(userId).setFavDrinks(listOfFavDrinksOfUser);
        }
    }

    public boolean isFavorite(Long drinkId, HttpSession sess) {
        boolean isInFavoriteList = false;
        List<Drink> listOfFavoriteDrinksOfUser = userRepository.getFavoriteDrinksOfUser((Long)sess.getAttribute("authenticatedUserId"));
        for (Drink d : listOfFavoriteDrinksOfUser) {
            if (d.getId()==drinkId) {
                isInFavoriteList = true;
            }
        }
        return isInFavoriteList;
    }

    public List<AlcoholIngredient> castingNewAlcoholIngredients (String[] newAlcoholName, String [] newAlcoholVolume) {
        List<AlcoholIngredient> newAlcoholIngredients = new ArrayList<>();
        for (int i = 0; i < newAlcoholName.length; i++) {
            AlcoholIngredient alcoholIngredient = new AlcoholIngredient();
            alcoholIngredient.setAlcoholType(newAlcoholName[i]);
            alcoholIngredient.setVolumeMillilitres(Integer.parseInt(newAlcoholVolume[i]));
            newAlcoholIngredients.add(alcoholIngredient);
        }
        return newAlcoholIngredients;
    }
    public List<FillIngredient> castingNewFillIngredients(String[] newFillName, String[] newFillAmount) {
        List<FillIngredient> newFillIngredients = new ArrayList<>();
        for (int i = 0; i < newFillName.length; i++) {
            FillIngredient fillIngredient = new FillIngredient();
            fillIngredient.setFill(newFillName[i]);
            fillIngredient.setAmount(newFillAmount[i]);
            newFillIngredients.add(fillIngredient);
        }
        return newFillIngredients;
    }


    public DrinkResponseDTO findLastDrinkOfUser(Long userId) {
        return getDrinkResponseDTOFromId(drinkRepository.findDrinkOfUser(userId).get(drinkRepository.findDrinkOfUser(userId).size()-1).getId().toString());
    }

    public void addNewRating(Long drinkId, int rating, HttpSession sess) {
        RatingEntity ratingEntity = ratingService.getRatingEntity(drinkId, (Long) sess.getAttribute("authenticatedUserId")).orElse(null);
        if (ratingEntity==null) {
            ratingEntity = new RatingEntity();
        }
        ratingEntity.setDrink(drinkRepository.findById(drinkId).orElse(null));
        ratingEntity.setValueOfRating(rating);
        ratingEntity.setUser(userRepository.findUserById((Long)sess.getAttribute("authenticatedUserId")));
        ratingService.addNewRating(ratingEntity);
    }

    public Page<DrinkResponseDTO> getPagedAllDrinkResponseDTOList(String noOfPage) {
        Pageable pageRequest = createPageRequestUsing(Integer.parseInt(noOfPage)-1, 5);
        List<DrinkResponseDTO> drinkResponseDTOListList = getDrinkResponseDTOList();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), drinkResponseDTOListList.size());
        List<DrinkResponseDTO> pageContent = drinkResponseDTOListList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, drinkResponseDTOListList.size());
    }

    public Page<DrinkResponseDTO> getPagedUserDrinkResponseDTOList(String noOfPage, HttpSession sess) {
        Long userId = (Long) sess.getAttribute("authenticatedUserId");
        Pageable pageRequest = createPageRequestUsing(Integer.parseInt(noOfPage)-1, 5);
        List<DrinkResponseDTO> userDrinkResponseDTOList = getUserDrinkResponseDTOList(userId);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), userDrinkResponseDTOList.size());
        List<DrinkResponseDTO> pageContent = userDrinkResponseDTOList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, userDrinkResponseDTOList.size());
    }

    public Page<DrinkResponseDTO> getPagedFavoriteDrinkResponseDTOList(String noOfPage, HttpSession sess) {
        Long userId = (Long) sess.getAttribute("authenticatedUserId");
        Pageable pageRequest = createPageRequestUsing(Integer.parseInt(noOfPage)-1, 5);
        List<DrinkResponseDTO> userDrinkResponseDTOList = getFavoriteDrinkResponseDTOList(userId);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), userDrinkResponseDTOList.size());
        List<DrinkResponseDTO> pageContent = userDrinkResponseDTOList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, userDrinkResponseDTOList.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }


}
