<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ page buffer="64kb" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="header.jsp"%>
<style type="text/css">
	<jsp:include page="css\StyleSheet.css"></jsp:include>
</style>

<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">

<title>Alta Administrativo</title>
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

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard - Alta Administrativo</h1>
				</div>
				<br>

				<div class="registro">
					<form method="post" action="ServletUsuario">
						<h1 style="font-size: 25px; margin-top: 30px; margin-bottom: 30px">Alta
							de Nuevo Administrativo</h1>
						<table class="formulario">
							<tr>
								<td><label>Nombre de Usuario:</label></td>
								<td><input name="txtUser" type="user"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									class="inputForm" size="20" required></td>
							</tr>
							<tr>
								<td><label>Clave:</label></td>
								<td><input type="password" class="inputForm" name="txtPass"
									placeholder="Password" class="inputForm" required></td>
							</tr>
							<tr>
								<td><label>Confirmar Clave:</label></td>
								<td><input type="password" class="inputForm"
									name="txtPass2" placeholder="Password" class="inputForm"
									required></td>
							</tr>
							<tr>
						</table>
						<div>
							<p style="color: red; margin-left: 125px;">
								<%
								String resultado = (String) request.getAttribute("mensaje");
								String mensaje = "";
								if (resultado != null) {
									mensaje = resultado;
								}
								%>
								<%=mensaje%>
							</p>

						</div>


						<br>
						<div class="text-center">  
							<input name="btnNuevoUser" type="submit"
								value="Registrar" class="btn btn-primary btn-block btn-large">
						</div>

					</form>
				</div>

				<%
				boolean exito = false;

				if (request.getAttribute("exito") != null) {

					exito = (boolean) request.getAttribute("exito");

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
								<p>El usuario se ha registrado satisfactoriamente.</p>
							</div>
						</div>
					</div>
				</div>
				<%
				}
				%>

			</main>
		</div>
	</div>
	
</body>
</html>