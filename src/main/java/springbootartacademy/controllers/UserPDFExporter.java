package springbootartacademy.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import springbootartacademy.models.entity.Usuarios;

public class UserPDFExporter {
	private List<Usuarios> listaUsuarios;

	public UserPDFExporter(List<Usuarios> listaUsuarios) {
		super();
		this.listaUsuarios = listaUsuarios;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();

		cell.setPhrase(new Phrase("Usuario ID"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Rol"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Estado"));
		table.addCell(cell);
		
	}
	
	private void writeTableData(PdfPTable table) {
		
		for (Usuarios usuarios: listaUsuarios) {
			table.addCell(String.valueOf(usuarios.getId()));
			table.addCell(usuarios.getCorreo());
			table.addCell(usuarios.getRoles().toString());
			table.addCell(String.valueOf(usuarios.isEstado()));

		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
	
	PdfWriter.getInstance(document,response.getOutputStream());
	
	document.open();
	
	document.add(new Paragraph("Lista de todos los usuarios"));
	
	PdfPTable table = new PdfPTable(5);
	table.setWidthPercentage(100);
	table.setSpacingBefore(15);
	
	
	writeTableHeader(table);
	writeTableData(table);
	
	document.add(table);
	
	document.close();
	
	}
}
