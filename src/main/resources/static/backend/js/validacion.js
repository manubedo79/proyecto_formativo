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
	$('form[id="formulariocliente"]').validate({
		rules:{
			nombre: 'required',
			apellido : 'required',
			direccion: 'required',
			telefono: 'required'			
		},
	 messages:{
			nombre: 'Por favor ingrese sus nombres completos',
			apellido : 'Por favor ingrese sus apellidos completos',
			direccion: 'Por favor ingrese su direccion de residencia',
			telefono: 'Por favor ingrese numero de telefono'
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
	
	$("#nombre").blur(function(){
		let uniquename=$("#nombre").val();
		$.ajax({
			url:"/obra/validar/nombre",
			data:"nombre="+uniquename,
			success: function(respuesta){
				if(respuesta=='Duplicate'){
					console.log(respuesta);
				$("#msgnombre").html("El nombre "+uniquename+ " Ya existe dentro de nuestro sistema");
				$("#nombre").focus();
				$("#btnconfirmar").prop("disabled", true);
				}else{
					$("#msgnombre").html("");
					$("#btnconfirmar").prop("disabled", false);
				}	
			}
		});
	});
	
	
});
$(function(){
	$('form[id="formulariocategoria"]').validate({
		rules:{
			nombrecategoria: 'required'	},
	 messages:{
		 	nombrecategoria: 'Por favor ingrese un nombre'	},
	submitHandler : function(form){
		form.submit();
	}
	});
	
});




