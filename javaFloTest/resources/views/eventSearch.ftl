<html>
<head>
<title>Personal Profile</title>
<link href="/css/searchingcss.css" rel="stylesheet" />

</head>
<body>

<div id="logo" >
  <div align="right">
    <table>   
      <tr id="account" >
        <td  valign="top" width="30" id="selfie">
        <img src="../pictures/20150410_120453000_iOS.jpg" style="max-width:30px;max-height:30px"></td>
        <td valign="top" id="account name"><label>Maneriar</label></td>
        <td width="10"></td>
        
        <td align="left">
<nav>
    <ul>
        <li>More
       <img src="../pictures/more.png" style="max-width:15px;max-height:15px">
            <ul>
                <li><a href="detailselfinfo.html">View SelfInfo</a></li>
                <li><a href="modification.html">Modify SelfInfon</a></li>
                <li><a href="pswchange.html">Change Password</a></li>
                 <li><a href="login.html">Log Out</a></li>
            </ul>
        </li>
    </ul>
</nav>
</td>    
      </tr>
    </table>
  </div>
  <form action="/search" id="search" method="post">
  <div align="center">
  <table align="center">
  <tr align="center" height="200px">
  <td height="200px"></td>
  </tr>
   <tr align="center">
  <td class="conditions">Category
  <input name="category" placeholder="i.e. concert" type="text"></td>
  <td class="conditions">Location
    <input name="location" placeholder="i.e. Arndale" type="text"></td><td class="conditions">
    Date<input name="date" placeholder="i.e. 2015-11-05" type="text"></td>
  </tr> 
  <tr height="20px"></tr>
  </table>
 </div>
 <div align="center">
  <table>
  <tr align="center">
  <td align="center">
<input id="putin"  name="keyword" placeholder="keyword" type="text" >

<button  id="button" name="search" type="submit">Search</button>
</tr>

  </table>


  
  </div>
  </div>
  </form>
</body>
</html>