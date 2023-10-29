package pl.coderslab.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Query("select avg(r.valueOfRating) from RatingEntity r where r.drink.id =:drinkId")
    public Double getRatingForDrinkFromDrinkId(@Param("drinkId") Long drinkId);

    @Query("select r from RatingEntity r where r.drink.id =:drinkId and r.user.id =:userId")
    public Optional<RatingEntity> getRatingEntityForUpdate(@Param("drinkId") Long drinkId, @Param("userId") Long userId);

    @Query("select count(*) from RatingEntity r where r.drink.id =:drinkId")
    public Integer getAllCountRatingForDrinkFromDrinkId(@Param("drinkId") Long drinkId);
}
