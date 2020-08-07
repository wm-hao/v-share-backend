create schema king default character set utf8 collate utf8_general_ci;
create user 'king'@'localhost' identified  by 'king';
grant all privileges on king.* to 'king'@'localhost';
flush privileges ;
