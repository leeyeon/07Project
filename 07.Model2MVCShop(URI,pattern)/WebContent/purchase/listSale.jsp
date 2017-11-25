<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title>상품 목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">


</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/purchase/listSale?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					상품 배송 관리
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="13" >
		전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td colspan="13" bgcolor="808285" height="1"></td>
	</tr>
	<tr>
		<td class="ct_list_b" width="70">No</td>
		<td class="ct_line02"></td>
		
		<c:if test="${!empty user && user.role.trim() eq 'admin'}">
		<td class="ct_list_b" width="120">상품번호</td>
		<td class="ct_line02"></td>
		</c:if>
		
		<td class="ct_list_b" width="450">상품정보</td>
		<td class="ct_line02"></td>		
		<td class="ct_list_b" width="120">가격
			<c:choose>
				<c:when test="${search.searchOrderbyPrice eq '0'}">
					<a href="javascript:fncGetList('1','1');">▼</a>
				</c:when>
				<c:when test="${search.searchOrderbyPrice eq '1'}">
					<a href="javascript:fncGetList('0','1');">▲</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:fncGetList('1',${resultPage.currentPage});">◇</a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">상품개수</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">구매자아이디</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="13" bgcolor="808285" height="1"></td>
	</tr>

	<c:forEach var="purchase" items="${list}">
		<c:set var="product" value="${purchase.purchaseProd}"/>
		<tr class="ct_list_pop">
			<td align="center">${list.indexOf(purchase) + 1}</td>
			<td></td>
			<c:if test="${!empty user && user.role.trim() eq 'admin'}">
			<td align="center">${product.prodNo}</td>
			<td></td>
			</c:if>
			<td align="left">
				<c:choose>
					<c:when test="${product.amount == 0}">
						<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"
						height="90px" width="100px" border="0" align="absmiddle"
						style="padding: 5px"/>&nbsp;
						${product.prodName}
					</c:when>
					<c:otherwise>
						<a href="/getProduct.do?prodNo=${product.prodNo}&menu=${menu}">
						<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"
						height="90px" width="100px" border="0" align="absmiddle"
						style="padding: 5px"/>&nbsp;
						${product.prodName}</a>
					</c:otherwise>
				</c:choose>
			</td>
			<td></td>
			<td align="right"><fmt:formatNumber value="${product.price}" pattern="#,###"/> 원</td>
			<td></td>
			<td align="center">
				${purchase.amount}&nbsp;개
			</td>
			<td></td>
			<td align="center">${purchase.buyer.userId}</td>
			<td></td>
			<td align="left">

			<c:choose>
				<c:when test="${purchase.tranCode.trim() eq '1'}">구매완료</c:when>
				<c:when test="${purchase.tranCode.trim() eq '2'}">배송중</c:when>
			<c:otherwise>배송완료</c:otherwise>
			
			</c:choose>
			<c:if test="${purchase.tranCode.trim() eq '1' && menu eq 'sale' }">
				<a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">배송하기</a>
			</c:if>
			
			</td>
		</tr>
		<tr>
			<td colspan="13" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<input type="hidden" id="priceOrderbyCode" name="priceOrderbyCode" value=""/>
			
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
