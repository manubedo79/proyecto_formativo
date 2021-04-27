$(function(){
	$('form[id="registro"]').validate({
		rules:{
			correo: 'required',
			roles : 'required',
			
		}
	messages:{
		correo: 'Por favor ingrese su correo',
		roles : 'Por favor seleccione un rol'
	}
	});
});
		
		