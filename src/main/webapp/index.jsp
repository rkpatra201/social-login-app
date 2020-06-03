<html>
<body>
<h2>Sign Up</h2>
<form method="POST" action="signup">
<label>Name: <input type="text" name="name"/></label>
<label>Email: <input type="text" name="email"/></label>
<label>Mobile: <input type="text" name="phone"/></label>
<label>Password: <input type="text" name="password"/></label>
<button type="submit">Signup</button>
</form>
<h2>Login</h2>
<form method="POST" action="login?provider=DATABASE">
<label>Mobile: <input type="text" name="phone"/></label>
<label>Password: <input type="text" name="password"/></label>
<button type="submit">Login</button>
</form>
<a href="login?provider=GITHUB">Login With Github</a>
<br/>
<h1>Authenticated API</h1>
<a href="api/users?action=list">All Users</a><br/>
<a href="api/users">Logged In User</a><br/>
<a href="api/logout">Logout</a><br/>
<h2>Update Password</h2>
<form method="POST" action="api/users">
<label>Password: <input type ="password" name ="password"/></label>
<button type="submit">Update Password</button>
</form>
</body>
</html>
