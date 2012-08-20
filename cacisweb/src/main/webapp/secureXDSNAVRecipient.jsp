<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><s:text name="secureXDSNAVBean.title" /></title>
<link href="cacisweb.css" rel="stylesheet" type="text/css">
<s:head />
</head>
<body class="mainframe">
<table class="headerTable">
	<tr>
		<td class="headerTable"><s:text name="secureXDSNAVBean.title" /></td>
	</tr>
</table>
<s:include value="messageAndError.jsp"></s:include>

<s:form action="secureXDSNAVRecipientAdd" theme="simple" method="post"
	enctype="multipart/form-data">
	<table class="formTable">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th class="formTable"><s:text
				name="secureXDSNAVBean.certificateAlias" /></th>
			<td><s:textfield key="secureXDSNAVBean.certificateAlias"
				size="55" cssClass="formTable" /></td>
		</tr>
		<tr>
			<th class="formTable"><s:text
				name="secureXDSNAVBean.certificate" /></th>
			<td><s:file key="secureXDSNAVBean.certificate" size="55"
				cssClass="formTable" /></td>
		</tr>
		<tr>
			<td colspan="2" class="formButton"><s:submit
				key="secureXDSNAVBean.addButton" cssClass="formButton" /></td>
		</tr>
	</table>
	<s:hidden name="operationMode" value="STORE"></s:hidden>
	<s:token />
</s:form>
<table class="listTable">
	<tr>
		<th class="listTable" width="25%"><s:text
			name="secureXDSNAVBean.certificateAlias" /></th>
		<th class="listTable" width="65%"><s:text
			name="secureXDSNAVBean.certificateDN" /></th>
		<th class="listTable" width="10%"><s:text
			name="secureXDSNAVBean.action" /></th>
	</tr>
	<s:iterator status="rowstatus" value="secureXDSNAVRecepientList"
		var="secureXDSNAVBean">
		<s:form theme="simple" method="post" enctype="multipart/form-data">
			<input type="hidden" name=secureXDSNAVBean.certificateAlias
				value='<s:text name="#secureXDSNAVBean.certificateAlias" />' />
			<input type="hidden" name=operationMode value='STORE' />
			<tr
				class='<s:if test="#rowstatus.odd == true ">listTableWhite</s:if><s:else>listTableGrey</s:else>'>
				<td align="left" width="25%" class="listTable"><s:text
					name="#secureXDSNAVBean.certificateAlias" /></td>
				<td align="left" width="65%" class="listTable"><s:text
					name="#secureXDSNAVBean.certificateDN" /></td>
				<td align="center" width="10%" class="listTable"><s:submit
					key="secureXDSNAVBean.deleteButton"
					action="secureXDSNAVRecipientDelete" cssClass="formButtonDelete" />
				</td>
			</tr>
			<s:token />
		</s:form>
	</s:iterator>
</table>
</body>
</html>
