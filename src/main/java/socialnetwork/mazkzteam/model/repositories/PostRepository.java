package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUser(User user);

//    @Query(value = "select post.id, post.content, post.created_date, post.modified_at, post.user_id, post.club_id, post.protective from post \n" +
//            "inner join (SELECT * FROM social_network.friendship\n" +
//            "where (user_receiver_id=?1 or user_sender_id=?1) and status=true) as f\n" +
//            "on post.user_id = f.user_receiver_id or post.user_id = f.user_sender_id\n" +
//            "where (user_id =?1 or user_id<>?1 and (protective=1 or protective=2)) and club_id=9999\n" +
//            "group by created_date order by created_date desc;", nativeQuery = true)
//    List<Post> findAllCommonFriendPublicPost(int id);

    @Query(value = "call get_common_friend_public_post(?1)", nativeQuery = true)
    List<Post> findAllCommonFriendPublicPost(int id);

    List<Post> getPostsByClub(Club club);

    @Query(value = "select * from post where user_id=?1 and protective=1 and club_id=9999", nativeQuery = true)
    List<Post> findAllPublicUserPost(int id);

    @Query(value = "select * from post where user_id=?1 and (protective=1 or protective=2) and club_id=9999", nativeQuery = true)
    List<Post> findAllPublicAndFriendUserPost(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from post where club_id = ?1",nativeQuery = true)
    void deleteAllByClub_idHieu(int id);
}
