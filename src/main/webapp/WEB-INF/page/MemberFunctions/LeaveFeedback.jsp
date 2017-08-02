<html>
<head>
	<link rel="stylesheet" type="text/css" href="../../../Harambase.css">
	<link rel="stylesheet" type="text/css" href="../../../DropdownMenu.css">
	<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
	<title>Leave feedback</title>
</head>
<body>

<h1>Leave feedback for the Seller</h1>



<form method="post" action="${basePath}/LeaveFeedback_action.do" name="AddFeedback">
<div id="table">
<table style="text-align: left; width: 30%;margin-right: auto; border="1"; cellpadding="2"; cellspacing="2"">

<tbody>
	    <tr>
	    	<td style="vertical-align: middle; text-align:left;" contenteditable='false'>Item Id</td>  
	    	<td style="vertical-align: middle; text-align:left;" contenteditable='false'>  
	    	<%=request.getParameter("ITEMID")%>
	    	<input name="itemID" type="hidden" value="<%=request.getParameter("ITEMID")%>"></td>
	    </tr>
	    <tr>
	    	<td style="vertical-align: middle; text-align:left;" contenteditable='false'>Item Name</td> 
	      	<td style="vertical-align: middle; text-align:left;" contenteditable='false'> 
	      	<%=request.getParameter("ITEMNAME")%>
	      	<input name="ItemName" type="hidden" value="<%=request.getParameter("ITEMNAME")%>"></td>
	    </tr>
	    <tr>
		    <td style="vertical-align: middle; text-align:left;" contenteditable='false'>Overall Rating</td>
		    <td style="vertical-align: middle; text-align:left;" contenteditable='false'>
		    <select name="overall">
			    <option value="1">1</option>
			    <option value="2">2</option>
			    <option value="3">3</option>
			    <option value="4">4</option>
			    <option value="5">5</option>
			    <option value="6">6</option>
			    <option value="7">7</option>
			    <option value="8">8</option>
			    <option value="9">9</option>
			    <option value="10">10</option>
			 </select>
			 </td>
		</tr>
		<tr>
		    <td style="vertical-align: middle; text-align:left;" contenteditable='false'>Item Quality</td>
		    <td style="vertical-align: middle; text-align:left;" contenteditable='false'>
		  		<input type="radio" name="itemQua" value="1"> 1
		  		<input type="radio" name="itemQua" value="2"> 2
		  		<input type="radio" name="itemQua" value="3"> 3
		  		<input type="radio" name="itemQua" value="4"> 4
		  		<input type="radio" name="itemQua" value="5"> 5
		  	</td>
		</tr>
		<tr>
			<td style="vertical-align: middle; text-align:left;" contenteditable='false'>Delivery</td>
			<td style="vertical-align: middle; text-align:left;" contenteditable='false'>
		  		<input type="radio" name="deli" value="1"> 1
		  		<input type="radio" name="deli" value="2"> 2
		  		<input type="radio" name="deli" value="3"> 3
		  		<input type="radio" name="deli" value="4"> 4
		  		<input type="radio" name="deli" value="5"> 5
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle; text-align:left;" contenteditable='false'>Comments</td>
			<td style="vertical-align: middle; text-align:left;" contenteditable='false'>
		  		<textarea rows="4" cols="50" name = "comment" style="width:100%"></textarea>
			</td>
	    </tr>   
	    
	
	
</tbody>
</table>
</div>
<input name="Submit" value="Rate" class="btnSearch2" style = "margin-left:18%" type="Submit">
</form>
</body>
</html>
