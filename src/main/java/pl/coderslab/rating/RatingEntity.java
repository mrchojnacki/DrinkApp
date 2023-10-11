package pl.coderslab.rating;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name= "Ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;
    private Long sumOfRatings;

    @Formula("case when count = 0 then 0 else sumOfRatings / count end")
    private Double rating;

    public RatingEntity() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getSumOfRatings() {
        return sumOfRatings;
    }

    public void setSumOfRatings(Long sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
