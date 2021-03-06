package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {

    @Query(value = "SELECT social_network.user.id,social_network.user.avatar,social_network.user.username,social_network.user.address,social_network.user.date_of_birth,social_network.user.first_name,social_network.user.last_name,social_network.user.phone,social_network.user.gender,social_network.friendship.status\n" +
            "FROM user\n" +
            "INNER JOIN friendship\n" +
            "ON user.id = friendship.user_sender_id where friendship.status = false and friendship.user_receiver_id =?1",nativeQuery = true)
    List<IFriend> receiverList(Integer id);

    @Query(value = "SELECT distinct social_network.user.id,social_network.user.avatar,social_network.user.username,social_network.user.address,social_network.user.date_of_birth,social_network.user.first_name,social_network.user.last_name,social_network.user.phone,social_network.user.gender,social_network.friendship.status\n" +
            "FROM user\n" +
            "INNER JOIN friendship\n" +
            "ON user.id = friendship.user_sender_id or user.id = friendship.user_receiver_id where friendship.status = true and (friendship.user_sender_id =?1 or friendship.user_receiver_id =?1) and social_network.user.id <> ?1 ",nativeQuery = true )
    List<IFriend> getListFriend(Integer id);

    //

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM friendship\n" +
            "WHERE (user_receiver_id=?1 AND user_sender_id= ?2) OR (user_receiver_id=?2 AND user_sender_id= ?1) AND status = true",nativeQuery = true)
    void deleteFriend(Integer idReceiver,Integer idSender);

    @Transactional
    @Modifying
    @Query(value = "UPDATE friendship\n" +
            "SET status = true\n" +
            "WHERE user_sender_id=? AND user_receiver_id = ?;",nativeQuery = true)
    void acceptFriend(Integer idSender, Integer idReceiver);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO friendship (status, user_sender_id,user_receiver_id)\n" +
            "VALUES (false ,?,?)",nativeQuery = true)
    void addFriend(Integer idSender, Integer idReceiver);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM friendship\n" +
            "WHERE (user_sender_id= ? AND user_receiver_id=?) AND status = false ",nativeQuery = true)
    void cancelFriendRequest(Integer idSender, Integer idReceiver);

    @Query(value = "SELECT  social_network.user.id,social_network.user.avatar,social_network.user.username,social_network.user.address,social_network.user.date_of_birth,social_network.user.first_name,social_network.user.last_name,social_network.user.phone,social_network.user.gender,friend2.status\n" +
            "FROM user\n" +
            "LEFT JOIN (select * from social_network.friendship where user_receiver_id = ?1 or user_sender_id = ?1) as friend2\n" +
            "ON user.id = friend2.user_sender_id or user.id = friend2.user_receiver_id group by social_network.user.username having user.id<>?1 and status is null",nativeQuery = true)
    List<IFriend> friendNotRequest(Integer userId);

    @Query(value = "SELECT social_network.user.id,social_network.user.avatar,social_network.user.username,social_network.user.address,social_network.user.date_of_birth,social_network.user.first_name,social_network.user.last_name,social_network.user.phone,social_network.user.gender,social_network.friendship.status\n" +
            "FROM user\n" +
            "INNER JOIN friendship\n" +
            "ON user.id = friendship.user_receiver_id where friendship.status = false and friendship.user_sender_id =?1",nativeQuery = true)
    List<IFriend> senderFriendRequestList(Integer idSender);
}


    //where user.id<>?1 and status is null
