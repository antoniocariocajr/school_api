package com.school.services;

import com.school.controllers.dto.user.CreateUserRequest;
import com.school.persistence.entities.User;

public interface UserService {
    User createUser(CreateUserRequest request);
}
