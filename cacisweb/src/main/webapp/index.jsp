<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK type="text/css" rel="stylesheet" href="cacisweb.css">
<title><s:text name="application.title" /></title>
</head>
<frameset rows="50,*" cols="*" frameborder="NO" border="0">
	<frameset rows="100%,0%" cols="*" frameborder="NO" border="0">
		<frame src="navTop.jsp" name="topFrame" title="Top Navigation Frame" scrolling="no" noresize>
	</frameset>
	<frameset cols="153,*" frameborder="NO" border="0">
		<frame src="navLeft.jsp" name="leftFrame" title="Left Menu Frame" scrolling="auto">
		<frame src="welcome.jsp" name="mainFrame" title="Main Content Frame" scrolling="auto">
	</frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>