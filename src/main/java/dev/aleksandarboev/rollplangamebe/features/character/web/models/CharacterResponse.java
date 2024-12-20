package dev.aleksandarboev.rollplangamebe.features.character.web.models;

import lombok.Builder;

@Builder
public record CharacterResponse(String id,
                                String name,
                                String race,
                                String gender,
                                Integer strength,
                                Integer dexterity,
                                Integer constitution,
                                Integer intelligence,
                                Integer wisdom,
                                Integer charisma) {
}
