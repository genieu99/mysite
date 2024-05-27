desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('1234'), 'male', current_date());

-- login
select no, name from user where email = 'moonjin0419@gmail.com' and password = password('1234');

-- test
select * from user;

-- guestbook
insert into guestbook values(null, '문상훈', '1234', '내가 니 삼촌이다!', now());

desc guestbook;