<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>
<h:form	enctype="multipart/form-data">
				<h:panelGrid	border="0"	columns="2">
						<h:panelGrid	border="0"	columns="1">
								<h:panelGrid	border="0"	columns="2">
										<h:inputFile	value="#{album.file}"/>
										<h:commandButton	value="Upload"	action="#{album.upload}"/>
								</h:panelGrid>
								<h:panelGrid	border="0"	columns="2">
										<h:inputFile	value="#{album.file}"/>
										<h:commandButton	value="Download"	action="#{album.download}"/>
								</h:panelGrid>
								<h:panelGrid	border="0"	columns="2">
										<h:selectOneListbox	value="#{album.display}">
												<f:selectItems	value="#{album.photos}"/>
										</h:selectOneListbox>
									 <h:commandButton	value="display"	action="#{album.displayImage}"></h:commandButton>
								</h:panelGrid>
								<h:panelGrid	border="0"	columns="2">
										<h:selectOneListbox	value="#{album.delete}">
												<f:selectItems	value="#{album.photos}"/>
										</h:selectOneListbox>
										<h:commandButton	value="delete"	action="#{album.deleteFiles}"></h:commandButton>
								</h:panelGrid>
						</h:panelGrid>
						<h:graphicImage	url="#{album.photoURL}"	style="margin-left:	1px;	max-width:	400px;	margin-top:	1px;	margin-bottom:	1px;	max-height:	400px;	margin-right:	
1px;	border:	1px	solid;"></h:graphicImage>
				</h:panelGrid>
		</h:form>
</f:view>
</body>
</html>