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

select board.no, board.title, user.no as user_no, board.hit, board.reg_date from board, user order by g_no desc, o_no asc limit 5, 5;

insert into board(title, contents, reg_date, g_no, o_no, depth) values("집 가고 싶다..", "진짜 집 언제 가지? 과제는 언제 다하지? 오늘 축제 가야되는데!!!!", now(), (select coalesce(max(g_no), 0) + 1 from board) ,1, 0);