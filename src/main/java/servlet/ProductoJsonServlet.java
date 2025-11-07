package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoServices;
import service.ProductosServicesImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/productojson")
public class ProductoJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoServices service = new ProductosServicesImplement();
        List<Producto> productos = service.listar();

        //indicamos que la respuesta es json
        resp.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("[");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                out.println("  {");
                out.println("    \"idProducto\": " + p.getIdProducto() + ",");
                out.println("    \"nombre\": \"" + p.getNombre() + "\",");
                out.println("    \"tipo\": \"" + p.getTipo() + "\",");
                out.println("    \"precio\": " + p.getPrecio());
                out.print("  }");
                if (i < productos.size() - 1) {
                    out.println(",");
                } else {
                    out.println();
                }
            }
            out.println("]");
        }
    }
}
