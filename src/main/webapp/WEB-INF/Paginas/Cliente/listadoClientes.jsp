<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_MX"/>

<section id="clientes">
    <div class="container">
        <div class="row">
            <div class="col-md-9"> <!-- En este div indicamos que va a ocupar 9 columnas de las 12 que establece bootstrap -->
                <div class="card">
                    <div class="card-header">
                        <h4>Listado de clientes</h4>
                    </div>

                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Saldo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- iteramos los elementos de la lista de clientes-->
                            <c:forEach var="cliente" items="${Clientes}">
                                <tr>
                                    <td>${cliente.idCliente}</td>
                                    <td>${cliente.nombre} ${cliente.apellido}</td>
                                    <td><fmt:formatNumber value="${cliente.saldo}" type="currency"/></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ServletControlador?accion=editar&idCliente=${cliente.idCliente}"
                                           class="btn btn-secondary">
                                            <i class="fas fa-angle-double-right"></i> Editar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!--Tarjetas para totales -->
            <div class="col-md-3">
                <div id="saldo_total" class="card text-center text-white mb-3">
                    <div class="card-body">
                        <h3> Saldo Total </h3>
                        <h6 class ="display-4">
                            <p class="small"> <fmt:formatNumber value="${saldoTotal}" type="currency"/> </p>
                        </h6>
                    </div>
                </div>
                <div id="cliente_totales" class="card text-center text-white mb-3">
                    <div class="card-body">
                        <h3>Total Clientes</h3>
                        <h6 class="display-4">
                            <i class="fas fa-users"> </i> ${totalClientes}
                        </h6>
                    </div>
                </div>
            </div>
        </div> 
    </div>
</section>
<!-- agregar modal -->
<jsp:include page="/WEB-INF/Paginas/Cliente/AgregarCliente.jsp"/>