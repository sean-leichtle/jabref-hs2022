package org.jabref.logic.formatter.translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.sun.istack.NotNull;

import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;

public class Translator {

    /**
     * Furnishes a German version of an input text
     * Note:    {@param gASTarget} must point to a valid
     *          url targeting a JavaScript app on the
     *          Google App Script platform which in turn
     *          interfaces with the Google Translate API
     *
     * @param text Input text (of any language) to be translated
     * @return German translation of {@param text}
     * @throws IOException if {@param text} is null or I/O
     *          operations are interrupted
     */
    public static String translate(String text) throws IOException {

        String gASTarget = "https://script.google.com/macros/s/AKfycbxhrSDQtChaPn4YxDoO5MO1icuNNx56sSSTCdBqTgJzDYrE2Q0/exec";
        String urlStr = gASTarget + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&target=de&source=";

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return unescapeHtml4(response.toString());
    }

    /**
     * Helper method for {@link #translate(String)}
     * Detects system OS and formats String accordingly
     *
     * NOTE:    This method is obviated by usage of the StandardCharsets.UTF_8
     *          argument in the BufferedReader as well as the unescapeHtml4 method
     *          in the {@link #translate(String)} method
     *
     * @param response String of translation to be formatted
     * @return formatted String
     */
    public static String formatResponse(StringBuilder response) {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) {
            return new String(response.toString().getBytes(StandardCharsets.UTF_16LE), StandardCharsets.UTF_16LE);
        } else if (osName.toLowerCase().contains("mac")) {
            return new String(response.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } else {
            return response.toString();
        }
    }

    /**
     * Allows usage of the Translator class as a command line interface
     * Compile with javac and run with 'java Translator [TEXT TO BE
     * TRANSLATED]'
     *
     * @param args the text to be translated
     * @throws IOException if {@param args} is null or I/O operations
     *          are interrupted
     */
    public static void main(@NotNull String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg + " ");
        }
        String text = sb.toString();
        System.out.println("Deutsch: " + translate(text));
    }
}
