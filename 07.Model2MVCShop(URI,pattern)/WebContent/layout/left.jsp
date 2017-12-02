<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	//==> jQuery 적용 추가된 부분
	 $(function() {
		
		$(".Depth03").css("cursor","pointer");
		
		var loc = "";
		 
		//==> 개인정보조회 Event 연결처리부분
		//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
	 	$(".Depth03:contains('개인정보조회')" ).bind("click" , function() {
	 		loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId=${user.userId}");
		});
		
	 	$(".Depth03:contains('회원정보조회')" ).bind("click" , function() {
	 		loc = $(".Depth03").index(this);
	 		$(window.parent.frames["rightFrame"].document.location).attr("href","/user/listUser");
		});

		$(".Depth03:contains('판매상품등록')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/product/addProduct");
		});
		
		$(".Depth03:contains('판매상품관리')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/product/listProduct?menu=manage");
		});

		$(".Depth03:contains('상품배송관리')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/purchase/listSale?menu=sale");
		});
		
		$(".Depth03:contains('상품검색')").bind("click", function() {
			//$(this).css("background-color","#BEAEAD");
			//console.log("상품검색"+$(".Depth03").index(this));
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/product/listProduct?menu=search");
		});
		
		$(".Depth03:contains('구매이력조회')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/purchase/listPurchase?menu=purchase");
		});

		$(".Depth03:contains('장바구니조회')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/cart/listCart?userId=${user.userId}");
		});
		
		// history
	 	$( ".Depth03:contains('최근 본 상품')" ).on("click" , function() {
	 		loc = $(".Depth03").index(this);
	 		popWin = window.open("/product/history",
					"popWin",
					"left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
		});
		
		// file upload
		$(".Depth03:contains('MultiFileUpload')").bind("click", function() {
			loc = $(".Depth03").index(this);
			$(window.parent.frames["rightFrame"].document.location).attr("href","/test/multiFileUpload");
		});
		
		$(".Depth03").bind("click", function() { 
			$(".Depth03").css("background-color","");
			//console.log("배경 흰색...");
			$($(".Depth03").get(loc)).css("background-color","#BEAEAD");
		});
	});	
	 
</script>
</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table width="159" border="0" cellspacing="0" cellpadding="0">

<!--menu 01 line-->
<tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
			<tr>
				<c:if test="${ !empty user }">
					<tr>
						<td class="Depth03">
							<!-- ////////////////// jQuery Event 처리로 변경됨 ///////////////////////// 
							<a href="/user/getUser?userId=${user.userId}" target="rightFrame">개인정보조회</a>	
							////////////////////////////////////////////////////////////////////////////////////////////////// -->
							개인정보조회
						</td>
					</tr>
				</c:if>
			
				<c:if test="${user.role == 'admin'}">
					<tr>
						<td class="Depth03" >
							<!-- ////////////////// jQuery Event 처리로 변경됨 ///////////////////////// 
							<a href="/user/listUser" target="rightFrame">회원정보조회</a>	
							////////////////////////////////////////////////////////////////////////////////////////////////// -->
							회원정보조회
						</td>
					</tr>
				</c:if>
			
				<tr>
					<td class="DepthEnd">&nbsp;</td>
				</tr>
		</table>
	</td>
</tr>

<!--menu 02 line-->
<c:if test="${user.role == 'admin'}">
	<tr>
		<td valign="top"> 
			<table  border="0" cellspacing="0" cellpadding="0" width="159">
				<tr>
					<td class="Depth03">
						<!-- <a href="../product/addProductView.jsp;" target="rightFrame">판매상품등록</a>  -->
						판매상품등록
					</td>
				</tr>
				<tr>
					<td class="Depth03">
						<!-- <a href="/listProduct.do?menu=manage"  target="rightFrame">판매상품관리</a> -->
						판매상품관리
					</td>
				</tr>
				<tr>
					<td class="Depth03">
							상품배송관리
					</td>
				</tr>
				<tr>
					<td class="DepthEnd">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</c:if>

<!--menu 03 line-->
<tr>
	<td valign="top"> 
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
			<tr>
				<td class="Depth03">
					상품검색
				</td>
			</tr>
			
			<c:if test="${ !empty user && user.role == 'user'}">
			<tr>
				<td class="Depth03">
					구매이력조회
				</td>
			</tr>

			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
			<tr>
				<td class="Depth03">
					장바구니조회
				</td>
			</tr>
			</c:if>	
				
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
			<tr>
				<td class="Depth03">
					최근 본 상품
				</td>
			</tr>
			<tr>
				<td class="DepthEnd">&nbsp;</td>
			</tr>
			<tr>
				<td class="Depth03">
					MultiFileUpload
				</td>
			</tr>
		</table>
	</td>
</tr>

</table>

</body>
</html>
