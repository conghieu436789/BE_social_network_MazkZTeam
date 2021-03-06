package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.repositories.FriendshipRepository;
import socialnetwork.mazkzteam.model.service.FriendshipService;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Override
    public List<Friendship> getAll() {
        return friendshipRepository.findAll();
    }

    @Override
    public Friendship save(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship findById(int id) {
        return friendshipRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            friendshipRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<IFriend> receiverList(Integer id) {
        return friendshipRepository.receiverList(id);
    }

    @Override
    public List<IFriend> getListFriend(Integer id) {
        return friendshipRepository.getListFriend(id);
    }

    @Override
    public void deleteFriend(Integer idReceiver,Integer idSender) {
        friendshipRepository.deleteFriend(idReceiver,idSender);
    }

    @Override
    public void acceptFriend(Integer idSender, Integer idReceiver) {
         friendshipRepository.acceptFriend(idSender,idReceiver);
    }

    @Override
    public void addFriend(Integer idSender, Integer idReceiver) {
        friendshipRepository.addFriend(idSender,idReceiver);
    }

    @Override
    public void cancelFriendRequest(Integer idSender, Integer idReceiver) {
        friendshipRepository.cancelFriendRequest(idSender,idReceiver);
    }

    @Override
    public List<IFriend> friendNotRequest(Integer userID) {
        return friendshipRepository.friendNotRequest(userID);
    }

    @Override
    public List<IFriend> senderFriendRequestList(Integer idSender) {
        return friendshipRepository.senderFriendRequestList(idSender);
    }


}
