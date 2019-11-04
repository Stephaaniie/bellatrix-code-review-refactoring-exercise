# Bellatrix Code Review / Refactoring exercise
Please review the following code snippet. Assume that all referenced assemblies have been properly included. 
The code is used to log different messages throughout an application. We want the ability to be able to log to a text file, the console and/or the database. Messages can be marked as message, warning or error. We also want the ability to selectively be able to choose what gets logged, such as to be able to log only errors or only errors and warnings. 
1.	If you were to review the following code, what feedback would you give? Please be specific and indicate any errors that would occur as well as other best practices and code refactoring that should be done. 
2.	Rewrite the code based on the feedback you provided in question 1. Please include unit tests on your code.


```html
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map dbParams;
	private static Logger logger;

	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");  
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
				+ ":" + dbParams.get("portNumber") + "/", connectionProps);

		int t = 0;
		if (message && logMessage) {
			t = 1;
		}

		if (error && logError) {
			t = 2;
		}

		if (warning && logWarning) {
			t = 3;
		}

		Statement stmt = connection.createStatement();

		String l = null;
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();
		
		if (error && logError) {
			l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (warning && logWarning) {
			l = l + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (message && logMessage) {
			l = l + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		
		if(logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		}
		
		if(logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, messageText);
		}
		
		if(logToDatabase) {
			stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
		}
	}
}

```

## Respuesta
* No existen separaciones de capas
* No se usan interfaces, las mismas nos permiten denotar un comportamiento de lo que queremos realizar, y separarlo de su implementacion. Si quiero implementar un Config Logger a través de una clase, debería contemplar esto.
* Cualquier error propaga una excepción generica poco entendible de lo que sucede. Se podrían crear excepciones mas especificas.
* No manejamos adecuadamente la conexión hacia una BD, entre sus errores no nos aseguramos de cerrar las conexiones.
* No informamos si existe un error al impactar la BD.  
* Se definen variables que no se usan.
* No es del todo claro lo que se guarda en la BD, contabilizamos en una int el nivel de log que necesitamos y guardamos en la BD, este numero en lugar del level. A su vez maneja una jerarquia distinta de niveles, como INFO (MESSAGE), ERROR Y WARNING
* Se hace un trim del mensaje pero no se asigna a una variable
* Si hacemos un trim de un nulo arroja una excepcion, no tiene sentido hacerlo y luego validar por nulos
* Valida si el size es = 0, pero existe un metodo .isEmpty que se encarga de ello.
* l esta definia como nula, y luego concatena textos, siempre va a ir el nulo al principio
* Uso de un map de datos de BD para almacenar info relacionada al FileLogger Appender
* Crea un File y luego valida si existe. Por otro no checkea si existe la propiedad o captura el error en caso que no exista
* Barra del path esta invertida
* No modulariza, en 3 if hace lo mismo, podría sacar a un metodo dicho codigo
* Lo que contatena no lo usa, luego loggea solo el mensaje.
* Define en el constructor de la clase, lo mismo que pasa por parametros en el metodo.
* El metodo no sigue las Namming Convetions (LogMessage)
* Mal uso de variables estaticas, no es correcto asignar las mismas mediante constructores. Ademas se compartiran.
* Demasiado parametros en un metodo.
* Variables inentendibles, muy cortas, hay que seguir el codigo para adivinar que significan o para que se usan.

Asumo que solo tenemos que refactorizar la clase, manteniendo su estructura original (tanto en el constructor como en el metodo).
Por otro lado entiendo que el ejercicio es para ejercitar la refactorización, pero para el manejo de logs, podemos utilizar logback y perfilar como queremos loguear la info (levels and appenders), también podemos hacer uso de la fachada slf4j e independizar el framework que utilicemos para loguear.
Si el proyecto a su vez, utiliza springboot, podemos hacer uso de los Actuator para modificar los levels sin necesidad de reiniciar la app (adjunto la dependencia de actuator, y un ejemplo de logback)

# Info
This application was generated using Spring Boot 2.2.0.RELEASE.
 
## Testing

To launch your application's tests, run:

    ./mvnw clean test




