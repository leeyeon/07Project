<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>�����ϱ�</title>
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


<!-- jQuery UI toolTip ��� CSS-->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jQuery UI toolTip ��� JS-->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
	body {
		padding-top: 50px;
	}
</style>

<script type="text/javascript">

	$(function() {
		
		$(".btn.btn-primary:contains('�� ��')").bind("click", function() {
			$("form").attr("method", "POST").attr("action", "/purchase/addPurchase").submit();
		})
		
		$( ".btn.btn-primary:contains('�� ��')" ).on("click" , function() {
			$("form")[0].reset();
		});
		
		$("#calender").on(
				"click",
				function() {
					show_calendar('document.detailForm.divyDate', $('input[name=divyDate]').val());
				});
		
	});

</script>
</head>

<body>
	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="container">
	
		<div class="page-header text-center">
			<h3 class="text-info">��ǰ����</h3>
		</div>
		
		<form class="form-horizontal" name="detailForm" >
			<input type="hidden" name="buyerId" value="${user.userId}" />
			<input type="hidden" name="prodNo" value="${product.prodNo}" />
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��ǰ��ȣ</strong></div>
				<div class="col-sm-4">
					${product.prodNo}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��ǰ��</strong></div>
				<div class="col-sm-4">
					${product.prodName}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��ǰ������</strong></div>
				<div class="col-sm-4">
					${product.prodDetail}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��������</strong></div>
				<div class="col-sm-4">
					${product.manuDate}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>����</strong></div>
				<div class="col-sm-4">
					${product.price}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�������</strong></div>
				<div class="col-sm-4">
					${product.regDate}
				</div>
			</div>
			
			<div class="form-group">
				<label for="amount" class="col-sm-offset-1 col-sm-3 control-label">��ǰ����</label>
				<div class="col-sm-4">
					<select name="amount" class="form-control" >
						<c:forEach var="i" begin="1" end="${(product.amount eq 0)? 1 : product.amount}">
							<option value="${i}" ${(i eq 1)? "selected": ""} >${i}��</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label for="amount" class="col-sm-offset-1 col-sm-3 control-label">���Ź��</label>
				<div class="col-sm-4">
					<select name="paymentOption" class="form-control" >
						<option value="1" selected="selected">���ݱ���</option>
						<option value="2">�ſ뱸��</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label for="receiverName" class="col-sm-offset-1 col-sm-3 control-label">�������̸�</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverName" name="receiverName"
						value="${user.userName}"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="receiverPhone" class="col-sm-offset-1 col-sm-3 control-label">�����ڿ���ó</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="receiverPhone" name="receiverPhone"
						value="${user.phone}"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="divyAddr" class="col-sm-offset-1 col-sm-3 control-label">�������ּ�</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyAddr" name="divyAddr"
						value="${user.addr}"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="divyRequest" class="col-sm-offset-1 col-sm-3 control-label">���ſ�û����</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="divyRequest" name="divyRequest"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="divyDate" class="col-sm-offset-1 col-sm-3 control-label">����������</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="divyDate" name="divyDate" readonly/>
				</div>
				<div class="col-sm-1">
					<h4>
						<span id="calender"
							class="glyphicon glyphicon-calendar text-center"></span>
					</h4>
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