package service;

import java.util.List;

import org.apache.catalina.connector.Response;

import dao.ReplyDAO;
import dto.Reply;

public class ReplyService {
	private ReplyDAO rdao = new ReplyDAO();
	public void insert(Reply reply) {
		//댓글의 순서(restep)
		reply.setRestep(reply.getRestep()+1);
		//댓글의 레벨(relevel)
		reply.setRelevel(reply.getRelevel()+1);
		
		//기존댓글의 순서를 수정
		rdao.update_restep(reply);
		
		int cnt = rdao.insert(reply);
		System.out.println(cnt+"건 추가");
	}
	public List<Reply> selectList(int bnum) {
		return rdao.selectList(bnum);
		
	}
	public void delete(int rnum) {
		int cnt = rdao.delete(rnum);
		System.out.println(cnt+"건 댓글 삭제");
		
		
		
	}
	public Reply selectOne(int rnum) {
		
		return rdao.selectOne(rnum);
	}
	public void update(Reply reply) {
		int cnt =  rdao.update(reply);
		System.out.println(cnt+"건 수정");
	}
	
	
	
}
