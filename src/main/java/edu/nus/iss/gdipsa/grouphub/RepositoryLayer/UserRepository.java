package edu.nus.iss.gdipsa.grouphub.RepositoryLayer;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
