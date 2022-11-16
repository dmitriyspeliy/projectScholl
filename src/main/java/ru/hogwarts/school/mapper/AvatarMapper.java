package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.record.AvatarRecord;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface AvatarMapper {
    @Mapping(target = "id", ignore = true)
    Avatar toEntity(AvatarRecord avatarRecord);

    AvatarRecord toRecord(Avatar avatar);

    Collection<Avatar> toEntityList(Collection<AvatarRecord> avatarRecord);

    Collection<AvatarRecord> toRecordList(Collection<Avatar> avatar);
}
