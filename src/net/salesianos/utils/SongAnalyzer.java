package net.salesianos.utils;

import java.io.File;
import java.util.ArrayList;

public class SongAnalyzer {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java SongAnalyzer <rutaArchivo>");
            return;
        }

        File inputFile = new File(args[0]);
        ArrayList<String> lines = FileHelper.readFile(inputFile);

        if (lines.isEmpty()) {
            System.out.println("archivo inválido: " + inputFile.getName());
            return;
        }

        String mostPopularSong = "";
        long maxPlays = 0;
        long totalPlays = 0;

        // 🔹 Recorremos todas las canciones
        for (String line : lines) {
            try {
                String[] parts = line.split("\\|\\|");
                String songName = parts[0].trim();
                String playsPart = parts[1].replace("Reproducciones", "").trim();
                playsPart = playsPart.replace(",", "").trim();
                long plays = Long.parseLong(playsPart);

                totalPlays += plays; // suma total para calcular la media
                if (plays > maxPlays) { // buscamos la canción más popular
                    maxPlays = plays;
                    mostPopularSong = songName;
                }

            } catch (Exception e) {
                System.out.println("Error en la línea: " + line);
            }
        }

        // 🔹 Cálculo de la media de reproducciones
        long average = totalPlays / lines.size();

        // 🔹 Mostramos resultados
        System.out.println("Archivo: " + inputFile.getName());
        System.out.println("Promedio de reproducciones: " + average);
        System.out.println("Canción más popular: " + mostPopularSong + " (" + maxPlays + " reproducciones)");
    }
}
