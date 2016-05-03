<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Registro</title>
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
			<h1>Crea una cuenta</h1>
			<div id="error">
				<p>${error}</p>
			</div>
			<div id="formulario">
				<form method="post" action="registro">
					<p>
						<label for="login">Nombre de usuario: <span
							class="required"></span></label>
					</p>
					<p>
						<input type="text" id="login" name="login" value=""
							placeholder="Nombre de usuario" required="required"
							autofocus="autofocus" maxlength="20" />
					</p>
					<p>
						<label for="nombre">Nombre: <span class="required"></span></label>
					</p>
					<p>
						<input type="text" id="nombre" name="nombre" value=""
							placeholder="Nombre" required="required" maxlength="40" />
					</p>
					<p>
						<label for="email">Email: <span class="required"></span></label>
					</p>
					<p>
						<input type="email" id="email" name="email" value=""
							placeholder="tu@email.com" required="required" maxlength="50" />
					</p>
					<p>
						<label for="password">Contraseña: <span class="required"></span></label>
					</p>
					<p>
						<input type="password" id="password" name="password" value=""
							placeholder="**********" required="required" maxlength="40" />
					</p>
					<p>
						<input type="submit" value="Crear cuenta" id="submit" /><input
							type="reset" value="Borrar" id="submit" />
					</p>
				</form>
				<p>
					¿Ya tienes cuenta? <a href="login"> Login</a>
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
