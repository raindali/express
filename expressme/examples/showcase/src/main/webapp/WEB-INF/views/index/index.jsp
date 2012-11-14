<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/taglibs/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Index Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx}/static/jquery/mobile/jquery.mobile-1.2.0.min.css" />
<script src="${ctx}/static/jquery/jquery.js"></script>
<script src="${ctx}/static/jquery/mobile/jquery.mobile-1.2.0.min.js"></script>
</head>
<body>
	<div data-role="page">
		<div data-role="header">
			<h1>登录</h1>
		</div>
		<!-- /header -->
		<div data-role="content">
			<form action="${ctx}/dali/index/1" method="post">
				<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
					<label for="email" class="ui-input-text">邮箱:</label>
					<input type="text" name="email" id="email" placeholder="email" value="" class="ui-input-text ui-body-c ui-corner-all ui-shadow-inset">
					<label for="password" class="ui-input-text">密码:</label>
					<input type="password" name="password" id="password" placeholder="password" value="" class="ui-input-text ui-body-c ui-corner-all ui-shadow-inset">
					<input type="submit" value="登录" />
				</div>
			</form>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->
</body>
</html>