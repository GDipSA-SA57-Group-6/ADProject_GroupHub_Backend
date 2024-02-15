package edu.nus.iss.gdipsa.grouphub.ModelLayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_hubs")
public class GroupHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private int initialQuantity;
    private int likes;

    /**
     * 暂时不使用
     */
    @Column(name = "is_depend_on_quantity")
    private boolean isDependOnQuantity;

    private double latitude;
    private double longitude;

    /**
     * 暂时不使用
     */
    @Column(name = "is_depend_on_location")
    private boolean isDependOnLocation;

    private LocalDate startTime;
    private LocalDate endTime;


    /**
     * 暂时不使用
     */
    @Column(name = "is_depend_on_time")
    private boolean isDependOnTime;

    @ManyToOne
    private User publishedBy;

    @JsonIgnore
    @ManyToMany
    private Set<User> hasUsers;

    public GroupHub() {
        hasUsers = new HashSet<>();
    }


    // ... other getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void incrementLikes() {
        this.likes++;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "GroupHub{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", likes=" + likes +
                ", isDependOnQuantity=" + isDependOnQuantity +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isDependOnLocation=" + isDependOnLocation +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isDependOnTime=" + isDependOnTime +
                '}';
    }

    public void setConfirmed(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setConfirmed'");
    }

    public void setCancelled(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCancelled'");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isDependOnQuantity() {
        return isDependOnQuantity;
    }

    public void setDependOnQuantity(boolean dependOnQuantity) {
        isDependOnQuantity = dependOnQuantity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDependOnLocation() {
        return isDependOnLocation;
    }

    public void setDependOnLocation(boolean dependOnLocation) {
        isDependOnLocation = dependOnLocation;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public boolean isDependOnTime() {
        return isDependOnTime;
    }

    public void setDependOnTime(boolean dependOnTime) {
        isDependOnTime = dependOnTime;
    }

    public User getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(User publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Set<User> getHasUsers() {
        return hasUsers;
    }

    public void setHasUsers(Set<User> hasUsers) {
        this.hasUsers = hasUsers;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }
}
