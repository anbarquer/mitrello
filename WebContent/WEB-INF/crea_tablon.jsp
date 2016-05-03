<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Nuevo tablón</title>
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
			<h1>Nuevo tablón</h1>
			<div id="error">
				<p>${error}</p>
			</div>
			<div id="formulario">
				<form method="post" action="crea_tablon">

					<p>
						<label for="nombre_tablon">Nombre del tablón:<span
							class="required"></span></label>
					</p>
					<p>
						<input type="text" id="nombre_tablon" name="nombre_tablon"
							value="" placeholder="Nombre del tablón" required="required"
							autofocus="autofocus" maxlength="40" />
					</p>
					<p>
						<input type="submit" value="Crear tablón" id="submit"
							name="cambiar" /><input type="reset" value="Borrar" id="submit" />
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
