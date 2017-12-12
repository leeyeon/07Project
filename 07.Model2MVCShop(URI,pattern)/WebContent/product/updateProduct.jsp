<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>상품정보수정</title>
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

<!--  ///////////////////////// CSS ////////////////////////// -->
<style>
	body {
		padding-top: 50px;
	}
</style>

<script type="text/javascript">
	function fncAddProduct() {
		//Form 유효성 검증

		var name = $("input[name='prodName']").val();
		var detail = $("input[name='prodDetail']").val();
		var amount = $("input[name='amount']").val();
		var manuDate = $("input[name='manuDate']").val();
		var price = $("input[name='price']").val();

		if (name == null || name.length < 1) {
			alert("상품명은 반드시 입력하여야 합니다.");
			return;
		}
		if (detail == null || detail.length < 1) {
			alert("상품상세정보는 반드시 입력하여야 합니다.");
			return;
		}
		if (amount == null || amount.length < 1) {
			alert("상품개수는 반드시 입력하셔야 합니다.");
			return;
		}

		if (manuDate == null || manuDate.length < 1) {
			alert("제조일자는 반드시 입력하셔야 합니다.");
			return;
		}
		if (price == null || price.length < 1) {
			alert("가격은 반드시 입력하셔야 합니다.");
			return;
		}

		$("form").attr("method", "POST").attr("action", "/product/updateProduct").submit();
	}

	$(function() {

		$(".btn.btn-primary:contains('수 정')").on("click", function() {
			fncAddProduct();
		});

		$(".btn.btn-primary:contains('취 소')").on("click", function() {
			$("form")[0].reset();
		});
		
		$("#calender").on(
				"click",
				function() {
					//show_calendar($("#manuDate"),$('input[name=manuDate]').val());
					show_calendar('document.detailForm.manuDate', $(
							'input[name=manuDate]').val());
				});
		
		$('#manuDate').datepicker({ dateFormat: 'yy-mm-dd' });
		
	});
</script>
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />

	<div class="container">

		<div class="page-header text-center">
			<h3 class="text-info">상품수정</h3>
		</div>

		<form class="form-horizontal" name="detailForm"
			enctype="multipart/form-data">
			<input type="hidden" name="prodNo" value="${product.prodNo}" />

			<div class="form-group">
				<label for="prodName" class="col-sm-offset-1 col-sm-3 control-label">상품명</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="prodName"
						name="prodName" value="${product.prodName}" />
				</div>
			</div>

			<div class="form-group">
				<label for="prodDetail"
					class="col-sm-offset-1 col-sm-3 control-label">상품상세정보</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="prodDetail"
						name="prodDetail" value="${product.prodDetail}" />
				</div>
			</div>

			<div class="form-group">
				<label for="amount" class="col-sm-offset-1 col-sm-3 control-label">상품개수</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="amount" name="amount"
						value="${product.amount}" />
				</div>
				<div class="col-sm-1">
					&nbsp;개
				</div>
			</div>

			<div class="form-group">
				<label for="manuDate" class="col-sm-offset-1 col-sm-3 control-label">제조일자</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="manuDate" name="manuDate"/>
				</div>
			</div>

			<div class="form-group">
				<label for="price" class="col-sm-offset-1 col-sm-3 control-label">가격</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="price" name="price"
						value="${product.price}" />
				</div>
				<div class="col-sm-1">
					&nbsp;원
				</div>
			</div>

			<div class="form-group">
				<label for="uploadFile"
					class="col-sm-offset-1 col-sm-3 control-label">상품이미지</label>
				<div class="col-sm-4">
					<input type="file" class="form-control" id="uploadFile"
						name="uploadFile" value="${product.fileName}" />
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