<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="application.title" /></title>
<LINK type="text/css" rel="stylesheet" href="cacisweb.css">
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<STYLE type="text/css">
body {
	background-color: #003366;
	padding-top: 0;
	margin-top: 0;
	padding-left: 0;
	margin-left: 0;
	padding-right: 0;
	margin-right: 0;
}
</STYLE>

</head>

<body style="vertical-align: top;">
<s:include value="banner.jsp"></s:include>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="topBar" summary="layout">
	<tr class="topBar">
		<td align="left" valign="middle">&nbsp;<a href="#skipLinksAnchor">Skip Navigational Links&nbsp;</a></td>
		<td>&nbsp;<td>
		<td align="left" valign="middle" class="topBarDiv">&nbsp;<A
			href="secureEmailRecipientList.action" title="Secure Email Configuration" target="mainFrame"><s:text
			name="navleft.secureEmail" /></A></td>
		<td align="left" valign="middle" class="topBarDiv">&nbsp;<A
			href="secureFTPRecipientList.action" title="Secure FTP Configuration" target="mainFrame"><s:text
			name="navleft.secureFTP" /></A></td>
		<td align="left" valign="middle" class="topBarDiv">&nbsp;<A
			href="secureXDSNAVRecipientList.action" title="Secure XDS NAV Configuration" target="mainFrame"><s:text
			name="navleft.secureXDSNAV" /></A></td>
		<td align="left" valign="middle" class="topBarDiv">&nbsp;<A
			href="cdwUserPermission.action" title="CDW User Permissions" target="mainFrame"><s:text
			name="navleft.cdwUserPermission" /></A></td>
		<td width="307" height="24" align="right"><a href="j_spring_security_logout" target="_parent"><s:text
			name="application.logout" />&nbsp;</a></td>
		<a name="skipLinksAnchor"><img src="images/dotclear.gif" alt="Blank image for skip-links anchor"></a>
	</tr>
</table>
</body>
</html>
