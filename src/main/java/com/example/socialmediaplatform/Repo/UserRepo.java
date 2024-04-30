package com.example.socialmediaplatform.Repo;


import com.example.socialmediaplatform.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {
    public Users findByEmail(String email);
}
