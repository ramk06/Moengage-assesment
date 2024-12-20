<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<style>
body {
	font-family: Arial, sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	background-color: #e9f5ff;
}

.container {
	width: 300px;
	padding: 20px;
	background: white;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	text-align: center;
}

.container h2 {
	margin-bottom: 20px;
}

.form-group {
	margin-bottom: 15px;
	text-align: left;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

.form-group input {
	width: 50%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

.button {
	width: 100%;
	padding: 10px;
	background-color: #007bff;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
}

.button:hover {
	background-color: #0056b3;
}

.link {
	display: block;
	margin-top: 10px;
	text-decoration: none;
	color: #007bff;
}

.link:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Login</h2>
		<form action="login" method="post">
			<div class="form-group">
				<label for="username">Username</label> <input type="text"
					id="username" name="username" placeholder="Enter your username"
					required>
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" placeholder="Enter your password"
					required>
			</div>
			<button type="submit" class="button">Login</button>
		</form>
		<a href="signup.jsp" class="link">Don't have an account? Sign up</a>
	</div>
</body>
</html>
