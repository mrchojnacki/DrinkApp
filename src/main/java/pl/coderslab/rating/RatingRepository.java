package pl.coderslab.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Query("select avg(r.valueOfRating) from RatingEntity r where r.drink.id =:drinkId")
    Double getRatingForDrinkFromDrinkId(@Param("drinkId") Long drinkId);

    @Query("select r from RatingEntity r where r.drink.id =:drinkId and r.user.id =:userId")
    Optional<RatingEntity> getRatingEntityForUpdate(@Param("drinkId") Long drinkId, @Param("userId") Long userId);

    @Query("select r from RatingEntity r where r.drink.id =:drinkId")
    List<RatingEntity> getAllRatingsOfDrinkByDrinkId(@Param("drinkId") Long drinkId);
}
