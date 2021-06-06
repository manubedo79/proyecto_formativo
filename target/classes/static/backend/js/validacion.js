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
			telefono: {required:true,number: true, minlength:7}		
		},
	 messages:{
			nombre: 'Por favor ingrese sus nombres completos',
			apellido : 'Por favor ingrese sus apellidos completos',
			direccion: 'Por favor ingrese su direccion de residencia',
			telefono: {required:'Por favor ingrese su telefono', number: 'Este campo sólo permite números', minlength:'El teléfono permite mínimo 7 números'}
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
			nombrecategoria: 'required',
			fileImage:'required',
			},
			
	 messages:{
		 	nombrecategoria: 'Por favor ingrese un nombre',
		 	fileImage: 'Por favor seleccione una imagen'},
	submitHandler : function(form){
		form.submit();
	}
	});
	$("#nombrecategoria").blur(function(){
		let uniquename=$("#nombrecategoria").val();
		$.ajax({
			url:"/categoria/validar/nombre",
			data:"nombrecategoria="+uniquename,
			success: function(respuesta){
				if(respuesta=='Duplicate'){
					console.log(respuesta);
				$("#mensajerror").html("El nombre "+uniquename+ " ya existe dentro de nuestro sistema");
				$("#nombrecategoria").focus();
				$("#btnconfirmar").prop("disabled", true);
				}else{
					$("#mensajerror").html("");
					$("#btnconfirmar").prop("disabled", false);
				}	
			}
		});
	});
	
});




