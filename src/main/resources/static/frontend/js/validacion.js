$(function(){
	 $.validator.addMethod("formEmail", function (value, element) {
	     var pattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
	     return this.optional(element) || pattern.test(value);
	  }, "Formato del correo incorrecto");
	$('form[id="registro"]').validate({
		rules:{
			nombre: 'required',
			apellido : 'required',
			direccion : 'required',
			telefono : {required:true,number: true, minlength:7},
			departamento : 'required',
			municipios : 'required',
			correo : {required:true,email: true, formEmail:true},
			contraseña: {required:true,minlength: 8}
			
		},
	 messages:{
		 nombre: 'Por favor ingrese su nombre',
			apellido : 'Por favor ingrese su apellido',
			direccion : 'Por favor ingrese su dirección',
			telefono : {required:'Por favor ingrese su telefono', number: 'Este campo sólo permite números', minlength:'El teléfono permite mínimo 7 números'},
			departamento : 'Por favor seleccione un departamento',
			municipios : 'Por favor seleccione un municipio',
			correo : {required:'Por favor ingrese su correo electronico',email:'Formato del correo incorrecto'},
			contraseña: { required:'Por favor ingrese su contraseña',minlength:'La contraseña debe de tener como mínico 8 caráteres'}
			
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	$("#correo").blur(function(){
		let uniqueemail=$("#correo").val();
		$.ajax({
			url:"/usuario/validar/correo",
			data:"correo="+uniqueemail,
			success: function(respuesta){
				if(respuesta=='Duplicate'){
					console.log(respuesta);
				$("#msgcorreo").html("El correo "+uniqueemail+ " ya existe dentro de nuestro sistema");
				$("#correo").focus();
				$("#login").prop("disabled", true);
				}else{
					$("#msgcorreo").html("");
					$("#login").prop("disabled", false);
				}	
			}
		});
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


$(function(){
	$('form[id="iniciosesion"]').validate({
		rules:{
			correo : 'required',
			password: 'required',
		},
	 messages:{
			correo : 'Por favor ingrese su correo electronico',
			password: 'Por favor ingrese su contraseña'
			
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	
});



$(function(){

	$('form[id="recuperacion"]').validate({
		rules:{
			correo : {required:true,email: true}
	},
	 messages:{
				correo : {required:'Por favor ingrese su correo electronico',email:'ingrese un correo valido'},
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	

});	



$(function(){

	$('form[id="formularioContraseña"]').validate({
		rules:{
			password: {required:true,minlength: 8},
			confirmar: {required:true}
	},
	 messages:{
				password: { required:'Por favor ingrese su contraseña',minlength:'La contraseña debe de tener como mínico 8 caráteres'},
				confirmar: { required:'Por favor ingrese su contraseña'}
	},
	submitHandler : function(form){
		form.submit();
	}
	});
	

});	