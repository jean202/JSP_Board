package com.lec.beans;

// DTO : Data Transfer Object
// freeboard 테이블을 표현할 빈 객체 정의
public class WriteDTO {
	// Q. name? acc_num?
	private int write_id;    //  wr_id
	private String subject;  // wr_subject
	private String content;  // wr_content
	private String name;     // wr_name 작성하는 사람 이름
	private int viewCnt;  	 // wr_viewcnt
	private String regDate;  // wr_regdate
	private int acc_num; 	 // 작성자 번호
	private String fileSystemName; 
	private String originalFileName;
	
	
	// 웹개발시...
	// 가능한, 다음 3가지는 이름을 일치시켜주는게 좋습니다.
	// 클래스 필드명 = DB 필드명 = form의 name명

	public String getFileSystemName() {
		return fileSystemName;
	}

	public void setFileSystemName(String fileSystemName) {
		this.fileSystemName = fileSystemName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	// 생성자
	public WriteDTO() {
		super();
		System.out.println("WriteDTO() 객체 생성");
	}
	
	public WriteDTO(int write_id, String subject, String content,int viewCnt, 
			int acc_num) {
		super();
		this.write_id = write_id;
		this.subject = subject;
		this.content = content;
		this.viewCnt = viewCnt;		
		this.acc_num = acc_num;
		System.out.println("WriteDTO(write_id, subject, content, name, viewcnt, regdate, acc_num) 객체 생성");
	}

	public int getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(int acc_num) {
		this.acc_num = acc_num;
	}

	public int getWrite_id() {
		return write_id;
	}

	public void setWrite_id(int write_id) {
		this.write_id = write_id;
	}

	
	
	public int getId() {
		System.out.println("getId() 호출");
		return write_id;
	}

	public void setId(int write_id) {
		System.out.println("setId(" + write_id + ") 호출");
		this.write_id = write_id;
	}

	public String getSubject() {
		System.out.println("getSubject() 호출");
		return subject;
	}

	public void setSubject(String subject) {
		System.out.println("setSubject(" + subject + ") 호출");
		this.subject = subject;
	}

	public String getContent() {
		System.out.println("getContent() 호출");
		return content;
	}

	public void setContent(String content) {
		System.out.println("setContent(" + content + ") 호출");
		this.content = content;
	}

	public String getName() {
		System.out.println("getName() 호출");
		return name;
	}

	public void setName(String name) {
		System.out.println("setName(" + name + ") 호출");
		this.name = name;
	}

	public int getViewCnt() {
		System.out.println("getViewCnt() 호출");
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		System.out.println("setViewCnt(" + viewCnt + ") 호출");
		this.viewCnt = viewCnt;
	}

	public String getRegDate() {
		System.out.println("getRegDate() 호출");
		return regDate;
	}

	public void setRegDate(String regDate) {
		System.out.println("setRegDate(" + regDate + ") 호출");
		this.regDate = regDate;
	}
	
	// 개발할때 Class의 toString()을 오버라이딩 해주면
	// 디버깅이나 테스팅하기 좋다.
	@Override
	public String toString() {
		return "WriteDTO] " + write_id + ":" + subject + ":" + content + ":" + name + ":" + viewCnt + ":" + regDate; 
	} // end toString()
} // end DTO class


