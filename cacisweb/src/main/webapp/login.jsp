<%--

    Copyright 5AM Solutions Inc
    Copyright SemanticBits LLC
    Copyright AgileX Technologies, Inc
    Copyright Ekagra Software Technologies Ltd

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.

--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><s:text name="secureEmailBean.title" /></title>
<link href="cacisweb.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function loadWindow(){
		if(window.name == 'mainFrame'){
			parent.location.href = '';
		} else {
			document.f.j_username.focus();
		}
		//alert(window.name);
	}
</script>
<s:head />
</head>
<!--<body class="mainframe" onload="parent.location.href = 'login.action';">
-->
<body style="vertical-align: top;" class="mainframe" onload="loadWindow();">
<s:include value="banner.jsp"></s:include>
<a name="skipLinksAnchor"><img src="images/dotclear.gif" alt="Blank image for skip-links anchor"></a>
<h2 ALIGN="CENTER">caCIS Administration</h2>
<s:include value="messageAndError.jsp"></s:include>

<form name='f' action="j_spring_security_check" method='POST'>
<table class="formTable" summary="layout">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th class="loginTable"><label for="j_username_id">Username:</label></th>
		<td class="loginTable"><input type="text" name="j_username" value="" id="j_username_id"></td>
	</tr>
	<tr>
		<th class="loginTable"><label for="j_password_id">Password:</label></th>
		<td class="loginTable"><input type="password" name="j_password" id="j_password_id"></td>
	</tr>
	<tr>
		<td colspan="2" class="formButton" align="center"><input type="reset" name="reset" value="RESET" class="formButton">&nbsp;&nbsp;&nbsp;<input type="submit" name="submit" value="LOGIN" class="formButton"></td>
	</tr>
</table>
</form>
</body>
</html>
