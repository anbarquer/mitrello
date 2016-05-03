<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="css/stylesheet.css" rel="stylesheet" type="text/css">
<title>miTrello - Listas</title>
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
			<h1>Listas de ${nombre_tablon}</h1>
			<p>
				<a href="crea_lista?tablon_id=${tablon_id}">Nueva lista</a>
			</p>
			<!-- Listas del tablón -->
			<c:forEach var="lista" items="${listas}">
				<div id="lista">
					<p>
						<strong>${lista.name}</strong>
					<p>
					<p>
						<a href="crea_tarjeta?lista_id=${lista.id}&tablon_id=${tablon_id}"><img
							src="imagen/add.gif" alt="Añadir" /></a>
					</p>


					<c:forEach var="tarjeta" items="${tarjetas}">
						<c:if test="${tarjeta.list == lista.id}">

							<div id="tarjeta">

								<a href="modifica_tarjeta?tarjeta_id=${tarjeta.id}&tablon_id=${tablon_id}">${tarjeta.name}</a>

								<div id="eliminar_tarjeta">
									<p>
										<a href="confirma_eliminar_tarjeta?tarjeta_id=${tarjeta.id}&tablon_id=${tablon_id}"><img
											src="imagen/close.gif" alt="Eliminar" border="0" /></a>
									</p>
								</div>

								<form method="post" action="modifica_tarjeta">
									<select name="seleccion">
										<c:forEach var="idlista" items="${listas}">
											<option value="${idlista.id}">${idlista.name}</option>
										</c:forEach>
									</select> <input type="submit" value="Cambiar" id="submit"
										name="mover" /> <input type="hidden" name="tarjeta_id"
										value="${tarjeta.id}">
										<input type="hidden" name="tablon_id"
										value="${tablon_id}">
								</form>
							</div>

						</c:if>
					</c:forEach>

				<div id="opcioneslista">

					<p>
						<a href="modifica_lista?lista_id=${lista.id}&tablon_id=${tablon_id}"><span>Modificar
								lista</span></a>
					</p>
					<p>
						<a href="confirma_eliminar_lista?lista_id=${lista.id}&tablon_id=${tablon_id}"><span>Eliminar
								lista</span></a>
						</p>		
						<p><a href="limpiar_lista?lista_id=${lista.id}&tablon_id=${tablon_id}"><span>Limpiar lista</span></a></p>
					
					</div>
				</div>
			</c:forEach>
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