<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>구매상세조회</title>
<meta charset="EUC-KR">

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Bootstrap Dropdown Hover CSS -->
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
<!-- Bootstrap Dropdown Hover JS -->
<script src="/javascript/bootstrap-dropdownhover.min.js"></script>


<!-- jQuery UI toolTip 사용 CSS-->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jQuery UI toolTip 사용 JS-->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
	body {
		padding-top: 50px;
	}
</style>

<script type="text/javascript">

	$(function(){
		
		$(".btn.btn-primary:contains('수 정')").bind("click", function() {
			self.location = "/purchase/updatePurchase?tranNo=${purchase.tranNo}";
		})
		
		$(".btn.btn-primary:contains('확 인')").bind("click", function() {
			self.location = "/purchase/listPurchase?menu=purchase";
		})
		
	})
	
</script>

</head>

<body>
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
	<!-- ToolBar End /////////////////////////////////////-->

	<div class="container">
	
		<div class="page-header text-center">
			<h3 class="text-info">구매상세조회</h3>
		</div>
		
		<form class="form-horizontal">
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>물품번호</strong></div>
				<div class="col-sm-4">
					${purchase.purchaseProd.prodNo}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자아이디</strong></div>
				<div class="col-sm-4">
					${purchase.buyer.userId}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매방법</strong></div>
				<div class="col-sm-4">
					${(purchase.paymentOption eq "1")? "현금구매" : "신용구매"}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자이름</strong></div>
				<div class="col-sm-4">
					${purchase.receiverName ne 'null' ? purchase.receiverName : ''}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자연락처</strong></div>
				<div class="col-sm-4">
					${purchase.receiverPhone ne 'null' ? purchase.receiverPhone : ''}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>가격</strong></div>
				<div class="col-sm-4">
					<c:if test="${!empty purchase.price}">
						${purchase.purchaseProd.price} (원) x ${purchase.amount} (개) = ${purchase.price} 원
					</c:if>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매요청사항</strong></div>
				<div class="col-sm-4">
					${purchase.divyRequest}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>배송희망일</strong></div>
				<div class="col-sm-4">
					${purchase.divyDate}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>주문일</strong></div>
				<div class="col-sm-4">
					${purchase.orderDate}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-center">
					<button type="button" class="btn btn-primary">수 정</button>
					<button type="button" class="btn btn-primary">확 인</button>
				</div>
			</div>
		
		</form>
	</div>

</body>
</html>