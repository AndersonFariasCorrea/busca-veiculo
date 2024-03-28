CREATE TABLE user(
    id int unsigned not null auto_increment primary key,
    first_name varchar(125) not null,
    last_name varchar(125) not null,
    email text not null,
    role int unsigned not null default 0
);