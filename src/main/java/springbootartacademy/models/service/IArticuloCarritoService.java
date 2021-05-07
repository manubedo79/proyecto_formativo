package springbootartacademy.models.service;

import java.util.List;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;

public interface IArticuloCarritoService {
public List<ArticuloCarrito> articuloCarritos(Usuarios usuarios);
}
