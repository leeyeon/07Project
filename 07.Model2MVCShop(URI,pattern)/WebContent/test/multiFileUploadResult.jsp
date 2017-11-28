<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>다중 파일 업로드(결과)</title>

</head>

<body bgcolor="#ffffff" text="#000000">

<c:forEach var="fileName" items="${list}">
	${fileName} <br/>
</c:forEach>

</body>
</html>