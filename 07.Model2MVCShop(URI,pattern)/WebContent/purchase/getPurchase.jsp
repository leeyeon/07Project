<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>���Ż���ȸ</title>
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

	$(function(){
		
		$(".btn.btn-primary:contains('�� ��')").bind("click", function() {
			self.location = "/purchase/updatePurchase?tranNo=${purchase.tranNo}";
		})
		
		$(".btn.btn-primary:contains('Ȯ ��')").bind("click", function() {
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
			<h3 class="text-info">���Ż���ȸ</h3>
		</div>
		
		<form class="form-horizontal">
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��ǰ��ȣ</strong></div>
				<div class="col-sm-4">
					${purchase.purchaseProd.prodNo}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�����ھ��̵�</strong></div>
				<div class="col-sm-4">
					${purchase.buyer.userId}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>���Ź��</strong></div>
				<div class="col-sm-4">
					${(purchase.paymentOption eq "1")? "���ݱ���" : "�ſ뱸��"}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�������̸�</strong></div>
				<div class="col-sm-4">
					${purchase.receiverName ne 'null' ? purchase.receiverName : ''}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�����ڿ���ó</strong></div>
				<div class="col-sm-4">
					${purchase.receiverPhone ne 'null' ? purchase.receiverPhone : ''}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>����</strong></div>
				<div class="col-sm-4">
					<c:if test="${!empty purchase.price}">
						${purchase.purchaseProd.price} (��) x ${purchase.amount} (��) = ${purchase.price} ��
					</c:if>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>���ſ�û����</strong></div>
				<div class="col-sm-4">
					${purchase.divyRequest}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>��������</strong></div>
				<div class="col-sm-4">
					${purchase.divyDate}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3 control-label"><strong>�ֹ���</strong></div>
				<div class="col-sm-4">
					${purchase.orderDate}
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-center">
					<button type="button" class="btn btn-primary">�� ��</button>
					<button type="button" class="btn btn-primary">Ȯ ��</button>
				</div>
			</div>
		
		</form>
	</div>

</body>
</html>