package dev.aleksandarboev.rollplangamebe.features.character;

import dev.aleksandarboev.rollplangamebe.features.character.repository.CharacterEntity;
import dev.aleksandarboev.rollplangamebe.features.character.repository.CharacterRepository;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterCreateRequest;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterResponse;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;

    public List<CharacterResponse> getCharacters(Long userId) {
        Optional<UserEntity> userEntityWithCharacters = userRepository.findByIdAndFetchAllCharacters(userId);
        if (userEntityWithCharacters.isPresent()) {
            return userEntityWithCharacters.get().getCharacters()
                    .stream()
                    .map(this::getCharacterCreateResponse)
                    .toList();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public CharacterResponse createCharacter(CharacterCreateRequest characterCreateRequest, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        CharacterEntity characterEntity = getCharacterEntity(characterCreateRequest, user);

        characterRepository.save(characterEntity);

        return getCharacterCreateResponse(characterEntity);
    }

    private static CharacterEntity getCharacterEntity(CharacterCreateRequest characterCreateRequest, UserEntity user) {
        return CharacterEntity.builder()
                .name(characterCreateRequest.name())
                .race(characterCreateRequest.race())
                .gender(characterCreateRequest.gender())
                .strength(characterCreateRequest.strength())
                .dexterity(characterCreateRequest.dexterity())
                .constitution(characterCreateRequest.constitution())
                .intelligence(characterCreateRequest.intelligence())
                .wisdom(characterCreateRequest.wisdom())
                .charisma(characterCreateRequest.charisma())
                .user(user)
                .build();
    }

    private CharacterResponse getCharacterCreateResponse(CharacterEntity characterEntity) {
        return CharacterResponse.builder()
                .id(characterEntity.getId().toString())
                .name(characterEntity.getName())
                .race(characterEntity.getRace())
                .gender(characterEntity.getGender())
                .strength(characterEntity.getStrength())
                .dexterity(characterEntity.getDexterity())
                .constitution(characterEntity.getConstitution())
                .intelligence(characterEntity.getIntelligence())
                .wisdom(characterEntity.getWisdom())
                .charisma(characterEntity.getCharisma())
                .build();
    }
}
