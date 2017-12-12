<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>상품등록</title>
	<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="../javascript/calendar.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>

	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
       body{
        	padding-top : 50px;
        }
    </style>
    
    <script type="text/javascript">
    
    	$(function() {
    		
    		$("button:contains('확인')").on("click", function() {
    			$(self.location).attr("href","/product/listProduct?menu=manage");
    		});
    		
			$("button:contains('구매')").on("click", function() {
				$(self.location).attr("href","/purchase/addPurchase?prodNo=${product.prodNo}");
    		});
    		
			$("button:contains('이전')").on("click", function() {
				if("${param.menu}" == 'purchase') {
					$(self.location).attr("href","/purchase/listPurchase?menu=${param.menu}");
				} else {
					$(self.location).attr("href","/product/listProduct?menu=${param.menu}");
				}
			});
    	});
    
    </script>

<title>상품상세조회</title>
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp"/>
	
	<div class="container">
	
		<div class="page-header">
	       <h3 class="text-info">상품상세조회</h3>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품번호</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodNo}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품명</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodName}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>가격</strong></div>
			<div class="col-xs-8 col-md-4">
				<fmt:formatNumber value="${product.price}" pattern="#,###" /> 원
			</div>
		</div>
		
		<hr/>
		
		<c:if test="${param.menu ne 'purchase'}">
			<div class="row">
		  		<div class="col-xs-4 col-md-2"><strong>상품개수</strong></div>
				<div class="col-xs-8 col-md-4">${product.amount} 개</div>
			</div>
			
			<hr/>
		</c:if>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품이미지</strong></div>
			<div class="col-xs-8 col-md-4">
				<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"/>
			</div>
		</div>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상품상세정보</strong></div>
			<div class="col-xs-8 col-md-4">${product.prodDetail}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>제조일자</strong></div>
			<div class="col-xs-8 col-md-4">
				<c:if test="${product.manuDate.contains('/')}">
					<fmt:parseDate value="${product.manuDate}" var="dateFmt" pattern="yy/mm/dd"/>
				</c:if>
				<c:if test="${!product.manuDate.contains('/')}">
					<fmt:parseDate value="${product.manuDate}" var="dateFmt" pattern="yyyymmdd"/>
				</c:if>
				<fmt:formatDate value="${dateFmt}" pattern="yyyy-mm-dd"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>등록일자</strong></div>
			<div class="col-xs-8 col-md-4">${product.regDate}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  		<c:choose>
		  		<c:when test="${!empty menu && menu eq 'ok'}">
		  			<button type="button" class="btn btn-primary">확인</button>
		  		</c:when>
		  		<c:otherwise>
			  		<c:if test="${!empty user && user.role eq 'user'}">
						<c:if test="${param.menu ne 'purchase'}">
			  				<button type="button" class="btn btn-primary">구매</button>
			  			</c:if>
			  		</c:if>
					<button type="button" class="btn btn-primary">이전</button>
		  		</c:otherwise>
	  		</c:choose>	
	  		</div>
		</div>
	</div>

</body>
</html>