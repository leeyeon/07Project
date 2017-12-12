<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>구매정보 수정</title>
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
<script type="text/javascript" src="../javascript/calendar.js"></script>

<!--  ///////////////////////// CSS ////////////////////////// -->
<style>
	body {
		padding-top: 50px;
	}
</style>
<script type="text/javascript">

	$(function() {
		
		$(".btn.btn-primary:contains('수 정')").bind("click", function() {
			$("form").attr("method","POST")
					 .attr("action","/purchase/updatePurchase?tranNo=${purchase.tranNo}").submit();
		})
		
		$( ".btn.btn-primary:contains('취 소')" ).on("click" , function() {
			$("form")[0].reset();
		});
		
		$("#calender").on(
				"click",
				function() {
					show_calendar('document.detailForm.divyDate', $('input[name=divyDate]').val());
				});
	})

</script>

</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="container">
	
		<div class="page-header text-center">
			<h3 class="text-info">구매정보수정</h3>
		</div>
	
		<form class="form-horizontal" name="detailForm">
			<input type="hidden" name="buyerId" value="${purchase.buyer.userId}">
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자아이디</strong></div>
				<div class="col-sm-4">
					${purchase.buyer.userId}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매방법</strong></div>
				<div class="col-sm-4">
					<select name="paymentOption" class="form-control">
						<option value="1" ${(purchase.paymentOption eq "1")? "selected" : ""}>현금구매</option>
						<option value="2" ${(purchase.paymentOption eq "2")? "selected" : ""}>신용구매</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자이름</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverName" name="receiverName"
							value="${purchase.receiverName}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자 연락처</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverPhone" name="receiverPhone"
							value="${purchase.receiverPhone}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매자주소</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyAddr" name="divyAddr"
							value="${purchase.divyAddr}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>구매요청사항</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyRequest" name="divyRequest"
							value="${purchase.divyRequest}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>배송희망일</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyDate" name="divyDate"
							value="${purchase.divyDate}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-center">
					<button type="button" class="btn btn-primary">수 정</button>
					<button type="button" class="btn btn-primary">취 소</button>
				</div>
			</div>
			
		</form>
	</div>

</body>
</html>