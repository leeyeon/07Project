<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>다중 파일 업로드</title>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="POST" enctype="multipart/form-data">
	<input type="file" name="uploadFile" class="ct_input_g" multiple="multiple"
							style="width: 300px; height: 100px" maxLength="13"/>
	<input type="submit" value="전송" />
</form>
</body>
</html>
