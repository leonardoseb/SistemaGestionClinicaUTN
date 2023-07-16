<%@page import="dominio.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page buffer="64kb" %>
<!DOCTYPE html>
<html>
<head>
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
		if(sesion.getAttribute("tipo").toString().equals("Admin")){
			res.sendRedirect("LoginError.jsp");
			return;
	}
	}
%>  

	<div class="container-fluid">
		<div class="row">
			<nav id="sidebarMenu"
				class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
				<div class="position-sticky pt-3">
					<ul class="nav flex-column">
						<h6
							class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
							<span>Seccion - Medico</span> <a class="link-secondary" href="#"
								aria-label="Add a new report"> <span
								data-feather="plus-circle"></span>
							</a>
						</h6>						
						<li class="nav-item"><a class="nav-link" href="ServletMedicoInicio?list=1"> <span
								data-feather="file"></span> Gestion de Turnos
						</a></li>																
					</ul>
				</div>
			</nav>

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard - Template</h1>
				</div>
				<h2>Mis Turnos</h2>
				<!-- ACA DEBERIA IR LO QUE SE QUIERE MOSTRAR -->
				<div class="container-xl">
					<div class="table-responsive">
						<div class="table-wrapper">
							<div style="padding-bottom: 0px;" class="table-title">
								<div class="row justify-content-left">
									<div class="col-sm-8">
										<h1>Mis turnos asignados</h1>
										<form method="post" action="ServletMedicoInicio">
											<table class="filtrosListado">
												<tr>
													<td><label>Estado:</label></td>
													<td><select name="slcEstado" class="select">
															<option value=0>TODOS</option>

															<option value=2>OCUPADO</option>
															<option value=3>AUSENTE</option>
															<option value=4>PRESENTE</option>
													</select></td>
													<td><label>Fecha:</label></td>
													<td><input type="date" name="fechaFiltro"></td>
													<td><input name="btnFiltrar" type="submit"
														value="Filtrar" class="btn btn-primary btn-sm"></td>
												</tr>

											</table>
										</form>
									</div>

								</div>
							</div>



							<table id="table_turnos" class="table table-dark table-striped">
								<thead>
									<tr>
										<th>Fecha</th>
										<th>Horario</th>
										<th>Paciente</th>
										<th>Estado</th>
										<th>Ultima Obsevacion</th>
										<th>Acciones</th>
									</tr>
								</thead>


								<tbody>

									<%
									List<Turno> listaMisTurnos = null;
									if (request.getAttribute("listaMisTurnos") != null) {
										listaMisTurnos = (ArrayList<Turno>) request.getAttribute("listaMisTurnos");
									}
									for (Turno tu : listaMisTurnos) {
									%>
									<tr>
										<td><%=tu.getFecha()%></td>
										<td><%=tu.getHora()%></td>
										<td><%=tu.getPaciente().getNombre()%> <%=tu.getPaciente().getApellido()%></td>
										<td><%=tu.getEstado().getDescripcion()%></td>
										<%-- <td><%=tu.getObservacion()%></td> --%>
										<td>
											<% if (tu.getObservacion() != null){%>
											<%=tu.getObservacion() %>
											<%}
											else{%>
											<%="No posee observacion." %>
											<%} %>
										</td>
										<td><a href="ServletMedicoInicio?Detalle=<%=tu.getPaciente().getDni()%>"
											class="view" title="View" data-bs-toggle="tooltip" style="color:green">
											<i class="material-icons">&#xE417;</i></a> 
											<a href="ServletMedicoInicio?Modificar=<%=tu.getIdTurno()%>&Pax=<%=tu.getPaciente().getDni()%>"
											class="edit" title="Edit" data-bs-toggle="tooltip" style="color:yellow">
											<i class="material-icons">&#xE254;</i></a></td>
									</tr>
									<% } %>
								</tbody>
							</table>


						</div>
					</div>
				</div>
				<% 		
			
						
						boolean detalle = false;
					
						
						if(request.getAttribute("detallePaciente")!=null){
						
							detalle = (boolean)request.getAttribute("detallePaciente");				
							
						}
						
						Paciente pa = new Paciente();
						
						if(request.getAttribute("Paciente")!=null){
							pa = (Paciente)request.getAttribute("Paciente");
						}
						
						String observacionesPaciente = new String();					
						
						if(request.getAttribute("observacionesPaciente")!=null){
						
							observacionesPaciente = (String) request.getAttribute("observacionesPaciente");				
							
						}

							if(detalle == true){
								%>
								<script type="text/javascript">
										window.onload = function() {
											OpenBootstrapPopup();
										};
										function OpenBootstrapPopup() {
											$("#openModalDetallePaciente").modal('show');
										}
										
									</script>
									
									<div id="openModalDetallePaciente" class="modal fade">
			<div class="modal-dialog modal-det">
				<div class="modal-content">
					<div  class="modal-header justify-content-left">						
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body text-center">
						<h1>Detalle Paciente</h1>	
						<table class="tablaDetalle">
				<tr>
					<td class="Campo"><label>DNI</label></td>
					<td><label name="lblDni"> <%=pa.getDni()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Nombres</label></td>
					<td><label name="lblNombre"> <%=pa.getNombre()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Apellidos</label></td>
					<td><label name="lblApellido"> <%=pa.getApellido()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Fecha Nacimiento</label></td>
					<td><label name="lblFechaNac"> <%=pa.getFechaNacimiento()%> </label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Sexo</label></td>
					<td><label name="lblSexo"> <%=pa.getSexo()%></label>
				</tr>
				<tr>
					<td class="Campo"><label>Nacionalidad</label></td>
					<td><label name="lblNacionalidad"> <%=pa.getNacionalidad().getDescripcion()%></label>
				</tr>
				<tr>
					<td class="Campo"><label>Provincia</label></td>
					<td><label name="lblProvincia"> <%=pa.getLocalidad().getProvincia().getDescripcion()%></label>
				</tr>
				<tr>
					<td class="Campo"><label>Localidad</label></td>
					<td><label name="lblLocalidad"><%=pa.getLocalidad().getDescripcion()%></label>
				</tr>
				<tr>
					<td class="Campo"><label>Direccion</label></td>
					<td><label name="lblDireccion"><%=pa.getDireccion()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>E-mail</label></td>
					<td><label name="email "><%=pa.getEmail()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Telefono</label></td>
					<td><label name="lblTelefono1"><%=pa.getTelefono()%></label></td>
				</tr>		
				<tr>
					<td class="Campo"><label>Observaciones</label></td>
					<td><label name="observaciones"><%=observacionesPaciente%></label></td>
					
				</tr>			
			</table>
					</div>
					
				</div>
			</div>
		</div>     
									
									
							
									
								 <%}
									%>
									
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
					<div  class="modal-header justify-content-left">						
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body text-center">
					<h1>Detalle Turno</h1>
					<form method="post" action="ServletMedicoInicio">
						<table class="tablaDetalle">
			
				<tr>
					<td></td>
					<td><input type="hidden" name="lblidTurno" value=<%= turno.getIdTurno()%>></td>
				</tr>
				<tr>
					<td class="Campo"><label>Fecha</label></td>
					<td><label name="lblFecha"><%= turno.getFecha()%></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Hora</label></td>
					<td><label name="lblHora"><%= turno.getHora() %></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Paciente</label></td>
					<td><label name="lblPaciente"><%= paciente.getNombre()%> <%= paciente.getApellido() %></label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Especialidad</label></td>
					<td><label name="lblEspecialidad"><%= turno.getMedico().getIdEspecialidad().getDescripcion() %> </label></td>
				</tr>
				<tr>
					<td class="Campo"><label>Estado</label></td>
					<td><select class="select" name= "lblEstado"><Option value=2>OCUPADO</Option>
							<Option value=4>PRESENTE</Option>
							<Option value=3>AUSENTE</Option></select>
				</tr>
				<tr>
					<td class="Campo"><label>Observacion</label></td>
					<td>
					<% if (turno.getObservacion() != null){
						%>
					<textarea rows="4" cols="20" name="txtObservacion" style="resize:none;"><%=turno.getObservacion() %></textarea>
					<%}
					else{%>
					<textArea  name="txtObservacion" style="resize: none;"
							class="inputForm" cols="26" rows="3"></textArea>
					<%} %>
				</td>				
				</tr>
			</table>
			<input name="actualizarTurno" type="submit" value="Guardar"
				class="btn btn-primary btn-sm btn-block" style="margin-top: 5px;">
			</form>
				</div>
					</div>
				</div>
				</div>
									<%}
									%>
									<% 		
			
						
						boolean exito = false;
						
						if(request.getAttribute("exito")!=null){
						
							exito = (boolean)request.getAttribute("exito");
							
						}
							if(exito == true){
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
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body text-center">
						<h4>Exito!</h4>	
						<p>El Turno ha sido modificado satisfactoriamente.</p>
					</div>
				</div>
			</div>
		</div>     
								 <%} 
									%>


	<script>
		$(document).ready(function() {
			$('#table_turnos').DataTable();
		});
	</script>
	<script>
		var table = $('#table_turnos').DataTable({
			"order": [[0, 'asc'] , [1, 'asc']],
			columnDefs : [ {
				targets : [ 4 ],
				orderable : false
			},

			]
		});
	</script>

			</main>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>