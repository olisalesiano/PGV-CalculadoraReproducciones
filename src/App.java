import java.io.*;
import java.util.*;

public class App {

    private static final String ANALYZER_CLASS = "net.salesianos.utils.SongAnalyzer";

    public static void main(String[] args) throws IOException, InterruptedException {

        File inputDir = new File("src/files/inputs");

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.out.println("no se encontró la carpeta de input: " + inputDir.getPath());
            return;
        }

        File[] inputFiles = inputDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (inputFiles == null || inputFiles.length == 0) {
            System.out.println("no hay archivos en carpeta input");
            return;
        }

        List<Process> processes = new ArrayList<>();
        List<String> results = new ArrayList<>();

        // se h ace un subproceso para cada archivo
        for (File file : inputFiles) {
            ProcessBuilder builder = new ProcessBuilder(
                    "java", "-Dfile.encoding=UTF-8", "-cp", "bin", ANALYZER_CLASS,
                    file.getPath());

            builder.redirectErrorStream(true);
            Process process = builder.start();
            processes.add(process);

            // captura la salida para mostrarla después
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "UTF-8"))) {
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                results.add(output.toString());
            }
        }

        // espera todos los subprocesos
        for (Process p : processes) {
            p.waitFor();
        }

        // muestra resultados finales
        System.out.println("\n==== RESULTADOS FINALES ====\n");
        for (String res : results) {
            System.out.println(res);
            System.out.println("--------------------------");
        }
    }
}
