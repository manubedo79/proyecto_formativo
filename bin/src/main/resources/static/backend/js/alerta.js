	function desactivar(nombre){
	swal({
		  title: "Esta Seguro que quiere desactivar?",
		  
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((OK) => {
		  if (OK) {
			  $.ajax({
				  url:"/cambiarEstado/"+nombre,
				  success: function(respuesta){
					console.log(repuesta);  
				  }
			  })
		    swal("Se desactivo de forma correcta!", {
		      icon: "success",
		    }).then((ok)=>{
		    	if(ok){
		    		location.href="/ListaUsuarios";
		    	}
		    });
		  } else {
		    swal("No se pudo desactivar!");
		  }
		});
}