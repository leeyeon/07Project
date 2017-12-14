<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
		body {
			padding-top : 50px;
		}
   </style>
   
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
			var tranNo = $($("input[name=tranNo]")[index]).val();
			//alert(prodNo+" :: index :: "+index);
			self.location="/purchase/updateTranCode?tranNo="+tranNo+"&tranCode=2";
		});
		
		$( "button.btn.btn-default" ).on("click" , function() {
			fncGetList(1);
		});
		
	});
	
	function fncGetList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form").attr("method" , "POST").attr("action" , "/purchase/listSale").submit();
	}

</script>
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="container">
	
		<div class="page-header text-info">
	       <h3 class="text-info">
	       		상품 배송 관리
	       </h3>
	    </div>
	    
	    <div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지
		    	</p>
		    </div>
	    </div>
	    
	    <table class="table table-hover table-striped" id="list">
	    	<thead>
	    	  <tr>
	    		<th align="center">No</th>
	            <c:if test="${!empty user && user.role.trim() eq 'admin'}">
	            	<th align="center">상품번호</th>
	            </c:if>
	            <th align="center">상품정보</th>
	            <th align="center">가격</th>
	            <th align="center">상품개수</th>
	            <th align="center">구매자아이디</th>
	            <th align="center">현재상태</th>
	          </tr>
	    	</thead>
	    	
	    	<tbody>
	    		<c:forEach var="purchase" items="${list}">
				<input type="hidden" name="tranNo" value="${purchase.tranNo}"/>
				<c:set var="product" value="${purchase.purchaseProd}"/>
	    			<tr>
	        			<td align="center">${list.indexOf(purchase) + 1}</td>
	        			<c:if test="${!empty user && user.role.trim() eq 'admin'}">
						<td align="center">${product.prodNo}
						</td>
						</c:if>
						<td align="center">
							<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"
								height="90px" width="100px" border="0" align="absmiddle"
								style="padding: 5px"/>&nbsp;
								${product.prodName}
						<input type="hidden" value="${product.prodNo}" name="prodNo"/>
						</td>
						<td align="right"><fmt:formatNumber value="${product.price}" pattern="#,###"/> 원</td>
	        			<td align="center">
							${purchase.amount}&nbsp;개
						</td>
						<td align="center">${purchase.buyer.userId}</td>
						<td align="center">
							<c:choose>
								<c:when test="${purchase.tranCode.trim() eq '1'}">구매완료</c:when>
								<c:when test="${purchase.tranCode.trim() eq '2'}">배송중</c:when>
							<c:otherwise>배송완료</c:otherwise>
							
							</c:choose>
							<c:if test="${purchase.tranCode.trim() eq '1'}">
								배송하기
							</c:if>
						</td>
	        		</tr>
	    		</c:forEach>
	    	</tbody>	    	
	    </table>
	    
	    
	    <jsp:include page="../common/pageNavigator_new.jsp"/>
	    
	</div>

		
</body>
</html>
