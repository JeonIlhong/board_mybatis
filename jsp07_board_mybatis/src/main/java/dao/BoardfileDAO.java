package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dto.Board;
import dto.Boardfile;

public class BoardfileDAO {
	public int insert(Boardfile boardfile) {
		try(SqlSession session = MBConn.getSession()) {
			return session.insert("BoardfileMapper.insert",boardfile);
		}
	}
	
	public int update(Boardfile boardfile) {
		try(SqlSession session = MBConn.getSession()) {
			return session.update("BoardfileMapper.update",boardfile);
		}
	 }
	
	public int delete(int bfnum) {
		try(SqlSession session = MBConn.getSession()) {
			return session.delete("BoardfileMapper.delete",bfnum);
		}
	}
	
	//bnum에 해당하는 boardfile을 삭제
	public int delete_bnum(int bnum) {
		try(SqlSession session = MBConn.getSession()) {
			return session.delete("BoardfileMapper.delete",bnum);
		}
	}
	
	
	public Boardfile selectOne(int bfnum) {
		try(SqlSession session = MBConn.getSession()) {
			return session.selectOne("BoardfileMapper.selectOne",bfnum);
		}
	}
	
	public List<Boardfile> selectList(int bnum) {
		try(SqlSession session = MBConn.getSession()) {
			return session.selectList("BoardfileMapper.selectList",bnum);
		}
	}
	
}
