<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>�������� ����</title>
<meta charset="EUC-KR">

<!-- ���� : http://getbootstrap.com/css/   ���� -->
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
		
		$(".btn.btn-primary:contains('�� ��')").bind("click", function() {
			$("form").attr("method","POST")
					 .attr("action","/purchase/updatePurchase?tranNo=${purchase.tranNo}").submit();
		})
		
		$( ".btn.btn-primary:contains('�� ��')" ).on("click" , function() {
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
			<h3 class="text-info">������������</h3>
		</div>
	
		<form class="form-horizontal" name="detailForm">
			<input type="hidden" name="buyerId" value="${purchase.buyer.userId}">
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�����ھ��̵�</strong></div>
				<div class="col-sm-4">
					${purchase.buyer.userId}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>���Ź��</strong></div>
				<div class="col-sm-4">
					<select name="paymentOption" class="form-control">
						<option value="1" ${(purchase.paymentOption eq "1")? "selected" : ""}>���ݱ���</option>
						<option value="2" ${(purchase.paymentOption eq "2")? "selected" : ""}>�ſ뱸��</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�������̸�</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverName" name="receiverName"
							value="${purchase.receiverName}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>������ ����ó</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverPhone" name="receiverPhone"
							value="${purchase.receiverPhone}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�������ּ�</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyAddr" name="divyAddr"
							value="${purchase.divyAddr}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>���ſ�û����</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyRequest" name="divyRequest"
							value="${purchase.divyRequest}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��������</strong></div>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyDate" name="divyDate"
							value="${purchase.divyDate}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-center">
					<button type="button" class="btn btn-primary">�� ��</button>
					<button type="button" class="btn btn-primary">�� ��</button>
				</div>
			</div>
			
		</form>
	</div>

</body>
</html>