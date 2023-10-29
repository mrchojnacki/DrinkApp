package pl.coderslab.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select c from CommentEntity c where c.drink.id =:drinkId")
    List<CommentEntity> getCommentEntitiesForDrinkFromDrinkId(@Param("drinkId") Long drinkId);

    @Query("select u.userName from CommentEntity c join c.user u where c.drink.id =:drinkId")
    List<String> getUserNameListForComment(@Param("drinkId") Long drinkId);

/*    @Query("select u.userName from CommentEntity c join c.user u where c.user.id =:userId")
    String getUserNameForComment(@Param("userId") Long userId);*/

    @Query(value = "select Users.userName from Comments join Users on Comments.user_id = Users.id where Users.id = ?1 LIMIT 1", nativeQuery = true)
    String getUserNameForComment(Long userId);
}
