package edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub;

import edu.nus.iss.gdipsa.grouphub.InterfaceLayer.IPublisher;
import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.GroupHubRepository;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import edu.nus.iss.gdipsa.grouphub.ModelLayer.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class GroupHubPublisherService implements IPublisher {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupHubRepository groupHubRepository;

    /**
     * 默认验证在controller里面，到达这一层的时候已经是绝对合法的GroupHub了。
     * @param groupHub
     * @return
     */
    @Override
    public GroupHub publish(GroupHub groupHub) {
        groupHubRepository.save(groupHub);

        return groupHub;
    }

    /**
     * 暂时不写
     * @param groupHub
     * @return
     */
    @Override
    public GroupHub modify(GroupHub updatedGroupHub) {
        Optional<GroupHub> existingGroupHub = groupHubRepository.findById(updatedGroupHub.getId());
        if (existingGroupHub.isPresent()) {
            GroupHub existing = existingGroupHub.get();
            
            // 更新existing的属性
            existing.setName(updatedGroupHub.getName());
            existing.setQuantity(updatedGroupHub.getQuantity());
            // 更新其他需要修改的字段
            
            return groupHubRepository.save(existing);
        } else {
            throw new RuntimeException("未找到ID为" + updatedGroupHub.getId() + "的GroupHub");
        }
    }
    

    /**
     * 暂时不写
     * @param groupHubID
     */
    @Override
    public void deleteByGroupHubID(Long groupHubID) {
        boolean exists = groupHubRepository.existsById(groupHubID);
        if (exists) {
            groupHubRepository.deleteById(groupHubID);
        } else {
            throw new RuntimeException("未找到ID为" + groupHubID + "的GroupHub");
        }
    }

    /**
     * 查找user所有publisher项目
     * @param userId
     */
    @Override
    public List<GroupHub> findAllPublishedByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return new ArrayList<>(user.getPublishedGroupHubs());
    }

    
}
