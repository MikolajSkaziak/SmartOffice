package com.example.smartofficeapi.services;

import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.repository.interfaces.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    @Transactional
    public void deleteUser(Long userId) {
        if (userId!=null) {
            userRepository.deleteById(userId);
        }
        else {
            throw new NullPointerException("Nie znaleziono użytkownika");
        }

    }
    @Transactional
    public void createUser(){

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
