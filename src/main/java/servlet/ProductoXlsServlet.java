package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoServices;
import service.ProductosServicesImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Clase ProductoXlsServlet.
 *
 * @author Genesis
 * @version 1.0
 * @since 2025-11-10
 *
 * Descripción:
 * Este servlet genera una tabla HTML con el listado de productos y permite exportarla
 * como archivo Excel (.xls). Utiliza la implementación {ProductosServicesImplement}
 * para obtener los datos desde el modelo {Producto}.
 * Está mapeado a dos rutas: <b>/productos.html</b> y <b>/productos.xls</b>.
 * Dependiendo de la extensión solicitada, la respuesta se mostrará como página HTML
 * o se descargará como archivo de Excel.
 */
@WebServlet({"/productos.xls", "/productos.html"})
public class ProductoXlsServlet extends HttpServlet {

    /**
     * Metodo que gestiona las peticiones HTTP GET.
     * @param req  Objeto {HttpServletRequest} que contiene la solicitud del cliente.
     * @param resp Objeto {HttpServletResponse} que permite enviar la respuesta al cliente.
     * @throws ServletException Si ocurre un error general del servlet.
     * @throws IOException Si ocurre un error de entrada o salida durante el proceso.
     * Descripción:
     * Este metodo obtiene la lista de productos del servicio, determina si la salida
     * debe mostrarse como HTML o como Excel, y genera dinámicamente una tabla con los datos.
     * Si se accede desde <b>/productos.xls</b>, se envía el archivo Excel como descarga.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Se crea un objeto del servicio que implementa la interfaz ProductoServices
        ProductoServices service = new ProductosServicesImplement();

        // Se obtiene la lista de productos usando el metodo listar()
        List<Producto> productos = service.listar();

        //revisar si el usuario tiene una cookie activa (sesion)
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        Optional<String> usernameCookie = Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();


        // Verificamos si la ruta termina en ".xls"
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");

        // Si la ruta es .xls, se configura la respuesta para descarga en formato Excel
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=productos.xls");
        } else {

            // Tipo de contenido por defecto: HTML
            resp.setContentType("text/html;charset=UTF-8");
        }

        // Escribimos la salida (HTML o Excel)
        try (PrintWriter out = resp.getWriter()) {

            // Si no es Excel, generamos la estructura completa de la página HTML
            if (!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Listado Productos</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Listado de Productos</h1>");

                // Sección superior con enlaces + nombre de admin si hay sesión
                out.println("<div class='links'>");
                out.println("<a href='" + req.getContextPath() + "/productos.xls'>Exportar a Excel</a>");
                out.println("<a href='" + req.getContextPath() + "/productojson'>Mostrar Json</a>");

                // Si el usuario logueado es admin, lo mostramos con color
                if (usernameCookie.isPresent() && usernameCookie.get().equalsIgnoreCase("admin")) {
                    out.println("<span class='admin'> | Sesión activa como ADMIN</span>");
                } else if (usernameCookie.isPresent()) {
                    out.println("<span> | Sesión activa: " + usernameCookie.get() + "</span>");
                } else {
                    out.println(" ");
                }
                out.println("</div>");
            }


            // Se construye la tabla de productos
            out.println("<div class='tabla-container'>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>TIPO</th>");
            out.println("<th>PRECIO</th>");
            out.println("</tr>");

            // Recorremos la lista de productos y generamos una fila por cada uno
            productos.forEach(p -> {
                out.println("<tr>");
                out.print("<td>" + p.getIdProducto() + "</td>");
                out.print("<td>" + p.getNombre() + "</td>");
                out.print("<td>" + p.getTipo() + "</td>");

                if (usernameCookie.isPresent()) {
                    out.print("<td>$" + p.getPrecio() + "</td>");
                } else {
                    out.print("<td class='no-sesion'>Loggeo necesario</td>");
                }

                out.println("</tr>");
            });

            out.println("</table>");
            out.println("</div>");

            if (!esXls) {
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}