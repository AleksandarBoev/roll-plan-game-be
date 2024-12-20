package dev.aleksandarboev.rollplangamebe.features.character.web;

import dev.aleksandarboev.rollplangamebe.configuration.userauthentication.UserJwtDto;
import dev.aleksandarboev.rollplangamebe.features.character.CharacterService;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterCreateRequest;
import dev.aleksandarboev.rollplangamebe.features.character.web.models.CharacterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<CharacterResponse>> getCharacters() {
        return new ResponseEntity<>(characterService.getCharacters(getUserId()), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CharacterResponse> createCharacter(@RequestBody final CharacterCreateRequest request) {
        CharacterResponse response = characterService.createCharacter(request, getUserId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable final String id) {
        boolean deleted = characterService.deleteCharacter(getUserId(), Long.parseLong(id));

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Long getUserId() {
        UserJwtDto userJwtDto = (UserJwtDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(userJwtDto.id());
    }
}
