desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('1234'), 'male', current_date());
insert into user values(null, '문상훈', 'bdns@gmail.com', password('1234'), 'male', current_date());

-- login
select no, name from user where email = 'moonjin0419@gmail.com' and password = password('1234');

-- test
select * from user;

-- role 추가
alter table user add column role enum('ADMIN', 'USER') not null default 'USER';
update user set role='ADMIN' where no = 1;

-- guestbook
insert into guestbook values(null, '문상훈', '1234', '내가 니 삼촌이다!', now());

desc guestbook;

select board.no, board.title, user.name as user_no, board.hit, board.reg_date from board inner join user on board.user_no = user.no order by g_no desc, o_no asc limit 0, 5;

insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values("집 가고 싶다..", "진짜 집 언제 가지? 과제는 언제 다하지? 오늘 축제 가야되는데!!!!", 0, now(), (select ifnull(max(g_no), 0) + 1 from board as subquery) ,1, 0, 2);
insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values("뉴진스!!", "How Sweet 진짜 좋음!!", 0, now(), (select ifnull(max(g_no), 0) + 1 from board as subquery) ,1, 0, 2);
insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values("동대축제 갈 사람~?", "1일차 진짜 좋았어서 2일차는 더더 기대됌", 0, now(), (select ifnull(max(g_no), 0) + 1 from board as subquery) ,1, 0, 2);
select no, title, hit, reg_date, contents, user_no from board where no = 9;
update board set title = '수정되라', contents = '제발', reg_date = now() where no = 9;

delete from board where no = 6;

update board set o_no = o_no + 1 where g_no = 7 and o_no > 2;
insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values('문당훈이에요', '조카다!!', 0, now(), 7, 2 ,1, 3);

select * from board;

select count(*) from board;