package org.example.service;

import org.example.aspect.TrackUserAction;
import org.example.model.User;
import org.example.repositories.UserRepo;
import org.example.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log
public class UserService {
    private final UserRepo userRepo;
    public List<User> findAll(){
        return userRepo.findAll();
    }
    @TrackUserAction
    public User saveUser(User user){
        return userRepo.save(user);
    }
    @TrackUserAction
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }

    public User findById(Long id) {
        return userRepo.getById(id);
    }
    @TrackUserAction
    public User update(User user) {
        Optional<User> optionalUser = userRepo.findById(user.getId());
        if (optionalUser.isPresent()) {
            User user1 = optionalUser.get();
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            return userRepo.save(user1);
        } else {
            throw new IllegalArgumentException("Book not found with id: " + user.getId());
        }
    }
}
