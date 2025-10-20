package net.salesianos.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileHelper {

    // lee archivo y devuelve todas las líneas
    public static ArrayList<String> readFile(File file) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("archivo no encontrado: " + file.getName());
        } catch (IOException e) {
            System.out.println("error leyendo el archivo " + file.getName());
        }
        return lines;
    }
}
