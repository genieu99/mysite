-- insert
desc site;
insert into site values(null, 'MySite', '안녕하세요. ""문유진""의 삼촌 문상훈의 mysite에 오신 것을 환영합니다.', '/assets/images/moonsanghoon.jpeg', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n메뉴는 사이트 소개, 방명록, 게시판이 있구요. Python 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.');

select * from site;

select title, welcome, profile, description from site order by no asc limit 0, 1;
update site set title = "MOON", welcome = "하이루", profile = "ㅋㅋㅋ", description = "test" where no = 1;