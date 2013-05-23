<%--

    Copyright Ekagra Software Technologies Ltd.
    Copyright SAIC, Inc
    Copyright 5AM Solutions
    Copyright SemanticBits Technologies

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.

--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><s:text name="secureEmailBean.title" /></title>
<link href="cacisweb.css" rel="stylesheet" type="text/css">
<s:head />
</head>
<body>
<s:if test="hasActionMessages()">
	<table class="messageTable" summary="layout">
		<tr>
			<td align="center"><s:actionmessage /></td>
		</tr>
	</table>
</s:if>
<s:if test="hasActionErrors() || hasFieldErrors()">
	<table class="errorTable" summary="layout">
		<tr>
			<td align="left"><s:actionerror /><s:fielderror /></td>
		</tr>
	</table>
</s:if>
</body>
</html>
