package edu.nus.iss.gdipsa.grouphub.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupHubRepository extends JpaRepository<GroupHub, Long> {
    // 通过名称查找GroupHub
    @Query("SELECT g FROM GroupHub g where g.name like %:name%")
    List<GroupHub> findByNameContaining(@Param("name") String name);

    // 按点赞数量降序排序查找所有GroupHub
    List<GroupHub> findAllByOrderByLikesDesc();
}
