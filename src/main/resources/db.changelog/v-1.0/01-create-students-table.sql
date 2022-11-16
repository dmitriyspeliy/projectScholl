create table if not exists students
(
    student_id   integer generated always as identity primary key,
    student_name varchar(256) unique not null,
    student_age  integer             not null CHECK (student_age > 15) default 20,
    faculty_id   integer             not null,
    constraint fk_faculty_id foreign key (faculty_id) references faculties (faculty_id)

);








