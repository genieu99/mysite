desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('1234'), 'male', current_date());

-- test
select * from user;

-- guestbook
insert into guestbook values(null, '문상훈', '1234', '내가 니 삼촌이다!', now());

desc guestbook;