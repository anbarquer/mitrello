var instablon_id = 0; // Para insertar nuevas listas,
var inslista_id = 0; //Para insertar tarjetas

// Listado ordenado por identificador
function mostrarListado(listado)
{
	if(typeof(listado[0])=="undefined")
		listado = [];
	else
		listado = _.sortBy(listado,"id");	
	return listado;
}

// Identificación del usuario
angular.module('Controllers', [])
	.controller('LoginController', ['$scope','$location','UsuariosFactory',
	    function($scope,$location,UsuariosFactory) {
			$scope.hacerLogin = function(){
				if($scope.usuario != undefined && $scope.password != undefined)
				{
					UsuariosFactory.get({ usuario: $scope.usuario, password: $scope.password })
						.$promise.then(function(data){
								if(data.ok == "true")
									$location.path('/listado-tablon');
								else
								{
									if(data.ok == "noregistrado")
										$scope.error_login = "El usuario no se encuentra registrado";
									else
										$scope.error_login = "La contraseña es incorrecta";
								}
						});
				}
				else
					$scope.error_login = "Los campos son incorrectos";
			};
	}])
	
	// Página principal del sitio
	.controller('HomeController', ['$scope',function( $scope ) {
	}])
	
	// Tablones del usuario
	.controller('TablonListController', ['$scope', 'TablonesFactory','TablonFactory','$location','$route',
	   function( $scope,TablonesFactory,TablonFactory,$location,$route) {
		
			$scope.editarTablon = function(tablon_id){
				$scope.nombretablon = "";
				$location.path('/detalle-tablon/' + tablon_id);
			};
			
			$scope.eliminarTablon = function(tablon_id){
				TablonFactory.borrar({ tablon_id: tablon_id }).$promise.then(function(respuesta){
					
					if(respuesta.ok == "true")
					{
						TablonesFactory.consultar_todos().$promise.then(function(data){					
							$scope.tabloncetes = mostrarListado(_([data.tablon]).flatten());
						});
					}
					else
					{
						$scope.error_eliminar_tablon = "No tiene permiso para eliminar este tablón";
					}
					
				});
			};
			
			$scope.crearTablon = function(){
				$location.path('/crear-tablon');
			};
			
			// Muestra los tablones cada vez que se cargue la plantilla
			TablonesFactory.consultar_todos().$promise.then(function(data){
				$scope.tabloncetes = mostrarListado(_([data.tablon]).flatten());
			});
				
	}])
	
	// Edición del nombre del tablón
	.controller('TablonDtlController', ['$scope','$routeParams','TablonFactory','$location',
	   function( $scope, $routeParams, TablonFactory, $location) {
			
		$scope.actualizarTablon = function(){
			if($scope.nombretablon != undefined)
			{
				TablonFactory.actualizar({tablon_id: $routeParams.tablon_id, nombre: $scope.nombretablon.trim()})
					.$promise.then(function(data){
						if(data.ok ==  "true")
							$location.path('/listado-tablon');
						else
						{
							if(data.ok == "duplicado")
								$scope.error_editar_tablon = "Ya existe un tablón con ese nombre";
							else
								$scope.error_editar_tablon = "No tiene permiso para editar este tablón";
						}
					});
			}
			else
				$scope.error_editar_tablon = "El nombre del tablón es incorrecto";
		};
		
		$scope.cancelarModTablon = function(){
			$location.path('/listado-tablon');
		};
		
	}])
	
	// Creación de un nuevo tablón
	.controller('TablonCreatController', ['$scope','TablonesFactory','$location',
	   function( $scope,TablonesFactory,$location ) {
		$scope.crearTablon = function(){
			if($scope.nombretablon != undefined)
			{
				TablonesFactory.crear({nombre: $scope.nombretablon.trim()})
				.$promise.then(function(data){
					if(data.ok ==  "true")
						$location.path('/listado-tablon');
					else
					{
						if(data.ok == "duplicado")
							$scope.error_crear_tablon = "Ya existe un tablón con ese nombre";	
					}
				});
			}
			else
				$scope.error_crear_tablon = "El nombre del tablón es incorrecto";
		};
		
		$scope.cancelarCrearTablon = function(){
			$location.path('/listado-tablon');
		};
	}])
	
	// Listas y tarjetas contenidas en cada tablón
	.controller('ListaListController', ['$scope','ListasFactory','$routeParams','$location',
	  'ListaFactory','TarjetasFactory','TarjetaFactory','TarjetaCambFactory','$route',
	     function( $scope,ListasFactory,$routeParams,$location,ListaFactory,TarjetasFactory,TarjetaFactory,TarjetaCambFactory,$route) {
						
			$scope.editarLista = function (lista_id){
				$location.path('/detalle-lista/'+ lista_id);
				
			};
			
			$scope.eliminarLista = function (lista_id){
				ListaFactory.borrar({lista_id : lista_id}).$promise.then(function(respuesta){
					
					if(respuesta.ok == "true")
					{
						$scope.listastablon = ListasFactory.consultar_todos
						({tablon_id: $routeParams.tablon_id}).$promise.then(function(data){
							$scope.listitas = mostrarListado(_([data.lista]).flatten());
							$scope.nombresLista = _.pluck($scope.listitas,"name");
						});
					}
					else
						$scope.error_eliminar_lista = "No tiene permiso para eliminar esta lista";
				});
			};
			
			$scope.crearLista = function(){
				instablon_id = $routeParams.tablon_id;
				$location.path('/crear-lista');
			};
			
			$scope.volverAtras = function(){
				window.history.back();
			};
			
			$scope.crearTarjeta = function(lista_id){
				inslista_id = lista_id;
				$location.path('/crear-tarjeta');
				
			};
			
			$scope.moverTarjeta = function(tarjeta_id,nombretarj){
				if(nombretarj != undefined)
				{
					_.map($scope.listitas,function(i){
						
						if(i.name == nombretarj)
						{
							TarjetaFactory.mover({lista_id : i.id, tarjeta_id: tarjeta_id}).$promise
							.then(function(){
								$scope.tarjetasLista = TarjetasFactory.consultar_todos
								().$promise.then(function(data){
									$scope.tarjetitas = mostrarListado(_([data.tarjeta]).flatten());
								});
							});
						}
					});
				}
			};
			
			$scope.eliminarTarjeta = function(tarjeta_id){
				TarjetaCambFactory.eliminar({tarjeta_id : tarjeta_id}).$promise
				.then(function(respuesta){
					if(respuesta.ok == "true")
					{
						$scope.tarjetasLista = TarjetasFactory.consultar_todos
						().$promise.then(function(data){
							$scope.tarjetitas = mostrarListado(_([data.tarjeta]).flatten());					
						});
					}
					else
						$scope.error_eliminar_tarjeta = "No tiene permiso para eliminar esta tarjeta";					
				});
			};
			
			$scope.editarTarjeta = function(tarjeta_id){
				$location.path('/detalle-tarjeta/'+ tarjeta_id);
				
			};
			
			$scope.listastablon = ListasFactory.consultar_todos
			({tablon_id: $routeParams.tablon_id}).$promise.then(function(data){
				$scope.listitas = _([data.lista]).flatten();
				if(typeof($scope.listitas[0])=="undefined")
					$scope.listitas = [];
				else
				{
					$scope.listitas = _.sortBy($scope.listitas,"id");
					// Nombre de las listas para la elección a la hora de mover las tarjetas
					$scope.nombresLista = _.pluck($scope.listitas,"name");
					$scope.tarjetasLista = TarjetasFactory.consultar_todos
					().$promise.then(function(data){
						$scope.tarjetitas = mostrarListado(_([data.tarjeta]).flatten());
					});
				}
			});
			
	}])
	
	// Edición del nombre de la lista
	.controller('ListaDtlController', ['$scope','ListaFactory','$routeParams','$location',
     function($scope,ListaFactory,$routeParams,$location) {
		
		$scope.actualizarLista = function(){
			if($scope.nombrelista != undefined)
			{
				ListaFactory.actualizar({lista_id : $routeParams.lista_id, nombre: $scope.nombrelista.trim()})
				.$promise.then(function(data){
					if(data.ok ==  "true")
						window.history.back();
					else
					{
						if(data.ok == "duplicado")
							$scope.error_editar_lista = "Ya existe una lista con ese nombre";
						else
							$scope.error_editar_lista = "No tiene permiso para editar esta lista";
					}
				});
			}
			else
				$scope.error_editar_lista = "El nombre de la lista es incorrecto";
		};
		
		$scope.cancelarModLista = function(){
			window.history.back();
		};
	}])
	
	// Creación de una nueva lista
	.controller('ListaCreatController', ['$scope','ListasFactory','$routeParams',
	     function( $scope,ListasFactory,$routeParams) {
		
		$scope.crearLista = function(){
			if($scope.nombrelista != undefined)
			{
				ListasFactory.crear({nombre : $scope.nombrelista.trim(), tablon_id: instablon_id})
					.$promise.then(function(data){
						if(data.ok == "true")
							window.history.back();
						else
						{
							if(data.ok == "duplicado")
								$scope.error_crear_lista = "Ya existe una lista con ese nombre";	
						}
					});
			}
			else
				$scope.error_crear_lista = "El nombre de la lista es incorrecto";
		};
		
		$scope.cancelarCrearLista = function(){
			window.history.back();
		};
	}])
	
	// Creación de una nueva tarjeta
	.controller('TarjetaCreatController', ['$scope','TarjetasFactory',
	   function($scope,TarjetasFactory){
		
		$scope.crearTarjeta = function(){
			
			if($scope.nombretarjeta != undefined)
			{
				TarjetasFactory.crear({nombre: $scope.nombretarjeta.trim(), lista_id: inslista_id})
					.$promise.then(function(data){
						if(data.ok == "true")
							window.history.back();
						else
						{
							if(data.ok = "duplicado")
								$scope.error_crear_tarjeta ="Ya existe una tarjeta con ese nombre";
							
						}
				});
			}
			else
				$scope.error_crear_tarjeta = "El nombre de la tarjeta es incorrecto";
		};
		
		$scope.cancelarCrearTarjeta = function(){
			window.history.back();
		};
		
	}])
	
	// Edición del nombre de la tarjeta
	.controller('TarjetaDtlController', ['$scope','TarjetaCambFactory','$routeParams',
	   function( $scope,TarjetaCambFactory,$routeParams) {
		
		$scope.actualizarTarjeta = function(){
			
			if($scope.nombretarjeta != undefined)
			{
				TarjetaCambFactory.actualizar({tarjeta_id: $routeParams.tarjeta_id, nombre:$scope.nombretarjeta.trim()})
					.$promise.then(function(data){
						if(data.ok == "true")
							window.history.back();
						if(data.ok == "duplicado")
							$scope.error_editar_tarjeta = "Ya existe una tarjeta con ese nombre";
						else
							$scope.error_editar_tarjeta= "No tiene permiso para editar esta tarjeta";
					});
			}
			else
				$scope.error_editar_tarjeta = "El nombre de la tarjeta es incorrecto";
		};
		
		$scope.cancelarModTarjeta = function(){
			window.history.back();
		};
		
	}])
	
	// Registro de un nuevo usuario
	.controller('RegistroController', ['$scope','RegistroFactory','$location',
	    function( $scope, RegistroFactory,$location) {					
			$scope.registrarse = function(){
				
			if(($scope.nombre != undefined && $scope.usuario != undefined && $scope.password != undefined && $scope.email != undefined))
			{
				
				RegistroFactory.registro({
					nombre: $scope.nombre.trim(), 
					usuario: $scope.usuario.trim(), 
					password: $scope.password.trim(), 
					email: $scope.email.trim()})
						.$promise.then(function(data){
							if(data.ok == "true")
							{
								$location.path("/login");
							}
							else
								$scope.error_registro = "El nombre de usuario ya se encuentra registrado";
					});		
			}
			else
				$scope.error_registro = "Existen errores en los campos de registro";
			};
	}]);
