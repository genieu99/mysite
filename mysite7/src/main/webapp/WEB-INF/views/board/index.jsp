<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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
							<td>
								<sec:authorize access="isAuthenticated()">
									<sec:authentication property="principal" var="authUser" />
									<c:if test="${authUser.no == vo.userNo }" >
										<a href="${pageContext.request.contextPath}/board/delete/${vo.no }?p=${page.currentPage }" class="del">삭제</a>
									</c:if>
								</sec:authorize>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:import url="/WEB-INF/views/includes/page.jsp"></c:import>	
				<!-- pager 추가 -->
				
				<div class="bottom">
					<sec:authorize access="isAuthenticated()">
						<a href="${pageContext.request.contextPath}/board/add" id="new-book">글쓰기</a>
					</sec:authorize>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>