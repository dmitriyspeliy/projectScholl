package ru.hogwarts.school.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.record.AvatarRecord;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T16:01:08+0700",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class AvatarMapperImpl implements AvatarMapper {

    @Override
    public Avatar toEntity(AvatarRecord avatarRecord) {
        if ( avatarRecord == null ) {
            return null;
        }

        Avatar avatar = new Avatar();

        avatar.setFilePath( avatarRecord.getFilePath() );
        avatar.setFileSize( avatarRecord.getFileSize() );
        avatar.setMediaType( avatarRecord.getMediaType() );
        byte[] data = avatarRecord.getData();
        if ( data != null ) {
            avatar.setData( Arrays.copyOf( data, data.length ) );
        }
        avatar.setStudentId( avatarRecord.getStudentId() );
        avatar.setStudent( avatarRecord.getStudent() );

        return avatar;
    }

    @Override
    public AvatarRecord toRecord(Avatar avatar) {
        if ( avatar == null ) {
            return null;
        }

        AvatarRecord avatarRecord = new AvatarRecord();

        avatarRecord.setId( avatar.getId() );
        avatarRecord.setFilePath( avatar.getFilePath() );
        avatarRecord.setFileSize( avatar.getFileSize() );
        avatarRecord.setMediaType( avatar.getMediaType() );
        byte[] data = avatar.getData();
        if ( data != null ) {
            avatarRecord.setData( Arrays.copyOf( data, data.length ) );
        }
        avatarRecord.setStudentId( avatar.getStudentId() );
        avatarRecord.setStudent( avatar.getStudent() );

        return avatarRecord;
    }

    @Override
    public Collection<Avatar> toEntityList(Collection<AvatarRecord> avatarRecord) {
        if ( avatarRecord == null ) {
            return null;
        }

        Collection<Avatar> collection = new ArrayList<Avatar>( avatarRecord.size() );
        for ( AvatarRecord avatarRecord1 : avatarRecord ) {
            collection.add( toEntity( avatarRecord1 ) );
        }

        return collection;
    }

    @Override
    public Collection<AvatarRecord> toRecordList(Collection<Avatar> avatar) {
        if ( avatar == null ) {
            return null;
        }

        Collection<AvatarRecord> collection = new ArrayList<AvatarRecord>( avatar.size() );
        for ( Avatar avatar1 : avatar ) {
            collection.add( toRecord( avatar1 ) );
        }

        return collection;
    }
}
