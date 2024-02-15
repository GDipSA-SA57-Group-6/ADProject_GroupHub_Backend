package edu.nus.iss.gdipsa.grouphub.ModelLayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @JsonIgnore
    @OneToMany(mappedBy = "publishedBy", cascade = CascadeType.ALL)
    private List<GroupHub> publishedGroupHubs;

    @JsonIgnore
    @ManyToMany(mappedBy = "hasUsers")
    private Set<GroupHub> belongsToGroupHubs;

    public User() {
        publishedGroupHubs = new ArrayList<>();
        belongsToGroupHubs = new HashSet<>();
    }
    // Getters and setters for all the fields


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<GroupHub> getPublishedGroupHubs() {
        return publishedGroupHubs;
    }

    public void setPublishedGroupHubs(List<GroupHub> publishedGroupHubs) {
        this.publishedGroupHubs = publishedGroupHubs;
    }

    public Set<GroupHub> getBelongsToGroupHubs() {
        return belongsToGroupHubs;
    }

    public void setBelongsToGroupHubs(Set<GroupHub> belongsToGroupHubs) {
        this.belongsToGroupHubs = belongsToGroupHubs;
    }
    
}