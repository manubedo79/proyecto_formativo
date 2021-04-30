$(function(){
	$('form[id="registrousuario"]').validate({
		rules:{
			nombre: 'required',
			apellido : 'required',
			direccion : 'required',
			telefono : 'required',
			departamento : 'required',
			municipios : 'required',
			correo : 'required',
			contraseña: 'required',
			
		},
	 messages:{
		 nombre: 'Por favor ingrese su nombre',
			apellido : 'Por favor ingrese su apellido(s)',
			direccion : 'Por favor ingrese su dirección',
			telefono : 'Por favor ingrese su telefono',
			departamento : 'Por favor seleccione un departamento',
			municipios : 'Por favor seleccione un municipio',
			correo : 'Por favor ingrese su correo electronico',
			contraseña: 'Por favor ingrese su contraseña'
			
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	
});

$(function(){

	$('form[id="formularioobra"]').validate({
		rules:{
			nombre: 'required',
			descripcion: 'required',
			tamaño: 'required',
			stock: 'required',
			precio: 'required',
			imagenobra: 'required',
			categoria: 'required'
	},
	 messages:{
			nombre: 'Por favor ingrese un nombre',
			descripcion: 'Por favor ingrese un descripcion',
			tamaño: 'Por favor ingrese un tamaño',
			stock: 'Por favor ingrese un stock',
			precio: 'Por favor ingrese un precio',
			imagenobra: 'Por favor ingrese una imagen principal',
			categoria: 'Por favor elija una categoria'
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	

});	

