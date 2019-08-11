
/* Drop Tables */

DROP TABLE freeboard CASCADE CONSTRAINTS;
DROP TABLE reviewboard CASCADE CONSTRAINTS;
DROP TABLE tipboard CASCADE CONSTRAINTS;
DROP TABLE account CASCADE CONSTRAINTS;
DROP TABLE file_info CASCADE CONSTRAINTS;
DROP TABLE boardid CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE account
(
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	id varchar2(20) NOT NULL,
	pw varchar2(20) NOT NULL,
	name varchar2(20) NOT NULL,
	email varchar2(50) NOT NULL,
	birth date NOT NULL,
	gender varchar2(10) NOT NULL,
	regdate date NOT NULL,
	nickname varchar2(20),
	PRIMARY KEY (acc_num)
);

DROP SEQUENCE account_seq;
CREATE SEQUENCE account_seq;

CREATE TABLE boardid
(
	-- 게시판을 식별하는 테이블의id
	board_id number NOT NULL,
	-- 각 게시판을 식별하기 위한 id
	board_name varchar2(20) NOT NULL,
	PRIMARY KEY (board_id)
);

DROP SEQUENCE boardid_seq;
CREATE SEQUENCE boardid_seq;

CREATE TABLE file_info
(
	-- 파일들을 식별하기 위한 id
	file_id number NOT NULL,
	systemname varchar2(50) NOT NULL,
	originalname varchar2(50) NOT NULL,
	fileseq number NOT NULL,
	-- 어떤 게시판의 파일인지 식별하기 위해서, 게시판 번호를 참조하고, 참조한 게시판 번호에 해당하는 게시판을 다시 참조
	boardid_id number NOT NULL,
	PRIMARY KEY (file_id)
);
DROP SEQUENCE file_info_seq;
CREATE SEQUENCE file_info_seq;
DROP SEQUENCE file_id_seq;
CREATE SEQUENCE file_id_seq;


CREATE TABLE freeboard
(
	write_id number NOT NULL,
	-- 자유게시판 글 제목
	subject varchar2(50) NOT NULL,
	content clob NOT NULL,
	regdate date NOT NULL,
	viewcnt number NOT NULL,
	-- 게시판을 식별하는 테이블의id
	board_id number NOT NULL,
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	PRIMARY KEY (write_id)
);
DROP SEQUENCE freeboard_seq;
CREATE SEQUENCE freeboard_seq;

CREATE TABLE reviewboard
(
	-- 리뷰게시판에서 각각의 글을 식별하기 위한 id
	write_id number NOT NULL,
	-- 리뷰게시판 글의 제목
	subject varchar2(50) NOT NULL,
	-- 리뷰게시판의 게시글 내용
	-- 
	content clob NOT NULL,
	rating number NOT NULL,
	regdate date NOT NULL,
	viewcnt number NOT NULL,
	-- 게시판을 식별하는 테이블의id
	board_id number NOT NULL,
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	PRIMARY KEY (write_id)
);
DROP SEQUENCE reviewboard_seq;
CREATE SEQUENCE reviewboard_seq;

CREATE TABLE tipboard
(
	-- 보드게시판의 게시글을 식별하기 위한 id
	write_id number NOT NULL,
	subject varchar2(50) NOT NULL,
	content clob NOT NULL,
	regdate date NOT NULL,
	viewcnt number NOT NULL,
	-- 게시판을 식별하는 테이블의id
	board_id number NOT NULL,
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	PRIMARY KEY (write_id)
);
DROP SEQUENCE tipboard_seq;
CREATE SEQUENCE tipboard_seq;


/* Create Foreign Keys */

ALTER TABLE freeboard
	ADD FOREIGN KEY (acc_num)
	REFERENCES account (acc_num)
;


ALTER TABLE reviewboard
	ADD FOREIGN KEY (acc_num)
	REFERENCES account (acc_num)
;


ALTER TABLE tipboard
	ADD FOREIGN KEY (acc_num)
	REFERENCES account (acc_num)
;


ALTER TABLE file_info
	ADD FOREIGN KEY (boardid_id)
	REFERENCES boardid (board_id)
;


ALTER TABLE freeboard
	ADD FOREIGN KEY (board_id)
	REFERENCES boardid (board_id)
;


ALTER TABLE reviewboard
	ADD FOREIGN KEY (board_id)
	REFERENCES boardid (board_id)
;


ALTER TABLE tipboard
	ADD FOREIGN KEY (board_id)
	REFERENCES boardid (board_id)
;



/* Comments */

COMMENT ON COLUMN account.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN boardid.board_id IS '게시판을 식별하는 테이블의id';
COMMENT ON COLUMN boardid.board_name IS '각 게시판을 식별하기 위한 id';
COMMENT ON COLUMN file.file_id IS '파일들을 식별하기 위한 id';
COMMENT ON COLUMN file.boardid_id IS '어떤 게시판의 파일인지 식별하기 위해서, 게시판 번호를 참조하고, 참조한 게시판 번호에 해당하는 게시판을 다시 참조';
COMMENT ON COLUMN freeboard.subject IS '자유게시판 글 제목';
COMMENT ON COLUMN freeboard.board_id IS '게시판을 식별하는 테이블의id';
COMMENT ON COLUMN freeboard.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN reviewboard.write_id IS '리뷰게시판에서 각각의 글을 식별하기 위한 id';
COMMENT ON COLUMN reviewboard.subject IS '리뷰게시판 글의 제목';
COMMENT ON COLUMN reviewboard.content IS '리뷰게시판의 게시글 내용
';
COMMENT ON COLUMN reviewboard.board_id IS '게시판을 식별하는 테이블의id';
COMMENT ON COLUMN reviewboard.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN tipboard.write_id IS '보드게시판의 게시글을 식별하기 위한 id';
COMMENT ON COLUMN tipboard.board_id IS '게시판을 식별하는 테이블의id';
COMMENT ON COLUMN tipboard.acc_num IS '회원을 식별해주는 id,식별값
';

SELECT * FROM account;
select * from BOARDID;
select * from freeboard;
INSERT INTO account values(account_seq.nextval,'a','a','a','a',sysdate,'a',sysdate,'a');
INSERT INTO freeboard(write_id, subject, content, regdate, viewcnt,  board_id, acc_num) 
	 VALUES(freeboard_seq.nextval, 'test3', 'test3', sysdate ,0, 2 , 1);
select acc_num from account where name = 'a';



select * from freeboard f, boardid b WHERE f.board_id = b.board_id;

--컬럼명이 틀린경우 , 테이블명이 틀린경우
select fi.file_id, b.board_id, fb.write_id
from FILE_INFO fi , boardid b, freeboard fb
where fi.BOARDID_ID. = b.board_id and
b.board_id = fb.board_id; 

(select * 
		FROM freeboard fb, boardid b 
		WHERE fb.board_id = b.board_id 
		AND fb.write_id=13) 
 

