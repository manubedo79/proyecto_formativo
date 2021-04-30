$(function(){
	$('form[id="registro"]').validate({
		rules:{
			correo: 'required',
			roles : 'required',
			contraseña: 'required',
			
		},
	 messages:{
		correo: 'Por favor ingrese su correo',
		roles : 'Por favor seleccione un rol',
		contraseña : 'Por favor ingrese su contraseña'
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
		