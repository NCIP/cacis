<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><s:text name="cdwUserBean.title" /></title>
<link href="cacisweb.css" rel="stylesheet" type="text/css">
<s:head />
</head>
<body class="mainframe">
<table class="headerTable">
	<tr>
		<td class="headerTable"><s:text name="cdwUserBean.title" /></td>
	</tr>
</table>
<s:include value="messageAndError.jsp"></s:include>

<s:form theme="simple" method="post" enctype="multipart/form-data">
	<table class="formTable">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th class="formTable"><s:text name="cdwUserBean.username" /></th>
			<td><s:textfield key="cdwUserBean.username" size="55"
				cssClass="formTable" /></td>
		</tr>
		<tr>
			<th class="formTable"><s:text name="cdwUserBean.password" /></th>
			<td><s:textfield key="cdwUserBean.password" size="55"
				cssClass="formTable" /></td>
		</tr>
		<tr>
			<td colspan="2" class="formButton"><s:submit
				key="cdwUserBean.addUserButton" action="cdwUserAdd"
				cssClass="formButton" />&nbsp;&nbsp;&nbsp;&nbsp;<s:submit
				key="cdwUserBean.searchButton" action="cdwUserPermissionSearch"
				cssClass="formButton" />&nbsp;&nbsp;&nbsp;&nbsp;<s:submit
				key="cdwUserBean.deleteUserButton" action="cdwUserDelete"
				cssClass="formButtonDelete" /></td>
		</tr>
	</table>

	<table class="listTable">
		<tr>
			<th class="listTable" width="25%"><s:text
				name="cdwPermissionBean.studyID" /></th>
			<th class="listTable" width="25%"><s:text
				name="cdwPermissionBean.siteID" /></th>
			<th class="listTable" width="25%"><s:text
				name="cdwPermissionBean.patientID" /></th>
			<th class="listTable" width="25%"><s:text
				name="cdwUserBean.action" /></th>
		</tr>

		<tr class='listTableWhite'>
			<td align="center" width="25%" class="listTable"><s:textfield
				key="cdwPermissionBean.studyID" size="35" cssClass="formTableList" /></td>
			<td align="center" width="25%" class="listTable"><s:textfield
				key="cdwPermissionBean.siteID" size="35" cssClass="formTableList" /></td>
			<td align="center" width="25%" class="listTable"><s:textfield
				key="cdwPermissionBean.patientID" size="35" cssClass="formTableList" /></td>
			<td colspan="2" width="25%" class="formButton"><s:submit
				key="cdwUserBean.addPermissionButton" action="cdwPermissionAdd"
				cssClass="formButton" /></td>
		</tr>

		<s:iterator status="rowstatus" value="cdwUserBean.userPermission"
			var="cdwPermissionBean">
			<s:url action="cdwPermissionDelete" var="deleteLink">
				<s:param name="cdwUserBean.username">
					<s:text name="cdwUserBean.username" />
				</s:param>
				<s:param name="cdwPermissionBean.studyID">
					<s:text name="#cdwPermissionBean.studyID" />
				</s:param>
				<s:param name="cdwPermissionBean.siteID">
					<s:text name="#cdwPermissionBean.siteID" />
				</s:param>
				<s:param name="cdwPermissionBean.patientID">
					<s:text name="#cdwPermissionBean.patientID" />
				</s:param>
				<s:param name="operationMode">STORE</s:param>
			</s:url>
			<tr
				class='<s:if test="#rowstatus.odd == true ">listTableGrey</s:if><s:else>listTableWhite</s:else>'>
				<td align="center" width="25%" class="listTable"><s:text
					name="#cdwPermissionBean.studyID" /></td>
				<td align="center" width="25%" class="listTable"><s:text
					name="#cdwPermissionBean.siteID" /></td>
				<td align="center" width="25%" class="listTable"><s:text
					name="#cdwPermissionBean.patientID" /></td>
				<td align="center" width="25%" class="listTable"><a
					href="${deleteLink}">
				<button class="tableRowDelete"><s:text
					name="cdwUserBean.deletePermissionButton" /></button>
				</a></td>
			</tr>
		</s:iterator>
	</table>
	<s:hidden name="operationMode" value="STORE"></s:hidden>
</s:form>
</body>
</html>
