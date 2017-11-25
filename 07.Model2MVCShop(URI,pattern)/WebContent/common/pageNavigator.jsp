<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetList('${search.searchOrderbyPrice}','${ resultPage.currentPage-1}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${search.searchOrderbyPrice}','${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${search.searchOrderbyPrice}','${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if>
	

<script type="text/javascript">
<!--
// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용 
function fncGetList(priceOrderbyCode, currentPage) {
	document.getElementById("priceOrderbyCode").value = priceOrderbyCode;
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();
}

window.onload = function change() {
    document.getElementById("pageTemp").style.fontWeight = "bold";
}
//-->
</script>
