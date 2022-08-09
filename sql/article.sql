drop table exists article;

create table article(
    id int unsigned not null primary key auto_increment,
    title varchar(100) not null,
    body text not null
);

insert into article(title, body) values('제목', '내용');
insert into article(title, body) values('제목', '내용');

select * from article;