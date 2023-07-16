<%@page import="dominio.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Calendar"%>
<%@ page buffer="64kb" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
<style type="text/css">
	<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>

<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8" src="bootstrap/dashboard/dashboard.js"></script>
<title>Template</title>
</head>
<body>
<% 
HttpServletResponse res = (HttpServletResponse) response;
HttpSession sesion = ((HttpServletRequest) request).getSession();

	if(sesion.getAttribute("username")==null){
		res.sendRedirect("Login.jsp");
	}
	if(sesion.getAttribute("tipo")!=null){
		if(sesion.getAttribute("tipo").toString().equals("Medico")){
			res.sendRedirect("LoginError.jsp");
			return;
	}
	}
%>  

<!-- LISTADO DE TURNOS CON FILTRO POR ESTADO, POR MEDICO, POR PACIENTE, POR FECHA -->
	<div class="container-fluid">
		<div class="row">
			<nav id="sidebarMenu"
				class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
				<div class="position-sticky pt-3">
					<ul class="nav flex-column">
						<h6
							class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
							<span>Seccion - Administrador</span> <a class="link-secondary" href="#"
								aria-label="Add a new report"> <span
								data-feather="plus-circle"></span>
							</a>
						</h6>						
						<li class="nav-item"><a class="nav-link" href="AdminInicio?Param=list"> <span
								data-feather="file"></span> Gestion de Turnos
						</a></li>						
						<li class="nav-item"><a class="nav-link"
							href="ServletGestionPacientes?Param=list"> <span
								data-feather="shopping-cart"></span> Gestion de Pacientes
						</a></li>
						<li class="nav-item"><a class="nav-link" href="CargarMedico?Param=list"> <span
								data-feather="users"></span> Gestion de Medicos
						</a></li>	
						<li class="nav-item"><a class="nav-link" href="ServletUsuario"> <span
								data-feather="users"></span> Gestion de Usuarios
						</a></li>					
					</ul>

					<h6
						class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
						<span>Seccion Reportes</span> <a class="link-secondary" href="#"
							aria-label="Add a new report"> <span
							data-feather="plus-circle"></span>
						</a>
					</h6>
					<ul class="nav flex-column mb-2">
						<li class="nav-item"><a class="nav-link" href="ServletReporte"> <span
								data-feather="file-text"></span> Reportes
						</a></li>
						<li class="nav-item"><a class="nav-link" href="ReporteCalendario.jsp"> <span
								data-feather="file-text"></span> Reportes Calendario
						</a></li>					
					</ul>
				</div>
			</nav>
