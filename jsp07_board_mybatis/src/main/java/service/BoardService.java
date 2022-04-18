package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDAO;
import dao.BoardfileDAO;
import dao.ReplyDAO;
import dto.Board;
import dto.Boardfile;

public class BoardService {
	private BoardDAO bdao = new BoardDAO();
	private BoardfileDAO bfdao = new BoardfileDAO();
	private ReplyDAO rdao = new ReplyDAO();
	
	public int insert(Board board,List<String> filenames) {
		
		int cnt = bdao.insert(board);
		System.out.println(cnt+"건 board 추가");
		
		//게시물 파일 저장 
		// bnum은 board저장시 생성(boardMapper에서 selectKey로 세팅)
		for(String filename:filenames) {
			Boardfile boardfile = new Boardfile();
			boardfile.setBnum(board.getBnum());
			boardfile.setFilename(filename);
			cnt += bfdao.insert(boardfile);
		}
		
		System.out.println(cnt+"건 boardfile 추가");
		return cnt;
	}

	public List<Board> selectList(Map<String,Object> findmap) {
		
		//페이징처리
		
		int perPage = 10; // 한 페이지의 게시물 수
		int curPage = (int)findmap.get("curPage");  // 현재페이지
		int startnum = (curPage-1)*perPage +1; //시작번호
		int endnum =startnum + perPage -1 ; //끝번호
		findmap.put("startnum", startnum);
		findmap.put("endnum", endnum);
		
		//전체 게시물수
		 
		int totcnt = bdao.select_totalcnt(findmap);
		int totPage = totcnt / perPage;
		if(totcnt % perPage > 0) totPage++; //나머지가 있으면 +1
		findmap.put("totPage", totPage);
		
		
		
		//페이징블럭 처리
		int perBlock = 10;
		int startPage = curPage - ((curPage-1)%perBlock);
		int endPage = startPage + perBlock -1 ;
		
		
		//endPage수정
	    if(endPage>totPage) endPage=totPage;
		
	    findmap.put("startPage",startPage); //블럭의 시작페이지
		findmap.put("endPage",endPage);//블럭의 끝페이지
		
		
		System.out.println(findmap);
		return bdao.selectList(findmap);
	}

	public Board selectOne(int bnum) {
		
		return bdao.selectOne(bnum);
	}

	public void delete(int bnum) {
		// 1) 댓글 삭제 (자식) : 반드시 자식부터 삭제
		int cnt = rdao.delete_bnum(bnum);
		System.out.println(cnt+"건 삭제");
		
		// 2) 게시물 파일 삭제 (자식): 반드시 자식부터 삭제
		 cnt = bfdao.delete_bnum(bnum);
		System.out.println(cnt+"건 boardfile삭제");
		
		//3) 게시물 삭제(부모)
		cnt = bdao.delete(bnum);
		System.out.println(cnt+"건 board 삭제");
		
		
		
	}

	public void update(Board board, List<String> filenames, String[] removefiles) {
		//1)게시물 수정
		bdao.update(board);
		
		//2)추가할 파일들 추가
		for(String filename:filenames) {
			Boardfile boardfile = new Boardfile();
			boardfile.setBnum(board.getBnum());
			boardfile.setFilename(filename);
			bfdao.insert(boardfile);
		}
		
		//3)파일들 삭제
		if (removefiles==null) return;
		for(String bfnum:removefiles) {
			bfdao.delete(Integer.parseInt(bfnum));
		}
	}
	
	public void update_readcnt(int bnum) {
		bdao.update_readcnt(bnum);
	}

}
