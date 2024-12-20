package dev.aleksandarboev.rollplangamebe.features.character.repository;

import dev.aleksandarboev.rollplangamebe.features.user.repository.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "characters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String race;

    @Column(nullable = false)
    private String gender;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer strength;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer dexterity;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer constitution;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer intelligence;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer wisdom;

    @Column(columnDefinition = "SMALLINT", nullable = false)
    private Integer charisma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
