<%@page import="dominio.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page buffer="64kb" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
<style type="text/css">
	<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>

<!-- Custom styles for this template -->
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8" src="bootstrap/dashboard/dashboard.js"></script>

</head>
<body>

<% 
HttpServletResponse res = (HttpServletResponse) response;
HttpSession sesion = ((HttpServletRequest) request).getSession();							

	if(sesion.getAttribute("username")==null){
		res.sendRedirect("Login.jsp");
		return;
	}
	if(sesion.getAttribute("tipo")!=null){
		if(sesion.getAttribute("tipo").toString().equals("Medico")){
			res.sendRedirect("LoginError.jsp");
			return;
	}
	}
	boolean exito = false;
	request.setAttribute("exito", exito); %>  

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
						<li class="nav-item"><a class="nav-link" href="ServletCalendario"> <span
								data-feather="file-text"></span> Reportes Calendario
						</a></li>					
					</ul>
				</div>
			</nav>

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard - Gestion de Pacientes</h1>
				</div>

				<!-- <hr class="hr hr-blurry" /> -->
				<h2>Listado de Pacientes</h2>
				<div class="col-auto">					
						<a style="height:38px;" href="ServletGestionPacientes?Param=insert" name="nuevoPaciente" class="btn btn-primary">Nuevo Paciente</a>
				</div>

				<%
				List<Paciente> listaPacientes = new ArrayList<Paciente>();
				if (request.getAttribute("listaPacientes") != null) {
					listaPacientes = (List<Paciente>) request.getAttribute("listaPacientes");
				}
				%>
			<div class="container-xl">
				<div class="table-responsive">
				<div class="table-wrapper">
					<table class="table table-dark table-striped" id="table_pacientes">
						<thead>
							<tr>
								<td><b>DNI</b></td>
								<td><b>NOMBRE</b></td>
								<td><b>APELLIDO</b></td>
								<td><b>SEXO</b></td>
								<td><b>EMAIL</b></td>
								<td><b>TELEFONO</b></td>
								<td><b>ACCIONES</b></td>
							</tr>
						</thead>
						<tbody id="idTBody">
							<%
							for (Paciente paciente : listaPacientes) {
							%>
							<tr>
								<td><%=paciente.getDni()%></td>
								<td><%=paciente.getNombre()%></td>
								<td><%=paciente.getApellido()%></td>
								<td><%=paciente.getSexo()%></td>
								<td><%=paciente.getEmail()%></td>
								<td><%=paciente.getTelefono()%></td>
								<td>
								<a href="ServletGestionPacientes?Modificar=<%=paciente.getDni()%>" 
									class="edit" title="Edit" data-bs-toggle="tooltip" style="font-size:28px;color:yellow"><i
										class="material-icons">&#xE254;</i></a>
										
								<!-- 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --> 
								<a href="#myModal"
									class="delete" title="Delete" data-bs-toggle="modal" 
									data-pac-id="<%=paciente.getDni()%>" style="font-size:28px;color:red"><i
										class="material-icons">&#xE872;</i></a>
								</td>

								<%
								}
								%>
							</tr>
						</tbody>
					</table>
				</div>
				</div>
				</div>
			</main>
		</div>
	</div>
					<script type="text/javascript">
	                	$(document).ready(function (e) {
	                        $('#myModal').on('show.bs.modal', function(e) {
	                        	 
	                        	var dni = $(e.relatedTarget).data('pac-id'); 	                       
	                        	$(e.currentTarget).find('input[name="pacId"]').val(dni);											                        		  
	                    	});
	                    });							
					</script>

					<form action="ServletGestionPacientes?Eliminar=1" method="post">
						<div id="myModal" class="modal fade">
							<div class="modal-dialog modal-confirm">
								<div class="modal-content">
									<div class="modal-header flex-column">
										<div class="icon-box">
											<i class="material-icons">&#xE5CD;</i>
										</div>
										<h4 class="modal-title w-100">¿Estás seguro?</h4>
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
									</div>
									<div class="modal-body">
										<p>¿Desea eliminar el paciente? Esta operación no se puede
											deshacer</p>
										<input type="hidden" name="pacId" id="pacId">
									</div>
									<div class="modal-footer justify-content-center">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Cancelar</button>
										<button type="submit" class="btn btn-danger">Borrar</button>
									</div>
								</div>
							</div>
						</div>
					</form>

					<script>
						$(document).ready(function() {
							$('#table_pacientes').DataTable();
						});
					</script>
					<script>
						var table = $('#table_pacientes').DataTable({
							columnDefs : [ {
								targets : [ 6 ],
								orderable : false
							},

							]
						});
					</script>

					<%
					boolean success = false;
					String texto = "";

					if (request.getAttribute("exito") != null) {
						success = (boolean) request.getAttribute("exito");
						texto = "registrado";
					}

					if (request.getAttribute("update") != null) {
						success = (boolean) request.getAttribute("update");
						texto = "modificado";
					}

					if (request.getAttribute("delete") != null) {
						success = (boolean) request.getAttribute("delete");
						texto = "eliminado";
					}

					if (success == true) {
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
										El paciente se ha
										<%=texto%>
										satisfactoriamente.
									</p>
								</div>
							</div>
						</div>
					</div>
					<%}%>

	<script
		src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"
		integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"
		integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha"
		crossorigin="anonymous"></script>


	<%@ include file="footer.jsp"%>
</body>
</html>