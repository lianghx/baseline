<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>安全基线checklist</title>

<link href="/baseline/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/baseline/css/bootstrap-responsive.min.css" rel="stylesheet" />
    
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet" />
    <link href="/baseline/css/font-awesome.css" rel="stylesheet" />
    
    <link href="/baseline/css/adminia.css" rel="stylesheet" /> 
    <link href="/baseline/css/adminia-responsive.css" rel="stylesheet" /> 
    
    <link href="/baseline/css/pages/dashboard.css" rel="stylesheet" /> 
</head>
<body>
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 				
			</a>
			
			<a class="brand" href="./">Adminia Admin</a>
			
			<div class="nav-collapse">
			
				<ul class="nav pull-right">
					<li>
						<a href="#"><span class="badge badge-warning">7</span></a>
					</li>
					
					<li class="divider-vertical"></li>
					
					<li class="dropdown">
						
						<a data-toggle="dropdown" class="dropdown-toggle " href="#">
							Rod Howard <b class="caret"></b>							
						</a>
						
						<ul class="dropdown-menu">
							<li>
								<a href="./account.html"><i class="icon-user"></i> Account Setting  </a>
							</li>
							
							<li>
								<a href="./change_password.html"><i class="icon-lock"></i> Change Password</a>
							</li>
							
							<li class="divider"></li>
							
							<li>
								<a href="./"><i class="icon-off"></i> Logout</a>
							</li>
						</ul>
					</li>
				</ul>
				
			</div> <!-- /nav-collapse -->
			
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->




<div id="content">
	
	<div class="container">
		
		<div class="row">
			
			<div class="span3">
				
				<div class="account-container">
				
					<div class="account-avatar">
						<img src="./img/headshot.png" alt="" class="thumbnail" />
					</div> <!-- /account-avatar -->
				
					<div class="account-details">
					
						<span class="account-name">Rod Howard</span>
						
						<span class="account-role">Administrator</span>
						
						<span class="account-actions">
							<a href="javascript:;">Profile</a> |
							
							<a href="javascript:;">Edit Settings</a>
						</span>
					
					</div> <!-- /account-details -->
				
				</div> <!-- /account-container -->
				
				<hr />
				
				<ul id="main-nav" class="nav nav-tabs nav-stacked">
					
					<li class="active">
						<a href="./">
							<i class="icon-home"></i>
							Dashboard 		
						</a>
					</li>
					
					<li>
						<a href="./faq.html">
							<i class="icon-pushpin"></i>
							FAQ	
						</a>
					</li>
					
					<li>
						<a href="./plans.html">
							<i class="icon-th-list"></i>
							Pricing Plans		
						</a>
					</li>
					
					<li>
						<a href="./grid.html">
							<i class="icon-th-large"></i>
							Grid Layout	
							<span class="label label-warning pull-right">5</span>
						</a>
					</li>
					
					<li>
						<a href="./charts.html">
							<i class="icon-signal"></i>
							Charts	
						</a>
					</li>
					
					<li>
						<a href="./account.html">
							<i class="icon-user"></i>
							User Account							
						</a>
					</li>
					
					<li>
						<a href="./login.html">
							<i class="icon-lock"></i>
							Login	
						</a>
					</li>
					
				</ul>	
				
				<hr />
				
		
			</div> <!-- /span3 -->
			
			
			
			<div class="span9">
				
				<h1 class="page-title">
					<i class="icon-home"></i>
					Dashboard					
				</h1>
				
				
				<div class="widget widget-table">
										
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Table</h3>
					</div> <!-- /widget-header -->
					
					<div class="widget-content">
					
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Username</th>
									<th>Company</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							
							<tbody>
								<tr>
									<td>1</td>
									<td>Michael</td>
									<td>Jordan</td>
									<td>@mjordan</td>
									<td>Chicago Bulls</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>					
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Magic</td>
									<td>Johnson</td>
									<td>@mjohnson</td>
									<td>Los Angeles Lakers</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>3</td>
									<td>Charles</td>
									<td>Barkley</td>
									<td>@cbarkley</td>
									<td>Phoenix Suns</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>4</td>
									<td>Karl</td>
									<td>Malone</td>
									<td>@kmalone</td>
									<td>Utah Jazz</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>					
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>5</td>
									<td>David</td>
									<td>Robinson</td>
									<td>@drobinson</td>
									<td>San Antonio Spurs</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>6</td>
									<td>Reggie</td>
									<td>Miller</td>
									<td>@rmiller</td>
									<td>Indiana Pacers</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>7</td>
									<td>Clyde</td>
									<td>Drexler</td>
									<td>@cdrexler</td>
									<td>Portland Trail Blazers</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
								<tr>
									<td>8</td>
									<td>Hakeem</td>
									<td>Olajuwon</td>
									<td>@holajuwon</td>
									<td>Houston Rockets</td>
									<td class="action-td">
										<a href="javascript:;" class="btn btn-small btn-warning">
											<i class="icon-ok"></i>								
										</a>						
										<a href="javascript:;" class="btn btn-small">
											<i class="icon-remove"></i>						
										</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div> <!-- /widget-content -->
					<dir><ul id="pagination-demo" class="pagination-sm"></ul></dir>
					
				</div> <!-- /widget -->
				
				
			</div> <!-- /span9 -->
			
			
		</div> <!-- /row -->
		
	</div> <!-- /container -->
	
</div> <!-- /content -->
					
	
<div id="footer">
	
	<div class="container">				
		<hr />
		<p>&copy; 2012 Go Ideate.</p>
	</div> <!-- /container -->
	
</div> <!-- /footer -->


<script src="/baseline/js/jquery-1.7.2.min.js"></script>
<script src="/baseline/js/bootstrap.js"></script>

<script src="/baseline/js/jquery.twbsPagination.min.js"></script>
<script type="text/javascript">
$('#pagination-demo').twbsPagination({
    totalPages: 5,
    visiblePages: 10,
    onPageClick: function (event, page) {
        alert(page)
    }
});
</script>
</body>
</html>