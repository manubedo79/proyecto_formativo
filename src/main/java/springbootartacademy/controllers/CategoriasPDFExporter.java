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

import springbootartacademy.models.entity.Categorias;

public class CategoriasPDFExporter {
	private List<Categorias> listaCategorias;

	public CategoriasPDFExporter(List<Categorias> listaCategorias) {
		super();
		this.listaCategorias = listaCategorias;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();

		cell.setPhrase(new Phrase("Categorias Id"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre de la categoria"));
		table.addCell(cell);
		
		
	}
	
	private void writeTableData(PdfPTable table) {
		
		for (Categorias categorias: listaCategorias) {
			table.addCell(String.valueOf(categorias.getId()));
			table.addCell(categorias.getNombrecategoria());
			
			


		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
	
	PdfWriter.getInstance(document,response.getOutputStream());
	
	document.open();
	
	document.add(new Paragraph("Lista de todas las categorias"));
	
	PdfPTable table = new PdfPTable(2);
	table.setWidthPercentage(100);
	table.setSpacingBefore(15);
	
	
	writeTableHeader(table);
	writeTableData(table);
	
	document.add(table);
	
	document.close();
	
	}
}
