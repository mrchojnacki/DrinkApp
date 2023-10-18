package pl.coderslab.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Query("select avg(r.valueOfRating) from RatingEntity r where r.drink.id =:drinkId")
    public Double getRatingForDrinkFromDrinkId(Long id);

    @Query("select r from RatingEntity r where r.drink.id =:drinkId and r.user.id =:userId")
    public RatingEntity getRatingEntityForUpdate(Long drinkId, Long userId);

    @Query("select count(*) from RatingEntity r where r.drink.id =:drinkId")
    public Integer getAllRatingForDrinkFromDrinkId(Long drinkId);
}
