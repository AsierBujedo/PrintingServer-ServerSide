package CustomLogger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* CustomLogger
 * UNIVERSIDAD DE DEUSTO
 */

public class CustomLogger {
	static PrintStream ps;
	static File f;

	/*
	 * M?todo create(), debe llamarse siempre al inicio del programa para generar el
	 * archivo (log)
	 */
	public boolean create(String name) {
		f = new File(name);
		try {
			ps = new PrintStream(f);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			ps.println("[BEGIN] CUSTOMLOGGER | FILE: " + name + " " + dtf.format(LocalDateTime.now()));
			return true;
		} catch (IOException e) {
			ps.println(e.toString());
			return false;
		}
	}

	/*
	 * M?todo close(), debe llamarse siempre al final del programa para cerrar el
	 * archivo (log) una vez cerrado, no se podr? modificar.
	 */
	public boolean close() {
		ps.print("[END] END OF FILE");
		ps.close();
		return true;
	}

	/*
	 * M?todo log(), debe ser llamado a la hora de querer loguear algo, antes debe
	 * usarse el m?todo create() al inicio del programa una sola vez. La gravedad
	 * est? definida por el enum "LEVEL"
	 */
	public boolean log(LEVEL lvl, String str) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		if (lvl == LEVEL.BEGIN) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[BEGIN]  " + str);
			return true;
		} else if (lvl == LEVEL.DONE) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[DONE]  " + str);
			return true;
		} else if (lvl == LEVEL.END) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[END]  " + str);
			return true;
		} else if (lvl == LEVEL.ERROR) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[ERROR]  " + str);
			return true;
		} else if (lvl == LEVEL.INFO) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[INFO]  " + str);
			return true;
		} else if (lvl == LEVEL.OK) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[OK]  " + str);
			return true;
		} else if (lvl == LEVEL.WARN) {
			ps.println("[" + dtf.format(LocalDateTime.now()) + "]" + "[WARN]  " + str);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * M?todo readlog(), puede ser llamado en cualquier momento, lee el archivo log
	 * y lo imprime por pantalla
	 */
	public boolean readlog() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String ln = reader.readLine();
			while (ln != null) {
				System.out.println(ln);
				ln = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("CANNOT LOCATE THE FILE");
			ps.println(e.toString());
		}
		return true;
	}

}
