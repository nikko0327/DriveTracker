<%@include file="navbar.jsp" %>

		<br>
		
		<div id="page_spinner" hidden>
		  		<center><i class="icon-spinner icon-spin icon-3x"></i></center>
		</div>
		
		<form class="form-horizontal">
		    <input placeholder = "Customer Name" type="text" name="NAME" id= "name">
		    <button type="submit" class="btn btn-primary" id="search">Search</button>
        </form>

		<div id="customer_list">
			&nbsp;
		</div>

		<input id="username" value='<%= session.getAttribute("username") %>' hidden>
		<input id="email" value='<%= session.getAttribute("mail") %>' hidden>

		<hr>
		<div class="footer">
			<p>&copy; proofpoint 2016</p>
		</div>
	</div>
	<!-- /container -->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/bootbox.js"></script>

	<script src="js/customerInfo_script.js"></script>
</body>
</html>