<!-- LISTADO DE TURNOS CON FILTRO POR ESTADO, POR MEDICO, POR PACIENTE, POR FECHA -->
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard - Turnos</h1>
				</div>
				<h2>Administracion de Turnos</h2>
				<!-- ACA DEBERIA IR LO QUE SE QUIERE MOSTRAR -->
				<div class="container-xl">
    <div class="table-responsive">
        <div class="table-wrapper">
        
          <div style="padding-bottom: 0px;" class="table-title">
                <div class="row justify-content-left">
                    <div class="col-md-6">
                    	<h1>Turnos</h1></div>                    	
                    	<form method="post" action="ServletTurno">
                		<table class ="filtrosListado">
                			<tr>
							<td><label>Estado:</label></td>
							<td><select name="slcEstado" class="select">
											<option value=0>TODOS</option>
											<option value=1>LIBRE</option>
											<option value=2>OCUPADO</option>
											<option value=3>AUSENTE</option>
											<option value=4>PRESENTE</option>
									</select></td>
							<td><label>Fecha:</label></td>
								<td><input type="date" name="fechaFiltro"></td>
							
							<td><input name="btnFiltrar" type="submit" value="Filtrar" class="btn btn-primary btn-sm"></td>
						</tr> 
                		</table>
                		</form>
                    </div>  
                    
                </div>
        
       
     
				 <%
					ArrayList<Turno> listaTurnos = (ArrayList<Turno>)session.getAttribute("listaTurnos"); 
					/* if(listaTurnos!= null){
						listaTurnos = (ArrayList<Turno>) request.getAttribute("listaTurnos");
					} */
				 %>

				<table id="table_turnos" class="table table-dark table-striped">
					   <thead>
                    <tr>
                        <th>#</th>
                        <th>DNI Paciente</th>
                        <th>Medico</th>
                        <th>Especialidadd</th>
                        <th>Fecha</th>
                        <th>Horario</th>
                        <th>Estado</th>
                        <th>Acciones </th>
                    </tr>
                </thead>
					 <tbody>
                        <%  if(listaTurnos !=null)
						for(Turno tu : listaTurnos){
					
					%>
	                    <tr>
	                    	<td><%=tu.getIdTurno()%></td>
	                    	<%
								if (tu.getPaciente().getDni() == null) {
							%><td>Sin Asignar</td>
							<%
								} else {
							%>
							<td><%=tu.getPaciente().getDni()%></td>
	                    	<%} %>
							<td><%=tu.getMedico().getNombre()%> <%=tu.getMedico().getApellido()%></td>							
							<td><%=tu.getMedico().getIdEspecialidad().getDescripcion()%></td>
							<td><%=tu.getFecha()%></td>
							<td><%=tu.getHora()%></td>
							<td><%=tu.getEstado().getDescripcion()%></td>
							<td>
							<% if (tu.getEstado().getDescripcion().equals("LIBRE")) { %>
	                    	<a href="ServletTurno?AsignarTurno=<%=tu.getIdTurno()%>" class="edit" title="Asignar" data-toggle="tooltip" style="color:green"><i class="material-icons">assignment_ind</i></a>
	                       	<% }
							else { 
								if (tu.getEstado().getDescripcion().equals("OCUPADO")) {
							%>
	                       	<a href="#myModal" class="delete" title="Liberar" data-bs-toggle="modal" style="color:red" data-turno-id="<%=tu.getIdTurno()%>"><i class="material-icons">event_busy</i></a>

	                       	<% } else {%>
	                       	<a href="ServletTurno?VerDetalleTurno=<%=tu.getIdTurno()%>&Paciente=<%= tu.getPaciente().getDni() %>" class="view" title="Detalle" data-toggle="tooltip" style="color:yellow"><i class="material-icons">&#xE417;</i></a>
	                       	<% } %>
	                       	</td>     	
	                 	</tr>
	               	<% } %>
	               	<% } %>
                </tbody>
				</table>
        </div>
    </div>  
   <!--  	</main>
		</div>
	</div> -->
