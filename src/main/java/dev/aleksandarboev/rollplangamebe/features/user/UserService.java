package dev.aleksandarboev.rollplangamebe.features.user;

import dev.aleksandarboev.rollplangamebe.configuration.userauthentication.UserAuthenticationProvider;
import dev.aleksandarboev.rollplangamebe.configuration.userauthentication.UserJwtDto;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserRepository;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserLoginRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserLoginResponse;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserRegistrationRequest;
import dev.aleksandarboev.rollplangamebe.features.user.web.models.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<UserLoginResponse> loginUser(UserLoginRequest userLoginRequest) {
        Optional<UserEntity> userFound = userRepository.findByUsername(userLoginRequest.username());

        if (userFound.isPresent()) {
            if (userFound.get().getPassword().equals(userLoginRequest.password())) {
                String jwtToken = userAuthenticationProvider.createToken(getUserJwtDto(userFound.get()));
                return Optional.of(new UserLoginResponse(userFound.get().getId().toString(), jwtToken));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
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
