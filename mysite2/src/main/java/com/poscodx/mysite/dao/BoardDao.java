package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

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
	
	public void reply(BoardVo boardVo) {
		System.out.println(boardVo.getGroupNo());
		System.out.println(boardVo.getOrderNo());
		System.out.println(boardVo.getDepth());
		
		Long groupNo = boardVo.getGroupNo();
		Long orderNo = boardVo.getOrderNo() + 1;
		int depth = boardVo.getDepth() + 1;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = " + groupNo + " and o_no >= " + orderNo);
				PreparedStatement pstmt2 = conn.prepareStatement("insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values(?, ?, 0, now(), " + groupNo +", " + orderNo + ", " + depth + ", ?)")
		){
			pstmt1.executeUpdate();
			
			pstmt2.setString(1, boardVo.getTitle());
			pstmt2.setString(2, boardVo.getContents());
			pstmt2.setLong(3, boardVo.getUserNo());
			pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public int countBoard() {
		int totalBoard = 0;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select count(*) from board");
				ResultSet rs = pstmt.executeQuery();
		) {
			if (rs.next()) {
				totalBoard = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return totalBoard;
	}
	
	public List<BoardVo> findAll(int currentPage) {
		int perPage = PageVo.getPerPage();
		
		List<BoardVo> result = new ArrayList<>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select board.no, board.title, user.name, board.hit, board.reg_date, user.no, board.depth from board inner join user on board.user_no = user.no order by g_no desc, o_no asc limit " + (currentPage - 1) * perPage + ", " + perPage);
				ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String userName = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				int depth = rs.getInt(7);
				
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setUserName(userName);
				boardVo.setHit(hit);
				boardVo.setRegDate(regDate);
				boardVo.setUserNo(userNo);
				boardVo.setDepth(depth);
				
				result.add(boardVo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return result;
	}

	public BoardVo findByNo(Long no) {
		BoardVo boardVo = new BoardVo();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select no, title, hit, reg_date, contents, user_no, g_no, o_no, depth from board where no = ?");
		) {
			pstmt.setLong(1, no);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Long boardNo = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				String contents = rs.getString(5);
				Long userNo = rs.getLong(6);
				Long groupNo = rs.getLong(7);
				Long orderNo = rs.getLong(8);
				int depth = rs.getInt(9);
				
				boardVo.setNo(boardNo);
				boardVo.setTitle(title);
				boardVo.setHit(hit);
				boardVo.setRegDate(regDate);
				boardVo.setContents(contents);
				boardVo.setUserNo(userNo);
				boardVo.setGroupNo(groupNo);
				boardVo.setOrderNo(orderNo);
				boardVo.setDepth(depth);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return boardVo;
	}

	public void deleteByNo(Long no) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void update(BoardVo boardVo) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ?, reg_date = now() where no = " + boardVo.getNo());
		) {
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void updateHitByNo(BoardVo boardVo) {
		int viewCount = boardVo.getHit() + 1;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set hit = " + viewCount + " where no = " + boardVo.getNo());
		) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
}
