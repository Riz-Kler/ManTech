<%@ language="javascript"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Complete Page</title>
<link href="registcss2.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<form id="register" style="background-image:url(../pictures/BGpicture.jpg);">
<h1>Complete</h1>

<div align="center">
  <img src=../pictures/<% 
Response.Write(Request.Form("file")) 
%>  height=100px />
  <table id="info">
    <tr>
    <td width="62">
    <div align="right">
    <font size="3px" align="right">Username:</font>
    </div>
    </td>
    <td id="username" width="168">
<% 
Response.Write(Request.Form("username")) 
%> 
    </td>
    </tr>
    <tr>
    <td>
    <div align="right">
    <font size="3px" align="right">E-mail:</font>
    </div>
    </td>
    <td id="email">
    <% 
Response.Write(Request.Form("email")) 
%> 
    </td>
    </tr>
    <tr>
    <td>
    <div align="right">
    <font size="3px" align="right">Name:</font>
    </div>
    </td>
    <td>
    <% 
Response.Write(Request.Form("firstname")+"  "+Request.Form("lastname")) 
%> 
    </td>
    </tr>
    <tr>
    <td>
    <div align="right">
    <font size="3px" align="right">Birthday:</font>
    </div>
    </td>
    <td>
    <% 
Response.Write(Request.Form("date")) 
%> 
    </td>
    </tr>
    <tr>
    <td>
    <div align="right">
    <font size="3px" align="right">Gender:</font>
    </div>
    </td>
    <td>
    <% 
Response.Write(Request.Form("gender")) 
%> 
    </td>
    </tr>
</table>
  
  
  
  
</div>
<fieldset id="actions">
  <p>&nbsp;    </p>
  <p>
  <a href="regist1.asp">
     <input type="button" id="previous" value="Previous" />
     </a>
     <a href="login.html">
    <input type="button" id="submit" value="Finish">
    </a>
  </p> 
</fieldset> 
</form>
</body>
</html>