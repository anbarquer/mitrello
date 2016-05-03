angular.module('myApp', [
	'ngRoute',
	'Filters',
	'Services',
	'Controllers'
]).
config(['$routeProvider', function($routeProvider,Controllers) {
	$routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginController'});
	$routeProvider.when('/listado-tablon', {templateUrl: 'partials/listado_tablones.html', controller: 'TablonListController'});
	$routeProvider.when('/detalle-tablon/:tablon_id', {templateUrl: 'partials/editar_tablon.html', controller: 'TablonDtlController'});
	$routeProvider.when('/crear-tablon', {templateUrl: 'partials/crear_tablon.html', controller: 'TablonCreatController'});
	$routeProvider.when('/listado-lista/:tablon_id', {templateUrl: 'partials/listado_listas.html', controller: 'ListaListController'});
	$routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeController'});
	$routeProvider.when('/registro', {templateUrl: 'partials/registro.html', controller: 'RegistroController'});
	$routeProvider.when('/detalle-lista/:lista_id', {templateUrl: 'partials/editar_lista.html', controller: 'ListaDtlController'});
	$routeProvider.when('/crear-lista', {templateUrl: 'partials/crear_lista.html', controller: 'ListaCreatController'});
	$routeProvider.when('/crear-tarjeta', {templateUrl: 'partials/crear_tarjeta.html', controller: 'TarjetaCreatController'});
	$routeProvider.when('/detalle-tarjeta/:tarjeta_id', {templateUrl: 'partials/editar_tarjeta.html', controller: 'TarjetaDtlController'});
	$routeProvider.otherwise({redirectTo: '/home'});
}]);
