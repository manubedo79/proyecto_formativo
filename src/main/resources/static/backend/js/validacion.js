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
		
		