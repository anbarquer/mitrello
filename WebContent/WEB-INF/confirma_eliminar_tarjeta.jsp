<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Borrado de tarjeta</title>
</head>

<body>

	<!--Cabecera-->
	<header>
		<div id="cabecera">
			<img src="imagen/logo.png" />

		</div>
	</header>

	<!--Menú superior-->
	<nav></nav>

	<!--Contenido central-->
	<aside>
		<div id="cuerpo">
			<h1>¿Está seguro?</h1>
			<div id="formulario">
				<form method="post" action="confirma_eliminar_tarjeta">
					<p>Todos los datos asociados a su tarjeta se perderán.</p>
					<p>
						<input type="submit" value="Si" id="submit" name="si" /><input
							type="submit" value="No" id="submit" name="no" />
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