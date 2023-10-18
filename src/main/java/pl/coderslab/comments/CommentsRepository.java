package pl.coderslab.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select c from CommentEntity c where c.drink.id =:drinkId")
    public List<CommentEntity> getCommentEntitiesForDrinkFromDrinkId(Long drinkId);

    @Query("select u.userName from CommentEntity c join c.user u where c.drink.id =:drinkId")
    public List<String> getUserNameForComment(Long drinkId);
}
