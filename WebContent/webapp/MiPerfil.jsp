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

<!-- Bootstrap core CSS -->
	<link href="../assets/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>

<title>Mi Perfil</title>
</head>
<body>
<% 
HttpServletResponse res = (HttpServletResponse) response;
HttpSession sesion = ((HttpServletRequest) request).getSession();

	if(sesion.getAttribute("username")==null){
		res.sendRedirect("Login.jsp");
		return;
	}
%>

	<div class="container-fluid">
		<div class="row">
			<nav id="sidebarMenu"
				class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
	<%
		if(sesion.getAttribute("tipo")!=null){
			if(sesion.getAttribute("tipo").toString().equals("Medico")){
	%>			
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
	<%		}else{
	%>
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
	<%
		}
	}
	%>
				<!-- <div class="position-sticky pt-3">
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
				</div> -->
			</nav>

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard - Mi Perfil</h1>
				</div>	

				<br>
				<div class="registro">
					<form method="post" action="ServletUsuario">
						<h1 style="font-size: 25px; margin-top: 30px; margin-bottom: 30px">Mi
							Cuenta</h1>
						<table class="formulario">
							<tr>
								<td><label>Nombre de Usuario:</label></td>
								<td><label name="txtUser" type="user" size="20"><%=session.getAttribute("username")%></label></td>
							</tr>
							<tr>
								<td><label>Nuevo Nombre de Usuario:</label></td>
								<td><input name="txtUserNuevo" type="user"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									class="inputForm" size="20"></td>
							</tr>
							<tr>
								<td><label>Clave Anterior:</label></td>
								<td><input type="password" class="inputForm"
									name="txtPassAnterior" placeholder="Password" class="inputForm"
									required></td>
							</tr>
							<tr>
								<td><label>Clave Nueva:</label></td>
								<td><input type="password" class="inputForm"
									name="txtPassNueva" placeholder="Password" class="inputForm"></td>
							</tr>
							<tr>
								<td><label>Confirmar Clave Nueva:</label></td>
								<td><input type="password" class="inputForm"
									name="txtPassNueva2" placeholder="Password" class="inputForm"></td>
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
							<input name="btnModificarUser" type="submit"
							value="Modificar Usuario"
							class="btn btn-primary btn-block btn-large">
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
								<p>El usuario se ha modificado satisfactoriamente.</p>
							</div>
						</div>
					</div>
				</div>
				<%} 
									%>

			</main>
		</div>
	</div>
</body>
</html>