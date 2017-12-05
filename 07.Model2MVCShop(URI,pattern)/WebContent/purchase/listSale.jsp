<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title>상품 목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	function fncGetList(currentPage) {
		//alert();
		$("#currentPage").val(currentPage);
		$("form").attr("method" , "POST").attr("action" , "/purchase/listSale").submit();
	}
	
	$(function() {
		
		var noIndex = $("#list td:contains('No')").index()+1;
		var prodIndex = $("#list td:contains('상품정보')").index()+1;
		var tranIndex = $("#list td:contains('현재상태')").index()+1;
		
		$("tr.ct_list_pop td:nth-child("+noIndex+")").bind("click", function(){
			var index = ($("tr.ct_list_pop td:nth-child("+noIndex+")").index(this));
			self.location="/purchase/getPurchase?tranNo="+$($("input:hidden[name='tranNo']")[index]).val();
		});
		
		$("tr.ct_list_pop td:nth-child("+prodIndex+")").bind("click", function() {
			//alert();
			var index = $("tr.ct_list_pop td:nth-child("+prodIndex+")").index(this);
			//console.log("클릭 Index :: "+index);
			var prodNo = $($("input[name=prodNo]")[index]).val();
			//console.log("상품번호 :: "+prodNo);

			self.location = "/product/getProduct?prodNo="+prodNo+"&menu=${menu}";
		});

		$("td:contains('배송하기')").bind("click", function(){
			var index = $("tr.ct_list_pop td:nth-child("+tranIndex+")").index(this);
			var prodNo = $($("input[name=prodNo]")[index]).val();
			//alert(prodNo+" :: index :: "+index);
			self.location="/purchase/updateTranCodeByProd?prodNo="+prodNo+"&tranCode=2";
		});
		
	});

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm">

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

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;"
id="list">
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
		<td class="ct_list_b" width="120">가격</td>
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
	<input type="hidden" name="tranNo" value="${purchase.tranNo}"/>
	<input type="hidden" name="prodNo" value="${purchase.purchaseProd.prodNo}"/>
	<c:set var="product" value="${purchase.purchaseProd}"/>
		<tr class="ct_list_pop">
			<td align="center">${list.indexOf(purchase) + 1}</td>
			<td></td>
			<c:if test="${!empty user && user.role.trim() eq 'admin'}">
			<td align="center">${product.prodNo}</td>
			<td></td>
			</c:if>
			<td align="left">
				<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"
				height="90px" width="100px" border="0" align="absmiddle"
				style="padding: 5px"/>&nbsp;
				${product.prodName}
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
				배송하기
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
			
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
