<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pager">
	<ul>
		<c:choose>
			<c:when test="${page.previousTab }">
				<li><a href="${pageContext.servletContext.contextPath }/board?a=list&p=${page.startPage - 1}">◀</a></li>
			</c:when>
			<c:otherwise>
				<li>◀</li>
			</c:otherwise>
		</c:choose>
		
		<c:forEach begin='${page.startPage}' end="${page.endPage}" var="no">
			<c:choose>
				<c:when test='${no == page.currentPage }'>
					<li class="selected">
				</c:when>
				<c:otherwise>
					<li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${no <= page.maxPage }">
					<a href="${pageContext.servletContext.contextPath }/board?a=list&p=${no}">${no}</a>
				</c:when>
				<c:otherwise>
					${no }
				</c:otherwise>
			</c:choose>
			</li>
		</c:forEach>
		
		<c:choose>
			<c:when test="${page.nextTab }">
				<li><a href="${pageContext.servletContext.contextPath }/board?a=list&p=${page.endPage + 1}">▶</a></li>
			</c:when>
			<c:otherwise>
				<li>▶</li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>