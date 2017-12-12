<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<!--  ///////////////////////// JSTL  ////////////////////////// -->
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
		.bubble {
		    transform: scale(1);
		    width: 120%;
		    height: 120%;
		    transition: all 2s;
		}
   </style>

<script type="text/javascript">

	function fncGetList(searchOrderbyPrice, currentPage) {
		$("#currentPage").val(currentPage);
		$("#searchOrderbyPrice").val(searchOrderbyPrice);
		$("form").attr("method" , "POST").attr("action" , "/product/listProduct?menu=${menu}").submit();
	}

	$(function() {
		
		// 검색했을 때 tooltip		
		$(":text[name='searchKeyword']").on("keydown", function(e) {
			
			if(e.keyCode == 13) {
				fncGetList('','1');
			} else {
				
				$(this).autocomplete({
					source: function(request, response) {
					$.ajax({
						url : "/product/json/listProductName",
						method : "POST",
						data : {
							prodName : request.term
						},
						dataType : "json",
						success : function(serverData) {
							response(serverData);
						}
					});
					},
					minLength : 2
				});
			}
		});

		// 무한 Scroll
		$(window).on("scroll", function() {
			if($(window).scrollTop() == ($(document).height() - $(window).height())) {
				dataLoading();
			}
		})
		
		var pageInfo = 1;
		
		function dataLoading() {
			pageInfo ++;
			//alert("pageInfo :: "+pageInfo);
			$.ajax({
				url : "/product/json/listProduct",
				method : "POST",
				contentType : "application/json; charset=UTF-8",
				data : JSON.stringify({
					currentPage : pageInfo
				}),
				dataType : "json",
				success : function(serverData) {
					//alert("ajax dataLoading Success...");

					var idx = 4*(pageInfo-1);
					
					$(serverData.list).each(function(index,data) {
						console.log(data.price);
						//alert(index);
						var html = '<tr><td align="center">'+(idx+index)+'</td><c:if test="${!empty user && user.role.trim() eq \'admin\'}">'
						+'<td align="center">'+data.prodNo+'</td></c:if>'
						+'<td align="left"><input type="hidden" value="'+data.prodNo+'" name="prodNo"/>'
						+'<img src = "/images/uploadFiles/'+data.fileName+'" onerror="this.src=\'/images/no_image.jpg\'" height="90px" width="100px" border="0" align="absmiddle" style="padding: 5px"/>'
						+'&nbsp;'+data.prodName+'</td><td align="right">'+data.price+' 원</td><td align="center">'
						+((data.amount==0)? "품절": data.amount+'&nbsp;개')+'</td><td align="center">'+data.regDate+'</td>';
						
						$("#list").append(html);
					})
					
				},
				error:function(request,status,error){
				    alert("code:"+request.status+"\n"+
				    		"message:"+request.responseText+
				    		 "\n"+"error:"+error);
				   }
			});
		}

		/////////////////////////////////////////////////////////////////////////////////////
		
		$("button.btn.btn-default").bind("click", function() {
			fncGetList('','1');
		});
		
		var columnIndex = $("#list th:contains('상품정보')").index()+1;
		
		//품절여부에 따라서 링크 조절
		var arr = $("tbody td").index($("tbody td:contains('품절')"));
		
		$("tbody td:nth-child("+columnIndex+")").on("click", function() {

			//console.log(columnIndex);
			var index = $("tbody td:nth-child("+columnIndex+")").index(this);
			console.log("클릭 Index :: "+index);
			var prodNo = $($("input[name=prodNo]")[index]).val();
			console.log("상품번호 :: "+prodNo);

			//이 페이지에 품절이 없을 때
			if(arr<0) {
				self.location = "/product/getProduct?prodNo="+prodNo+"&menu=${menu}";
			} else {
				var position = true;
				$("tbody td:contains('품절')").each(function(idx){
					//console.log("idx :: "+idx+"index :: "+(index-1));
					//console.log("이값은?"+$("tr.ct_list_pop").index($($("tr.ct_list_pop:contains('품절')")[idx])));
					if($("tbody td").index($($("tbody td:contains('품절')")[idx])) == index) {
						position = false;
						//console.log("position :: "+position);
					}
				})
				if(position == true) {
					console.log("prodNo:: "+prodNo+" / menu = ${menu}");
					self.location = "/product/getProduct?prodNo="+prodNo+"&menu=${menu}";
				}
			}

		});
		
		$("tbody td img").hover(function() {
			$(this).addClass('bubble');
		}, function() {
			$(this).removeClass('bubble');
		});
		
	});

</script>
</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />

	<div class="container">
	
		<div class="page-header text-info">
	       <h3 class="text-info">
	       <c:choose>
				<c:when test="${!empty menu && menu eq 'search'}">
				 	상품 목록
				</c:when>
				<c:otherwise>
					판매 상품 관리
				</c:otherwise>
			</c:choose>
	       </h3>
	    </div>
	    
	    <div class="row">
	    	<div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체 ${resultPage.totalCount} 건수, 추가 상품은 스크롤스크롤~
		    	</p>
		    </div>
		    <div class="col-md-6 text-right">
			    <form class="form-inline" name="detailForm">
			    
				  <div class="form-group">
				    <select class="form-control" name="searchCondition" >
						<option value="1" ${(!empty search && search.searchCondition eq '1')? "selected" : ""}>상품명</option>
						<option value="2" ${(!empty search && search.searchCondition eq '2')? "selected" : ""}>상품가격</option>
					</select>
				  </div>
				  
				  <div class="form-group">
				    <label class="sr-only" for="searchKeyword">검색어</label>
				    <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"  placeholder="검색어"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				  <button type="button" class="btn btn-default">검색</button>
				  
				  <!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				  <input type="hidden" id="currentPage" name="currentPage" value=""/>
				  
				</form>
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
	            <th align="center">가격
		        <c:choose>
					<c:when test="${search.searchOrderbyPrice eq '0'}">
						<a href="javascript:fncGetList('1','1');">▼</a>
					</c:when>
					<c:when test="${search.searchOrderbyPrice eq '1'}">
						<a href="javascript:fncGetList('0','1');">▲</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:fncGetList('1',${resultPage.currentPage});">◇</a>
					</c:otherwise>
				</c:choose>
				</th>
	            <th align="center">상품개수</th>
	            <th align="center">상품등록일</th>
	          </tr>
	        </thead>
	        
	        <tbody>
	        	<c:forEach var="product" items="${list}">
	        		<tr>
	        			<td align="center">${list.indexOf(product) + 1}</td>
	        			<c:if test="${!empty user && user.role.trim() eq 'admin'}">
						<td align="center">${product.prodNo}
						</td>
						</c:if>
						<td align="left">
						<img src = "/images/uploadFiles/${product.fileName}" onerror="this.src='/images/no_image.jpg'"
						height="90px" width="100px" border="0" align="absmiddle"
						style="padding: 5px"/>&nbsp;
						${product.prodName}
						<input type="hidden" value="${product.prodNo}" name="prodNo"/>
						</td>
						<td align="right"><fmt:formatNumber value="${product.price}" pattern="#,###"/> 원</td>
	        			<td align="center">
						<c:if test="${product.amount == 0}">
							품절
						</c:if>	
						<c:if test="${product.amount != 0}">
							${product.amount}&nbsp;개
						</c:if>
						</td>
						<td align="center">${product.regDate}</td>
	        		</tr>
	        	</c:forEach>
	        </tbody>
    	
    	</table>

</div>
</body>
</html>
