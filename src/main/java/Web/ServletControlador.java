package Web;

import Datos.ClienteDaoJDBC;
import domain.Cliente;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request,response);
                    break;
                default:
                    this.accionDefault(request, response); // En caso que la accion sea erronea vuelve a la pag principal
            }
        } else {
            this.accionDefault(request, response); // en caso que la accion sea null vuelve a la pag principal
        }
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // El servlet controlador recupera la informacion de clientes
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        // La coloca en el alcance de request
        HttpSession sesion = request.getSession();
        sesion.setAttribute("Clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        // envia la informacion al JSP de clientes
//        request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            saldoTotal += cliente.getSaldo();
        }
        return saldoTotal;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request,response);
                    break;
                default:
                    this.accionDefault(request, response); // En caso que la accion sea erronea vuelve a la pag principal
            }
        } else {
            this.accionDefault(request, response); // en caso que la accion sea null vuelve a la pag principal
        }
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperamos los valores del formulario agregar cliente.
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        double saldo = 0;
        String saldoStr = request.getParameter("saldo");

        // Antes de convertir String a double hay que validar q no sea null o vacio sino va a tirar una excepcion
        if (saldoStr != null && !"".equals(saldoStr)) {
            saldo = Double.parseDouble(saldoStr);
        }
        //Creamos el objeto Cliente (modelo)
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);

        // lo insertamos en la base de datos
        int registroModificados = new ClienteDaoJDBC().insertar(cliente);
        System.out.println("registroModificados = " + registroModificados);

        //Redirigimos a la pagina principal
        this.accionDefault(request, response);
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Recuperamos el IdCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        // Recuperamos el objeto de tipo cliente mediante el modelo y la capa de datos
        Cliente cliente = new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        
        //Lo compartimos en el alcance de request con el nombre de variable "cliente"
        // De esta manera podemos acceder desde un JSP al objeto cliente que comparte el servlet
        request.setAttribute("cliente", cliente);
        String jspEditar = "/WEB-INF/Paginas/Cliente/EditarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
        
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         // recuperamos los valores del formulario editar cliente.
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        double saldo = 0;
        String saldoStr = request.getParameter("saldo");

        // Antes de convertir String a double hay que validar q no sea null o vacio sino va a tirar una excepcion
        if (saldoStr != null && !"".equals(saldoStr)) {
            saldo = Double.parseDouble(saldoStr);
        }
        //Creamos el objeto Cliente (modelo)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //modificamos el objeto en la base de datos
        int registroModificados = new ClienteDaoJDBC().actualizar(cliente);
        System.out.println("registroModificados = " + registroModificados);

        //Redirigimos a la pagina principal
        this.accionDefault(request, response);
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
         // recuperamos los valores del formulario editar cliente.
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        //Creamos el objeto Cliente (modelo)
        Cliente cliente = new Cliente(idCliente);

        //Eliminamos el objeto en la base de datos
        int registroModificados = new ClienteDaoJDBC().eliminar(cliente);
        System.out.println("registroModificados = " + registroModificados);

        //Redirigimos a la pagina principal
        this.accionDefault(request, response);
    }

}
