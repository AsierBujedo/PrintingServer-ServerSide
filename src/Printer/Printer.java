package Printer;

import java.io.*;
import java.util.Arrays;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.Sides;
import CustomLogger.*;
import ServerSide.Main;

public class Printer {
	public void getPrinters() {
		Main.logger.log(LEVEL.INFO, "Lista de impresoras solicitadas...");
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		System.out.println("Impresoras: " + printServices.length);

		for (PrintService printer : printServices) {
			System.out.println("-> " + printer.getName());
			Main.logger.log(LEVEL.INFO, "-> " + printer.getName());
		}
	}

	public boolean printPDF(File f) {
		Main.logger.log(LEVEL.INFO, "Método .printPDF lanzado...");
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
		DocFlavor flavor2 = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		patts.add(Sides.DUPLEX);
		Main.logger.log(LEVEL.INFO, "Obteniendo lista de impresoras...");
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor2, patts);
		System.out.println(ps);
		if (ps.length == 0) {
			System.out.println("No hay impresoras disponibles o no están configuradas");
			Main.logger.log(LEVEL.ERROR, "Impresora no encontrada");
			throw new IllegalStateException("Impresora no encontrada");
		}
		System.out.println("Impresoras disponibles: " + Arrays.asList(ps));

		PrintService myService = null;
		for (PrintService printService : ps) {
			System.out.println(printService.getName());
			if (printService.getName().equals("HP LaserJet Pro MFP M125nw")) {
				myService = printService;
				Main.logger.log(LEVEL.INFO, "¡Se ha obtenido la impresora correctamente!");
				break;
			}
		}

		if (myService == null) {
			Main.logger.log(LEVEL.ERROR, "Impresora no encontrada");
			throw new IllegalStateException("Impresora no encontrada");
		}

		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
			DocPrintJob printJob = myService.createPrintJob();
			printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
			fis.close();
			Main.logger.log(LEVEL.DONE, "Archivo enviado correctamente a la impresora");
			return true;
		} catch (IOException | PrintException e) {
			Main.logger.log(LEVEL.ERROR, "Imposible imprimir, más detalles: "+ e.toString());
			return false;
		}
		
	}

}
