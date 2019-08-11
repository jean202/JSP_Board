
/* Drop Tables */

DROP TABLE freeboard CASCADE CONSTRAINTS;
DROP TABLE reviewboard CASCADE CONSTRAINTS;
DROP TABLE tipboard CASCADE CONSTRAINTS;
DROP TABLE account CASCADE CONSTRAINTS;
DROP TABLE file_info CASCADE CONSTRAINTS;




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


CREATE TABLE file_info
(
	-- 파일들을 식별하기 위한 id
	file_id number NOT NULL,
	systemname varchar2(50) NOT NULL,
	originalname varchar2(50) NOT NULL,
	fileseq number NOT NULL,
	PRIMARY KEY (file_id)
);


CREATE TABLE freeboard
(
	write_id number NOT NULL,
	-- 자유게시판 글 제목
	subject varchar2(50) NOT NULL,
	content clob NOT NULL,
	regdate date NOT NULL,
	viewcnt number NOT NULL,
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	-- 파일들을 식별하기 위한 id
	file_id number NOT NULL,
	PRIMARY KEY (write_id)
);


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
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	-- 파일들을 식별하기 위한 id
	file_id number NOT NULL,
	PRIMARY KEY (write_id)
);


CREATE TABLE tipboard
(
	-- 보드게시판의 게시글을 식별하기 위한 id
	write_id number NOT NULL,
	subject varchar2(50) NOT NULL,
	content clob NOT NULL,
	regdate date NOT NULL,
	viewcnt number NOT NULL,
	-- 회원을 식별해주는 id,식별값
	-- 
	acc_num number NOT NULL,
	-- 파일들을 식별하기 위한 id
	file_id number NOT NULL,
	PRIMARY KEY (write_id)
);



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


ALTER TABLE freeboard
	ADD FOREIGN KEY (file_id)
	REFERENCES file_info (file_id)
;


ALTER TABLE reviewboard
	ADD FOREIGN KEY (file_id)
	REFERENCES file_info (file_id)
;


ALTER TABLE tipboard
	ADD FOREIGN KEY (file_id)
	REFERENCES file_info (file_id)
;



/* Comments */

COMMENT ON COLUMN account.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN file_info.file_id IS '파일들을 식별하기 위한 id';
COMMENT ON COLUMN freeboard.subject IS '자유게시판 글 제목';
COMMENT ON COLUMN freeboard.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN freeboard.file_id IS '파일들을 식별하기 위한 id';
COMMENT ON COLUMN reviewboard.write_id IS '리뷰게시판에서 각각의 글을 식별하기 위한 id';
COMMENT ON COLUMN reviewboard.subject IS '리뷰게시판 글의 제목';
COMMENT ON COLUMN reviewboard.content IS '리뷰게시판의 게시글 내용
';
COMMENT ON COLUMN reviewboard.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN reviewboard.file_id IS '파일들을 식별하기 위한 id';
COMMENT ON COLUMN tipboard.write_id IS '보드게시판의 게시글을 식별하기 위한 id';
COMMENT ON COLUMN tipboard.acc_num IS '회원을 식별해주는 id,식별값
';
COMMENT ON COLUMN tipboard.file_id IS '파일들을 식별하기 위한 id';

select * from freeboard;

