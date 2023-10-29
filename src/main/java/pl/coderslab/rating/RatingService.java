package pl.coderslab.rating;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    public Double getAverageRatingForDrink(String drinkId) {
        return ratingRepository.getRatingForDrinkFromDrinkId(Long.parseLong(drinkId));
    }

    public Optional<RatingEntity> getRatingEntity(Long drinkId, Long userId) {
        return ratingRepository.getRatingEntityForUpdate(drinkId, userId);
    }

    public void addNewRating(RatingEntity ratingEntity) {
        ratingRepository.save(ratingEntity);
    }
}
