package edu.nus.iss.gdipsa.grouphub.InterfaceLayer;

import java.util.List;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;

public interface ISubscriber {
    /**
     * 根据name搜索GroupHub
     * @param name
     * @return
     */
    List<GroupHub> searchByName(String name);

    /**
     * 根据点赞数返回GroupHub
     * @return
     */
    List<GroupHub> sortByPopularity();

    /**
     * 根据用户当前距离 返回GroupHub
     * @return
     */
    List<GroupHub> sortByDistance(double latitude, double longitude);

    /**
     * 用户确认加入一个事件
     * @param userId
     * @param groupId
     */
    boolean eventConfirm(Integer userId, long groupId);

    /**
     * 用户取消一个事件
     * @param userId
     * @param groupId
     * @return
     */
    boolean eventCancel(Integer userId, long groupId);

    /**
     * 查找用户所有订阅的GroupHub
     * @param userId
     * @return
     */
    public List<GroupHub> findAllSubscribedByUser(Integer userId);

    void ec_Like(Integer userId, long groupId);
}

