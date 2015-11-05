<%@ language="javascript"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Step two</title>
<link href="registcss1.css" rel="stylesheet" type="text/css"/>
</head>

<body style="background-image:url(../pictures/BGpicture.jpg);">
<form id="register" method="post" action="regist2.asp">

<fieldset id="RegistInput">

<h1>Step Two</h1>
<%
Response.Write("<input name='username' type='hidden' value='");
Response.Write(Request.Form("username")); 
Response.Write("'></input>");
%> 

<label for="firstname"> 
Your First Name
</label>
<input id="firstname" type="text" name="firstname" placeholder="firstname">
<label for="lastname"> 
Your Last Name
</label>
<input id="lastname" type="text" name="lastname" placeholder="lastname">

<label for="email"> 
Your E-mail Address
</label>
<input id="email" type="text" name="email" placeholder="e-mail address">

<label for="date"> 
Date of Birth
</label>
<input name="date" class="datepicker" data-ideal="date" type="date" placeholder="DD/MM/YY"/>

<label for="gender"> 
Your Gender
</label>
<select id="gender" name="gender">
	<option>Male</option>
    <option>Female</option>
</select>


</fieldset>

<div>
<label>Upload avatar:</label><input id="file" name="file" multiple type="file"/>
</div>


<fieldset id="actions"> 
<a href="regist.html">
<input type="button" id="previous" value="Previous">
</a>

<input type="submit" id="submit" value="Next"> 

</fieldset> 
</form>
</body>
</html>