package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}
	
	public void insert(BoardVo boardVo) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values(?, ?, 0, now(), (select ifnull(max(g_no), 0) + 1 from board as subquery) ,1, 0, ?)")
		) {
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getUserNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public void reply(BoardVo vo) {
		Long groupNo = vo.getGroupNo();
		Long orderNo = vo.getOrderNo() + 1;
		int depth = vo.getDepth() + 1;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = " + groupNo + " and o_no = " + orderNo);
				PreparedStatement pstmt2 = conn.prepareStatement("insert into board(title, contents, reg_date, g_no, o_no, depth) values(?, ?, now(), " + groupNo +", " + orderNo + ", " + depth)
		){
			pstmt1.executeUpdate();
			
			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select board.no, board.title, user.name, board.hit, board.reg_date, user.no from board inner join user on board.user_no = user.no order by g_no desc, o_no asc limit 0, 5");
				ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String userName = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setUserName(userName);
				boardVo.setHit(hit);
				boardVo.setRegDate(regDate);
				boardVo.setUserNo(userNo);
				
				result.add(boardVo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return result;
	}

	public void deleteByNoAndPassword(Long no, String password) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no = ? and password = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public BoardVo findByNo(Long no) {
		BoardVo boardVo = new BoardVo();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select no, title, name, hit, reg_date from board where no = ?");
				ResultSet rs = pstmt.executeQuery();
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return boardVo;
	}
}
