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

import springbootartacademy.models.entity.Obras;

public class ObrasPDFExporter {
	private List<Obras> listaObras;

	public ObrasPDFExporter(List<Obras> listaObras) {
		super();
		this.listaObras = listaObras;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();

		cell.setPhrase(new Phrase("Obras ID"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Estado"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Descripcion"));
		table.addCell(cell);
		
	}
	
	private void writeTableData(PdfPTable table) {
		
		for (Obras obras: listaObras) {
			
			table.addCell(String.valueOf(obras.getId()));
			table.addCell(obras.getNombre());
			table.addCell(String.valueOf(obras.isEstado()));
			table.addCell(obras.getDescripcion());

		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
	
	PdfWriter.getInstance(document,response.getOutputStream());
	
	document.open();
	
	document.add(new Paragraph("Lista de las obras"));
	
	PdfPTable table = new PdfPTable(4);
	table.setWidthPercentage(100);
	table.setSpacingBefore(15);
	
	
	writeTableHeader(table);
	writeTableData(table);
	
	document.add(table);
	
	document.close();
	
	}
}
