<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Datos de usuario</title>
</head>

<body>

	<!--Cabecera-->
	<header>
		<div id="cabecera">
			<img src="imagen/logo.png" />

		</div>
	</header>

	<!--Menú superior-->
	<nav>
		<div id="navegar">
			<div id="tablon_boton">
				<a href="tablon"><span>Tablones</span></a>
			</div>
			<div id="usuario_boton">
				<a href="usuario"><span>Usuario</span></a>
			</div>
			<div id="salir_boton">
				<a href="login"><span>Salir</span></a>
			</div>
			<div id="busqueda">
				<form method="post" action="buscar">
					<p>
						<input type="text" id="busqueda" name="busqueda" value=""
							placeholder="Buscar tablón" required="required" maxlength="20" />

					</p>
					<p>
						<input type="submit" value="Buscar" id="submit" />
					</p>
				</form>
			</div>
		</div>
	</nav>

	<!--Contenido central-->
	<aside>
		<div id="cuerpo">
			<h1>Datos de usuario</h1>
			<div id="error">
				<p>${error}</p>
			</div>
			<div id="datos_usuario">
				<p>
					<label for="login"><strong>Nombre de usuario:</strong><span></span></label>
				</p>
				<p>${usuario_mostrar.username}</p>
				<p>
					<label for="nombre"><strong>Nombre:</strong><span></span></label>
				</p>
				<p>${usuario_mostrar.name}</p>
				<p>
					<label for="email"><strong>Email:</strong><span></span></label>
				</p>
				<p>${usuario_mostrar.email}</p>
			</div>

			<div id="eliminar_perfil">

				<form method="post" action="usuario">
					<p>
						<input type="submit" value="Eliminar perfil" id="submit"
							name="eliminar" />
					</p>
				</form>
				<form method="post" action="usuario">
					<p>
						<input type="submit" value="Cambiar contraseña" id="submit"
							name="cambiar_password" />
					</p>
				</form>

			</div>




			<div id="formulario_usuario">
				<form method="post" action="usuario">
					<p>
						<label for="login">Nombre de usuario: <span
							class="required"></span></label>
					</p>
					<p>
						<input type="text" id="login" name="login" value="" maxlength="20" />
					</p>
					<p>
						<label for="nombre">Nombre: <span class="required"></span></label>
					</p>
					<p>
						<input type="text" id="nombre" name="nombre" value=""
							maxlength="40" />
					</p>
					<p>
						<label for="email">Email: <span class="required"></span></label>
					</p>
					<p>
						<input type="email" id="email" name="email" value=""
							maxlength="50" />
					</p>
					<p>
						<input type="submit" value="Modificar" id="submit" name="cambiar" /><input
							type="reset" value="Borrar" id="submit" />
					</p>
				</form>
			</div>
		</div>
	</aside>
	<!--Pie -->
	<footer>
		<div id="pie">
			<p>Programación en Internet - GIIIS - 2013/2014</p>
		</div>
	</footer>
</body>
</html>
