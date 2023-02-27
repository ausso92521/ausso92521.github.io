package tech.leftii.animaltracker.shared;

import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.leftii.animaltracker.animal.AnimalDto;
import tech.leftii.animaltracker.animal.AnimalEntity;
import tech.leftii.animaltracker.note.NoteDto;
import tech.leftii.animaltracker.note.NoteEntity;
import tech.leftii.animaltracker.role.RoleDto;
import tech.leftii.animaltracker.role.RoleEntity;
import tech.leftii.animaltracker.user.NewUserDto;
import tech.leftii.animaltracker.user.ResponseUserDto;
import tech.leftii.animaltracker.user.UpdateUserDto;
import tech.leftii.animaltracker.user.UserEntity;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = DateTimeFormatter.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataMapper {

    /**
     * Converts NewUserDto to UserEntity
     *
     * @param newUserDto Data provided from client
     * @return Entity mapped from DTO
     */
    @Mapping(target = "password", qualifiedByName = "encode")
    UserEntity convert(NewUserDto newUserDto, @Context PasswordEncoder encoder);

    /**
     * Converts UserEntity to ResponseUserDto
     *
     * @param user Entity from Database
     * @return DTO mapped from Entity
     */
    ResponseUserDto convert(UserEntity user);

    /**
     * Updates UserEntity using UpdateUserDto
     * Password is ignored because we use @BeforeMapping to encode and map the encoded password to the entity
     * Without using @Mapping(target = "password", ignore = true) the plaintext password would overwrite the encoded one
     *
     * @param updateUserDto Dto submitted by client with values to update
     * @param user          Entity to be updated
     */
    @Mapping(target = "password", source = "newPassword", qualifiedByName = "encode",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "email", source = "email",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "firstName", source = "firstName",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "lastName", source = "lastName",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "phone", source = "phone",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    UserEntity convert(UpdateUserDto updateUserDto, @MappingTarget UserEntity user, @Context PasswordEncoder encoder);

    /**
     * This method is called when the convert passwords when converting to entities
     * This encodes the password prior to mapping to the UserEntity
     *
     * @param password Password from dto to be encoded
     * @param encoder  Injecting PasswordEncoder bean to encode the password before mapping
     */
    @Named(value = "encode")
    default String encodePassword(String password, @Context PasswordEncoder encoder) {
        return password != null ? encoder.encode(password) : null;
    }

    /**
     * Converts RoleDto to RoleEntity
     *
     * @param roleDto Data provided from client
     * @return Entity mapped from DTO
     */
    RoleEntity convert(RoleDto roleDto);

    /**
     * Converts RoleEntity to RoleDto
     *
     * @param role Entity from Database
     * @return DTO mapped from Entity
     */
    RoleDto convert(RoleEntity role);

    /**
     * Converts NoteDto to NoteEntity
     *
     * @param noteDto Data provided from client
     * @return Entity mapped from DTO
     */
    @Mapping(target = "animal.id", source = "animalId")
    NoteEntity convert(NoteDto noteDto);

    /**
     * Converts NoteEntity to NoteDto
     *
     * @param note Entity from Database
     * @return DTO mapped from Entity
     */
    @Mapping(target = "noteDateAsLong", expression = "java(note.getNoteDate() != null ? Long.parseLong(note.getNoteDate().format(DateTimeFormatter.ofPattern(\"yyyyMMddHHmm\"))) : null)")
    @Mapping(source = "animal.id", target = "animalId")
    NoteDto convert(NoteEntity note);

    /**
     * Updates and existing note with the information supplied in the NoteDto
     *
     * @param updateNote NoteDto with new values to update NoteEntity with
     * @param note NoteEntity to be updated
     * @return NoteEntity after updates have been applied
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "animal", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "noteDate", source = "noteDate")
    @Mapping(target = "content", source = "content",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NoteEntity convert(NoteDto updateNote, @MappingTarget NoteEntity note);
    /**
     * Converts AnimalDto to AnimalEntity
     *
     * @param animalDto Data provided from client
     * @return Entity mapped from DTO
     */
    AnimalEntity convert(AnimalDto animalDto);

    /**
     * Converts AnimalEntity to AnimalDto
     *
     * @param animal Entity from Database
     * @return DTO mapped from Entity
     */
    AnimalDto convert(AnimalEntity animal);
}


