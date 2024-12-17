package dev.aleksandarboev.rollplangamebe.features.user;

import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserRepository;
import dev.aleksandarboev.rollplangamebe.features.user.web.UserRegistrationRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        UserEntity userEntity = mapToUserEntity(userRegistrationRequest);
        userRepository.save(userEntity);
        return new UserRegistrationResponse(userEntity.getUsername(), "If you see this in PostMan, then it worked!");
    }

    private UserEntity mapToUserEntity(UserRegistrationRequest userRegistrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationRequest.username());
        userEntity.setPassword(userRegistrationRequest.password());
        return userEntity;
    }
}
