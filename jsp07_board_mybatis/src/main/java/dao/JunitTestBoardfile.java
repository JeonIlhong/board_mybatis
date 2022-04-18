package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dto.Boardfile;

class JunitTestBoardfile {
	private BoardfileDAO bfdao = new BoardfileDAO();
	@Test
	void BFinsertTest() {
		Boardfile boardfile = new Boardfile();
		boardfile.setBnum(1);
		boardfile.setFilename("파일제목1");
		
		int cnt = bfdao.insert(boardfile);
		System.out.println(cnt+"건 추가");
	}
	
	@Test
	void BFupdateTest() {
		Boardfile boardfile = new Boardfile();
		boardfile.setBfnum(1);
		boardfile.setBnum(1);
		boardfile.setFilename("파일제목수정1");
		
		int cnt = bfdao.update(boardfile);
		System.out.println(cnt+"건 수정");
	}
	
	@Test
	void BFdeleteTest() {
		int cnt = bfdao.delete(4);
		System.out.println(cnt+"건 삭제");
	}
	
	@Test
	void BFselectOneTest() {
	   Boardfile boardfile = bfdao.selectOne(5);
	   System.out.println(boardfile);
	  }
	
	@Test
	void BFselectListTest() {
		Map<String,Object> findmap = new HashMap<>();
		findmap.put("findkey", "filename");
		findmap.put("findvalue", "제목");
		
//		List<Boardfile> bflist = bfdao.selectList(findmap);
//		System.out.println(bflist);
	  }
	

}
