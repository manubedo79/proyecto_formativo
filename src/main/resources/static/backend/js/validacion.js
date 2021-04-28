	jQuery.validator.addMethod("noSpace", function(value, element) { 
		    return value == '' || value.trim().length != 0;  
		}, "No space please and don't leave it empty");
		jQuery.validator.addMethod("customEmail", function(value, element) { 
		  return this.optional( element ) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test( value ); 
		}, "Please enter valid email address!");
		$.validator.addMethod( "alphanumeric", function( value, element ) {
		return this.optional( element ) || /^\w+$/i.test( value );
		}, "Letters, numbers, and underscores only please" );
		var $registrationForm = $('#registro');
		if($registrationForm.length){
		  $registrationForm.validate({
		      rules:{
		          roles: {
		              required: true
		          },
		          correo: {
		              required: true,
		              customEmail: true
		          },
		          contraseña: {
		              required: true
		          },
		      },
		      messages:{
		          roles: {
		              //error message for the required field
		              required: 'Por favor seleccione un rol!'
		          },
		          correo: {
		              required: 'Por favor ingrese su correo!',
		              correo: 'Por favor ingrese un correo valido!'
		          },
		          contraseña: {
		              required: 'Por favor ingrese su contraseña!'
		          }
		      },
		     
		  });
		}
		
		jQuery.validator.addMethod("noSpace", function(value, element) { 
		    return value == '' || value.trim().length != 0;  
		}, "No space please and don't leave it empty");
		jQuery.validator.addMethod("customEmail", function(value, element) { 
		  return this.optional( element ) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test( value ); 
		}, "Please enter valid email address!");
		$.validator.addMethod( "alphanumeric", function( value, element ) {
		return this.optional( element ) || /^\w+$/i.test( value );
		}, "Letters, numbers, and underscores only please" );
		var $registrationForm = $('#editar');
		if($registrationForm.length){
		  $registrationForm.validate({
		      rules:{
		          roles: {
		              required: true
		          },
		          correo: {
		              required: true,
		              customEmail: true
		          }
		      },
		      messages:{
		          roles: {
		              //error message for the required field
		              required: 'Por favor seleccione un rol!'
		          },
		          correo: {
		              required: 'Por favor ingrese su correo!',
		              correo: 'Por favor ingrese un correo valido!'
		          }
		      },
		     
		  });
		}