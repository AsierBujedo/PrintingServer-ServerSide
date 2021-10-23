package ServerSide;

import java.io.IOException;
import Printer.Printer;
import CustomLogger.*;

public class Main {
	public static CustomLogger logger = new CustomLogger();
	
	public static void main(String[] args) {
		logger.create("log.log");
		try {
			new Printer().getPrinters();
			while(true) {
			new Sockets().ServerInit(4444);
			}
		} catch (IOException e) {
		}
	}

}
