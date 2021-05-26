package springbootartacademy.models.entity;

import java.util.List;

public class CarritoCompras {
	public CarritoCompras() {
		// TODO Auto-generated constructor stub
	}
	private List<ArticuloCarrito> carritoitems;
	
	public CarritoCompras(List<ArticuloCarrito> carritoitems) {
		this.carritoitems = carritoitems;
	}

	public List<ArticuloCarrito> getCarritoitems() {
		return carritoitems;
	}

	public void setCarritoitems(List<ArticuloCarrito> carritoitems) {
		this.carritoitems = carritoitems;
	}
	public ArticuloCarrito buscarArticuloCarritoByCaracteristicas(Long id) {
		for(ArticuloCarrito item : this.carritoitems)
		{
			if(item.getCaracteristicas().getId().equals(id)) 
			{
				return item;
			}
		}
		return null;
	}
	public float total() {
		float total=0;

		for(ArticuloCarrito item : this.carritoitems)
		{
			
			total += (item.getCantidad().floatValue()*item.getCaracteristicas().getPrecio());
		}		
		return total;
	}
}
