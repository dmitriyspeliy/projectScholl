create table if not exists avatar
(
    avatar_id integer generated always as identity primary key,
    avatar_filePath varchar(256) not null,
    avatar_fileSize integer not null,
    avatar_mediaType varchar(256) not null,
    avatar_data oid not null,
    studentId bigint not null
)









