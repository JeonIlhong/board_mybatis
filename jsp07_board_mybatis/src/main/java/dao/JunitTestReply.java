package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;


import dto.Reply;

class JunitTestReply {
	private ReplyDAO rdao = new ReplyDAO();
	@Test
	void insert() {
		Reply reply = new Reply();
		
		reply.setBnum(50);
		reply.setContent("댓글내용테스트");
		reply.setRelevel(1);
		reply.setRestep(1);
		
		int cnt = rdao.insert(reply);
		System.out.println(cnt + "건 추가");
	}
	
	@Test
	void update() {
		Reply reply = new Reply();
		reply.setContent("댓글수정테스트2");
		reply.setRnum(1);
		
		int cnt = rdao.update(reply);
		System.out.println(cnt + "건 수정");
	}
	
	@Test
	void delete() {
		Reply reply = new Reply();
		int cnt = rdao.delete(5);
		System.out.println(cnt + "건 삭제");
	}
	
	@Test
	void selectOne() {
		Reply reply = rdao.selectOne(1);
		System.out.println(reply);
	}
	
	@Test
	void selectList() {
//		Map<String,Object> findmap = new HashMap<>();
//		findmap.put("findkey","bnum" );
//		findmap.put("findvalue", 100);
		
		List<Reply> rlist = rdao.selectList(100);
		System.out.println(rlist);
	}
	
	
	
}
