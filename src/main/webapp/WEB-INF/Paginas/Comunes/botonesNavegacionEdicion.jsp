<section id="actions_edicion" class="py-4 mb-4">
    <div class ="container">
        <div  class="row">
            <div class ="col-md-3">
                <a href="index.jsp" class="btn btn-outline-secondary btn-blockn" id="btn_regresar">
                    <i class="fa fa-arrow-left"></i> Regresar al inicio
                </a>
            </div>
            <div class="col-md-3">
                <button id="btn_guardar" type="submit" class="btn btn-block">
                    <i class="fas fa-check"></i> Guardar 
                </button>
            </div>
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/ServletControlador?accion=eliminar&idCliente=${cliente.idCliente}" id="btn_eliminar" class="btn btn-block">
                    <i class="fas fa-trash"></i> Eliminar Cliente
                </a>
            </div>
        </div>
    </div>
</section>