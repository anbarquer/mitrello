<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Login</title>
</head>

<body>

	<!-- Cabecera-->
	<header>
		<div id="cabecera">
			<img src="imagen/logo.png" />

		</div>
	</header>

	<!--Menú superior-->
	<nav>
		<div id="navegar">
			<div id="home_boton">
				<a href="home"><span>Home</span></a>
			</div>
			<div id="registro_boton">
				<a href="registro"><span>Registro</span></a>
			</div>
			<div id="login_boton">
				<a href="login"><span>Login</span></a>
			</div>
		</div>
	</nav>

	<!--Contenido central-->
	<aside>
		<div id="cuerpo">
			<h1>Introduce tus credenciales</h1>

			<div id="error">
				<p>${error}</p>
			</div>

			<div id="formulario">
				<form method="post" action="login">
					<p>
						<label for="login">Nombre de usuario: <span
							class="required"></span></label>
					</p>
					<p>
						<input type="text" id="nombre" name="login" value=""
							placeholder="Nombre de usuario" required="required"
							autofocus="autofocus" maxlength="20" />
					</p>
					<p>
						<label for="password">Contraseña: <span class="required"></span></label>
					</p>
					<p>
						<input type="password" id="password" name="password" value=""
							placeholder="**********" required="required" />
					</p>
					<p>
						<input type="submit" value="Login" id="submit" /><input
							type="reset" value="Borrar" id="submit" maxlength="40" />
					</p>
				</form>
				<p>
					¿No tienes cuenta? <a href="registro"> Regístrate</a>
				</p>
			</div>
		</div>
	</aside>

	<!-- Pie -->
	<footer>
		<div id="pie">
			<p>Programación en Internet - GIIIS - 2013/2014</p>
		</div>
	</footer>
</body>
</html>
