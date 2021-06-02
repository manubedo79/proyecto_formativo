package springbootartacademy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootartacademy.models.dao.IObrasDao;
import springbootartacademy.models.service.IObrasService;
import springbootartacademy.models.service.IUsuariosService;

@RestController

public class UsuariosRestController {
@Autowired
private IUsuariosService ususervice;
@Autowired
private IObrasService obraser;

@PostMapping("/usuario/check_email")
public String checkDuplicateEmail(@Param("correo") String correo) {
	
return ususervice.iscorreounique(correo) ? "OK" : "Duplicado";  	
}

}
