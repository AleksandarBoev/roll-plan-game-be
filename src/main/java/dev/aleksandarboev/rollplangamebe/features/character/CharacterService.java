package dev.aleksandarboev.rollplangamebe.features.character;

import dev.aleksandarboev.rollplangamebe.features.character.repository.CharacterEntity;
import dev.aleksandarboev.rollplangamebe.features.character.repository.CharacterRepository;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterCreateRequest;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterEditRequest;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterResponse;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import dev.aleksandarboev.rollplangamebe.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean deleteCharacter(Long userId, Long characterId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return false;
        }

        Optional<CharacterEntity> characterEntity = characterRepository.findById(characterId);
        if (characterEntity.isEmpty()) {
            return false;
        }

        user.get().removeCharacter(characterEntity.get());
        userRepository.save(user.get());
        return true;
    }

    public boolean editCharacter(CharacterEditRequest characterEditRequest) {
        Optional<CharacterEntity> characterEntity = characterRepository.findById(characterEditRequest.id());
        if (characterEntity.isEmpty()) {
            return false;
        }

        editCharacterEntity(characterEntity.get(), characterEditRequest);
        characterRepository.save(characterEntity.get());
        return true;
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

    private void editCharacterEntity(CharacterEntity characterEntity, CharacterEditRequest characterEditRequest) {
        characterEntity.setName(characterEditRequest.name());
        characterEntity.setGender(characterEditRequest.gender());
        characterEntity.setRace(characterEditRequest.race());
        characterEntity.setStrength(characterEditRequest.strength());
        characterEntity.setDexterity(characterEditRequest.dexterity());
        characterEntity.setConstitution(characterEditRequest.constitution());
        characterEntity.setIntelligence(characterEditRequest.intelligence());
        characterEntity.setWisdom(characterEditRequest.wisdom());
        characterEntity.setCharisma(characterEditRequest.charisma());
    }
}
