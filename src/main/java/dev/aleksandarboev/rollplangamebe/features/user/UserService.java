package dev.aleksandarboev.rollplangamebe.features.user;

import dev.aleksandarboev.rollplangamebe.configuration.userauthentication.UserAuthenticationProvider;
import dev.aleksandarboev.rollplangamebe.configuration.userauthentication.UserJwtDto;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserRepository;
import dev.aleksandarboev.rollplangamebe.features.user.web.UserRegistrationRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    public UserService(UserRepository userRepository, UserAuthenticationProvider userAuthenticationProvider) {
        this.userRepository = userRepository;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        UserEntity userEntity = mapToUserEntity(userRegistrationRequest);
        userRepository.save(userEntity);
        String jwtToken = userAuthenticationProvider.createToken(getUserJwtDto(userEntity));
        return new UserRegistrationResponse(userEntity.getId().toString(), jwtToken);
    }

    private UserEntity mapToUserEntity(UserRegistrationRequest userRegistrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationRequest.username());
        userEntity.setPassword(userRegistrationRequest.password());
        return userEntity;
    }

    private UserJwtDto getUserJwtDto(UserEntity userEntity) {
        return new UserJwtDto(userEntity.getId().toString());
    }
}
