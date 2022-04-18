package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Board;
import dto.Boardfile;
import dto.Reply;
import service.BoardService;
import service.BoardfileService;
import service.ReplyService;

/**
 * Servlet implementation class BoardCotroller
 */
@WebServlet("/board/*")
public class BoardCotroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BoardfileService boardfileService = new BoardfileService();  
    private BoardService boardservice = new BoardService();
    private ReplyService replyservice = new ReplyService();
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String path = request.getContentType();
		
		if(uri.contains("add")) {
			//게시물등록
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*20; // 20mbyte
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, size, "utf-8",
			new DefaultFileRenamePolicy());
			
			//클라이언트 접속 ip 정보
			String ip = request.getRemoteAddr();
			String userid = multi.getParameter("userid");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			//board dto만들기
			Board board = new Board();
			board.setIp(ip);
			board.setUserid(userid);
			board.setSubject(subject);
			board.setContent(content);
			System.out.println(board);
			
			
			//실제올라간 파일의 이름리스트
			List<String> filenames = new ArrayList<>();
			
			
			//파일의 이름의 모음
			
			Enumeration<String> files = multi.getFileNames();
			while(files.hasMoreElements()) { //다음 자료가 있다면
				String name=files.nextElement();
				System.out.println(name);
				String filename =multi.getFilesystemName(name); //실제 저장된 파일 이름
				System.out.println(filename);
				if(filename !=null) filenames.add(filename);
			}
			
			System.out.println(filenames);
			//service 호출
			
			int cnt = boardservice.insert(board,filenames);
			
			//redirect이동
			response.sendRedirect(path + "view/board/list.jsp?msg="
					+URLEncoder.encode("추가완료","utf-8"));
		} else if (uri.contains("list")) {
			//리스트
			String findkey = request.getParameter("findkey");
			String findvalue = request.getParameter("findvalue");
			String curPage_s = request.getParameter("curPage");
			int curPage=1;
			if(curPage_s != null) 
			  curPage = Integer.parseInt(curPage_s);
			System.out.println("현재페이지:"+curPage);
			
			//조회조건 map만들기
			Map<String,Object> findmap = new HashMap<>();
			if(findkey==null || findkey.equals(""))findkey="userid";
			if(findvalue==null)	findvalue="";		
			findmap.put("findkey", findkey);
			findmap.put("findvalue", findvalue);
			findmap.put("curPage", curPage);
			
			List<Board> blist = boardservice.selectList(findmap);
			System.out.println(blist);
			//forward이동
			request.setAttribute("blist", blist);
			request.setAttribute("findmap", findmap);
			request.getRequestDispatcher("/view/board/list.jsp")
			.forward(request, response);
			
		}else if (uri.contains("detail")) {
			//상세페이지
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			System.out.println(bnum);
			
			//조회수 +1
			boardservice.update_readcnt(bnum);
			
			//게시물조회
			Board board = boardservice.selectOne(bnum);
			System.out.println(board);
			
			//게시물파일 조회
			List<Boardfile> bflist = boardfileService.selectList(bnum);
			System.out.println(bflist);
			
			
			//댓글 조회
			List<Reply> rlist =replyservice.selectList(bnum);
			System.out.println(rlist);
			
			//forward 방식
			request.setAttribute("board", board);
			request.setAttribute("bflist", bflist);
			request.setAttribute("rlist", rlist);
			request.getRequestDispatcher("/view/board/detail.jsp")
			.forward(request, response);
		
		
		}else if (uri.contains("remove")) {
			//삭제
			int bnum =Integer.parseInt(request.getParameter("bnum"));
			System.out.println(bnum);
			boardservice.delete(bnum);
			
			//redirect이동
			response.sendRedirect(path+"/board/list");
		
		}else if(uri.contains("modiform")) {
			
			//수정폼으로 이동
			int bnum = Integer.parseInt(request.getParameter("bnum")); 
			//board조회
			Board board = boardservice.selectOne(bnum);
			
			//boardfile조회
			List<Boardfile> bflist = boardfileService.selectList(bnum);
			//forward이동
			request.setAttribute("board", board);
			request.setAttribute("bflist", bflist);
			request.getRequestDispatcher("/view/board/modify.jsp")
			.forward(request, response);
			
			
			
			
			
		}else if (uri.contains("modify")) {
			//수정
			
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*20; // 20mbyte
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, size, "utf-8",
			new DefaultFileRenamePolicy());
			
			//클라이언트 접속 ip 정보
			String ip = request.getRemoteAddr();
			int bnum = Integer.parseInt(multi.getParameter("bnum"));
			String userid = multi.getParameter("userid");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			//board dto만들기
			Board board = new Board();
			board.setBnum(bnum);
			board.setIp(ip);
			board.setUserid(userid);
			board.setSubject(subject);
			board.setContent(content);
			System.out.println(board);
			
			
			//실제올라간 파일의 이름리스트
			List<String> filenames = new ArrayList<>();
			
			
			//파일의 이름의 모음
			
			Enumeration<String> files = multi.getFileNames();
			while(files.hasMoreElements()) { //다음 자료가 있다면
				String name=files.nextElement();
				String filename =multi.getFilesystemName(name); //실제 저장된 파일 이름
				if(filename !=null) filenames.add(filename);
			}
			
			System.out.println("추가할 파일리스트"+filenames);
			
			String[] removefiles = multi.getParameterValues("removefile");
			System.out.println("삭제할 파일 리스트"+ Arrays.toString(removefiles));
			
			//서비스호출
			boardservice.update(board,filenames,removefiles);
			
			
			// detail로 이동
			response.sendRedirect(path+"/board/detail?bnum="+bnum);
			
		}else {
			System.out.println("잘못된 uri");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
