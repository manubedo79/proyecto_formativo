package springbootartacademy.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import springbootartacademy.models.entity.Clientes;


public class ClientesPDFExporter {
	private List<Clientes> listaClientes;

	public ClientesPDFExporter(List<Clientes> listaClientes) {
		super();
		this.listaClientes = listaClientes;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();

		cell.setPhrase(new Phrase("Cliente Id"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre del cliente"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Apellido(s) del cliente"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Dirección"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Telefono"));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		
		for (Clientes clientes: listaClientes) {
			table.addCell(String.valueOf(clientes.getId()));
			table.addCell(clientes.getNombre());
			table.addCell(clientes.getApellido());
			table.addCell(clientes.getDireccion());
			table.addCell(clientes.getTelefono());
			


		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
	
	PdfWriter.getInstance(document,response.getOutputStream());
	
	document.open();
	
	document.add(new Paragraph("Lista de todos los clientes"));
	
	PdfPTable table = new PdfPTable(5);
	table.setWidthPercentage(100);
	table.setSpacingBefore(15);
	
	
	writeTableHeader(table);
	writeTableData(table);
	
	document.add(table);
	
	document.close();
	
	}
}
