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
<s:head />
</head>
<body class="mainframe">
<table class="headerTable" summary="layout">
	<tr>
		<td class="headerTable"><s:text name="secureEmailBean.title" /></td>
	</tr>
</table>
<s:include value="messageAndError.jsp"></s:include>

<s:form action="secureEmailRecipientAdd" theme="simple" method="post"
	enctype="multipart/form-data">
	<table class="formTable" summary="layout">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th class="formTable"><label for="secureEmailRecipientAdd_secureEmailBean_certificateAlias"><s:text
				name="secureEmailBean.certificateAlias" /></label></th>
			<td><s:textfield key="secureEmailBean.certificateAlias"
				size="55" cssClass="formTable" /></td>
		</tr>
		<tr>
			<th class="formTable"><label for="secureEmailRecipientAdd_secureEmailBean_certificate"><s:text name="secureEmailBean.certificate" /></label></th>
			<td><s:file key="secureEmailBean.certificate" size="55"
				cssClass="formTable" /></td>
		</tr>
		<tr>
			<td colspan="2" class="formButton"><s:submit
				key="secureEmailBean.addButton" cssClass="formButton" /></td>
		</tr>
	</table>
	<s:hidden name="operationMode" value="STORE"></s:hidden>
	<s:token />
</s:form>
<table class="listTable">
	<tr>
		<th class="listTable" width="25%" scope="col"><s:text
			name="secureEmailBean.certificateAlias" /></th>
		<th class="listTable" width="65%" scope="col"><s:text
			name="secureEmailBean.certificateDN" /></th>
		<th class="listTable" width="10%" scope="col"><s:text
			name="secureEmailBean.action" /></th>
	</tr>
	<s:iterator status="rowstatus" value="secureEmailRecepientList"
		var="secureEmailBean">
		<s:form theme="simple" method="post" enctype="multipart/form-data">
			<input type="hidden" name=secureEmailBean.certificateAlias
				value='<s:text name="#secureEmailBean.certificateAlias" />' />
			<input type="hidden" name=operationMode value='STORE' />
			<tr
				class='<s:if test="#rowstatus.odd == true ">listTableWhite</s:if><s:else>listTableGrey</s:else>'>
				<th align="left" width="25%" class="listTable" scope="row"><s:text
					name="#secureEmailBean.certificateAlias" /></th>
				<td align="left" width="65%" class="listTable"><s:text
					name="#secureEmailBean.certificateDN" /></td>
				<td align="center" width="10%" class="listTable"><s:submit
					key="secureEmailBean.deleteButton"
					action="secureEmailRecipientDelete" cssClass="formButtonDelete" />
				</td>
			</tr>
			<s:token />
		</s:form>
	</s:iterator>
</table>
</body>
</html>
