<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var="count" value="${page.index }" />
					<c:forEach items="${list }" var="vo" varStatus="status">			
						<tr>
							<td>[${count - status.index }]</td>
							<td style="text-align:left; padding-left: ${20 * vo.depth }px">
								<c:if test="${vo.depth > 0}">
									<img src='${pageContext.request.contextPath}/assets/images/reply.png'>
								</c:if>
								<a href="${pageContext.request.contextPath}/board/view/${vo.no}?p=${page.currentPage }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${not empty authUser && vo.userNo == authUser.no}">
									<td><a href="${pageContext.request.contextPath}/board/delete/${vo.no }?p=${page.currentPage }" class="del">삭제</a></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:import url="/WEB-INF/views/includes/page.jsp"></c:import>	
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:choose>
						<c:when test="${empty authUser.no }">
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/board/add" id="new-book">글쓰기</a>
						</c:otherwise>
					</c:choose>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>