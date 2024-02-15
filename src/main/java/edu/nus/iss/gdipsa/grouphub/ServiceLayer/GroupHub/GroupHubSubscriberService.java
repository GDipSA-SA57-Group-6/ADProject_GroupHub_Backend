package edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub;

import java.util.List;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.User;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.GroupHubRepository;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;
import jakarta.persistence.EntityNotFoundException;
import edu.nus.iss.gdipsa.grouphub.InterfaceLayer.ISubscriber;
import java.util.ArrayList;

@Service
public class GroupHubSubscriberService implements ISubscriber {
    @Autowired
    GroupHubRepository groupHubRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<GroupHub> searchByName(String name) {
        return groupHubRepository.findByNameContaining(name);
    }
    
    @Override
    public List<GroupHub> sortByPopularity() {
        return groupHubRepository.findAllByOrderByLikesDesc();
    }

    @Override
    public List<GroupHub> sortByDistance(double latitude, double longitude) {
        return null;
    }

    @Override
    public boolean eventConfirm(Integer userId, long groupId) {
        // by default, when code reaches this line, both of the userId and groupId are valid.
        // which means one can't subscribe the event that he/she launches
        User user = userRepository.findById(userId).get();
        GroupHub groupHub = groupHubRepository.findById(groupId).get();

        if(groupHub.getHasUsers().contains(user)) return false;
        groupHub.getHasUsers().add(user);
        groupHub.setQuantity(groupHub.getQuantity()-1);
        groupHubRepository.save(groupHub);

        return true;
    }

    @Override
    public boolean eventCancel(Integer userId, long groupId) {
        User user = userRepository.findById(userId).get();
        GroupHub groupHub = groupHubRepository.findById(groupId).get();

        groupHub.getHasUsers().remove(user);
        groupHub.setQuantity(groupHub.getQuantity()+1);

        groupHubRepository.save(groupHub);
        return true;
    }

    @Override
    public List<GroupHub> findAllSubscribedByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return new ArrayList<>(user.getBelongsToGroupHubs());
    }


    @Override
    public void ec_Like(Integer userId, long groupId) {

    }
}

