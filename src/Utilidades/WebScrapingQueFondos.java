package Utilidades;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebScrapingQueFondos {

    private static final String URL_BASE = "https://www.quefondos.com/es/fondos/ficha/index.html?isin=";

    // Método para obtener los datos del fondo según el ISIN
    public static Map<String, Object> obtenerDatosFondo(String isin) {
        // Crear mapa para almacenar los resultados
        Map<String, Object> resultados = new HashMap<>();
        
        // Construir la URL completa con el ISIN proporcionado
        String url = URL_BASE + isin;

        try {
            // Realiza una solicitud HTTP y obtiene el documento
            Document doc = Jsoup.connect(url).get();

            // Obtener y almacenar los datos en el mapa
            resultados.put("nombre", obtenerNombreFondo(doc));
            resultados.put("cotizacion", obtenerCotizacion(doc));
            resultados.put("categoriaVDOS", obtenerCategoriaVDOS(doc));
            resultados.put("divisa", obtenerDivisa(doc));

        } catch (IOException e) {
            // Manejo de errores, se retornan mensajes adecuados
            resultados.put("nombre", "Error al acceder al sitio web");
            resultados.put("cotizacion", e.getMessage());
            resultados.put("categoriaVDOS", "Error al acceder a la categoría");
            resultados.put("divisa", "Error al acceder a la divisa");
        }

        return resultados;
    }

    // Método para obtener el nombre del fondo
    private static String obtenerNombreFondo(Document doc) {
        Element nombreElement = doc.select("div.informe h2").first();
        return nombreElement != null ? nombreElement.text() : "Nombre no encontrado";
    }

    // Método para obtener la cotización
    private static Object obtenerCotizacion(Document doc) {
        Element ultimaValoracion = doc.select("div.w100 h4:contains(Última valoración)").first();
        if (ultimaValoracion != null) {
            Element valorElement = ultimaValoracion.closest("div.w100").select("span.floatright").first();
            if (valorElement != null) {
                String valorStr = valorElement.text().replace(" EUR", "").trim();
                try {
                    return Double.valueOf(valorStr.replace(",", "."));
                } catch (NumberFormatException e) {
                    return "Valor liquidativo no válido";
                }
            }
        }
        return "Valor liquidativo no encontrado";
    }

    // Método para obtener la categoría VDOS
    private static String obtenerCategoriaVDOS(Document doc) {
        Element categoriaElement = doc.select("div.informe p span.floatleft:contains(Categoría VDOS:)").next().first();
        return categoriaElement != null ? categoriaElement.text() : "Categoría no encontrada";
    }

    // Método para obtener la divisa
    private static String obtenerDivisa(Document doc) {
        Element divisaElement = doc.select("div.informe p span.floatleft:contains(Divisa:)").next().first();
        return divisaElement != null ? divisaElement.text() : "Divisa no encontrada";
    }
}