</div>
<%
		boolean exito = false;
			String texto = "";

			if (request.getAttribute("exito") != null) {

		exito = (boolean) request.getAttribute("exito");
		texto = "registrado";

		}
			if (request.getAttribute("exito3") != null ){
				exito = (boolean) request.getAttribute("exito3");
				texto = "liberado";
			}
		if (exito == true) {
	%>
	<script type="text/javascript">
		window.onload = function() {
			OpenBootstrapPopup();
		};
		function OpenBootstrapPopup() {
			$("#simpleModal").modal('show');
		}
	</script>

	<div id="simpleModal" class="modal fade">
		<div class="modal-dialog modal-ok">
			<div class="modal-content">
				<div class="modal-header justify-content-center">
					<div class="icon-box">
						<i style="color: green" class="material-icons">&#xE876;</i>
					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body text-center">
					<h4>Exito!</h4>
					<p>
						El turno se ha <%=texto%> satisfactoriamente.
					</p>
				</div>
			</div>
		</div>
	</div>
	<%
		}
	%>		
	
	<script>
		$(document).ready(function() {
			$('#table_turnos').DataTable();
		});
	</script>
	<script>
		var table = $('#table_turnos').DataTable({
			"order": [[4, 'asc'] , [5, 'asc']],
			columnDefs : [ {
				targets : [ 7 ],
				orderable : false
			},

			]
		});
	</script>
				<script type="text/javascript">
					$(document).ready(
							function(e) {
								$('#myModal').on(
										'show.bs.modal',
										function(e) {

											var id = $(e.relatedTarget).data(
													'turno-id');
											$(e.currentTarget).find(
													'input[name="turnoId"]')
													.val(id);

										});
	
							});
				</script>


				<form action="ServletTurno?LiberarTurno=1" method="post">
							<div id="myModal" class="modal fade">
								<div class="modal-dialog modal-confirm">
									<div class="modal-content">
										<div class="modal-header flex-column">
											<div class="icon-box">
												<i class="material-icons">&#xE5CD;</i>
											</div>						
											<h4 class="modal-title w-100">¿Estas seguro?</h4>	
							                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										</div>
										<div class="modal-body">
											<p>¿Desea liberar el turno?</p>
											<input type="hidden" name="turnoId" id="turnoId">
										</div>
										<div class="modal-footer justify-content-center">
											<button type="button" id="cerrar" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
											<button type="submit" class="btn btn-danger">Liberar</button>
										</div>
									</div>
								</div>
							</div>    
						</form>
						
						
						<% 		
			
						
						boolean modalEdit = false;
									
						Turno turno = new Turno();
						
						if(request.getAttribute("Turno")!=null){
							turno = (Turno)request.getAttribute("Turno");
						}
					
						
						if(request.getAttribute("ModalEdit")!=null){
						
							modalEdit = (boolean)request.getAttribute("ModalEdit");				
							
						}
						
						Paciente paciente = new Paciente();
						
						if(request.getAttribute("Paciente")!=null){
							paciente = (Paciente)request.getAttribute("Paciente");
						}

							if(modalEdit == true){
								%>
								<script type="text/javascript">
										window.onload = function() {
											OpenBootstrapPopup();
										};
										function OpenBootstrapPopup() {
											$("#openModalDetalleTurno").modal('show');
										}
										
									</script>

				<div id="openModalDetalleTurno" class="modal fade">
					<div class="modal-dialog modal-det">
						<div class="modal-content">
							<div class="modal-header justify-content-left">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
							</div>
							<div class="modal-body">
								<h1>Detalle Turno</h1>
								<table class="tablaDetalle">
									<tr>
										<td></td>
										<td><input type="hidden" name="lblidTurno"
											value=<%=turno.getIdTurno()%>></td>
									</tr>
									<tr>
										<td><label>Fecha:</label></td>
										<td><input class="inputForm" name="lblFecha" id="lblFecha"
										readonly value="<%=turno.getFecha()%>"></input>										
									</tr>
									<tr>
										<td><label>Horario de Atencion:</label></td>										
										<td><input class="inputForm" name="lblHora" id="lblHora"
										readonly value="<%=turno.getHora()%>"></input>
									</tr>
									<tr>
									<td><label>Paciente</label></td>
										<td><input class="inputForm" name="lblPaciente" id="lblPaciente"
										readonly value="<%=paciente.getNombre()%>
												<%=paciente.getApellido()%>"></input>
									
									</tr>
									<tr>
									<td><label>Estado Turno</label></td>																		
										<td><input class="inputForm" name="lblEspecialidad" id="lblEspecialidad"
										readonly value="<%=turno.getMedico().getIdEspecialidad().getDescripcion()%>"></input>
									</tr>
									<tr>
									<td><label>Observación</label></td>										
										<td>
											<%
											if (turno.getObservacion() != null) {
											%> <textarea class="inputForm" rows="4" cols="20" name="txtObservacion"
												readonly style="resize: none;"><%=turno.getObservacion()%></textarea>
											<%}
					else{%> <input class="inputForm" name="txtObservacion" id="txtObservacion" readonly></input><%} %>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<%}
									%>
				
	 		</main>
		</div>
	</div> 
	<%@ include file="footer.jsp"%>
</body>
</html>