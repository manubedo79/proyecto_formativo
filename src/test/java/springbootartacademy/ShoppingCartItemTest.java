package springbootartacademy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import springbootartacademy.models.dao.IArticuloCarritoDao;
import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Usuarios;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ShoppingCartItemTest {

	@Autowired
	private IArticuloCarritoDao iarticulodao;
	@Autowired
	private TestEntityManager entityManager;
	@Test
	public void testAddOneCart() {
		Caracteristicas caracteristicas = entityManager.find(Caracteristicas.class, 1);
		Usuarios usuarios = entityManager.find(Usuarios.class, 1);
		ArticuloCarrito articuloCarrito = new ArticuloCarrito();
		articuloCarrito.setCaracteristicas(caracteristicas);
		articuloCarrito.setUsuarios(usuarios);
		articuloCarrito.setCantidad(5);
		ArticuloCarrito cartitem= iarticulodao.save(articuloCarrito);
		assertTrue(cartitem.getId() > 0);
		
	}
}
