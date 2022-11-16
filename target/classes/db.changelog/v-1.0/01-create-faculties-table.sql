create table if not exists faculties
(
    faculty_id    integer generated always as identity primary key,
    faculty_name  varchar(256) not null,
    faculty_color varchar(32)  not null,
    constraint nameAndColor_unique UNIQUE (faculty_name, faculty_color)
);