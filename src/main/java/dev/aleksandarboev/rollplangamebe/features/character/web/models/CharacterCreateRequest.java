package dev.aleksandarboev.rollplangamebe.features.character.web.models;

public record CharacterCreateRequest(String name,
                                     String race,
                                     String gender,
                                     Integer strength,
                                     Integer dexterity,
                                     Integer constitution,
                                     Integer intelligence,
                                     Integer wisdom,
                                     Integer charisma) {
}
