var Services = angular.module('Services', [ 'ngResource' ]);

Services.factory('TablonFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/tablones/:tablon_id', {}, {
		mostrar : 		{method : 'GET',	params: {tablon_id : '@tablon_id'}},
		actualizar : 	{method : 'PUT',	params: {tablon_id : '@tablon_id', nombre: '@nombre'}},
		borrar : 		{method : 'DELETE', params:	{tablon_id : '@tablon_id'}}
	});
} ]);

Services.factory('TablonesFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/tablones/:tablon_id', {}, {
		consultar_todos :			{ method : 'GET',	params : {tablon_id : ''}},
		crear : 					{ method : 'POST',  params : {nombre: '@nombre'}}
			
	});
} ]);

Services.factory('ListaFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/listas/:lista_id', {}, {
		mostrar : 		{method : 'GET',	params: {lista_id : '@lista_id'}},
		actualizar : 	{method : 'PUT',	params: {lista_id  : '@lista_id', nombre: '@nombre'}},
		borrar : 		{method : 'DELETE', params:	{lista_id : '@lista_id'}}
	});
} ]);

Services.factory('ListasFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/listas/:tablon_id', {}, {
		consultar_todos :	{ method : 'GET',	params : {tablon_id : '@tablon_id'}},
		crear : 			{ method : 'POST',  params : {nombre: '@nombre', tablon_id : '@tablon_id'}}
	});
} ]);

Services.factory('UsuariosFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/usuarios/', {}, {});
} ]);

Services.factory('TarjetaFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/tarjetas/:lista_id', {}, {
		mover : {method : 'PUT',	params: {lista_id  : '@lista_id', tarjeta_id: '@tarjeta_id'}},
		
		
	});
} ]);

Services.factory('TarjetaCambFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/tarjetas/:tarjeta_id', {}, {
		actualizar : 	{method : 'GET',	params: {tarjeta_id  : '@tarjeta_id', nombre: '@nombre'}},
		eliminar : 		{method : 'DELETE',	params: {tarjeta_id  : '@tarjeta_id'}},
	});
} ]);

Services.factory('TarjetasFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/tarjetas/:lista_id', {}, {
		consultar_todos :	{method	: 'GET', params : {lista_id : ''}},
		crear :				{method	: 'POST', params : {lista_id : '@lista_id', nombre: '@nombre'}}
		
	});
} ]);

Services.factory('RegistroFactory', [ '$resource', function($resource) {
	return $resource('/mitrello/rest/usuarios/', {}, {
		registro: {method: 'POST', params : {
											nombre: '@nombre', 
											usuario: '@usuario', 
											password: '@password', 
											email: '@email'}},
		consultar: {method: 'GET'}
	});
} ]);

