<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ page buffer="64kb" %>
             <%@page import="dominio.*"%>
    <%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="header.jsp"%>
<style type="text/css">
	<jsp:include page="css\StyleSheet.css"></jsp:include>
</style>

<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">

<title>Template</title>
</head>
<body onLoad="myOnLoad()">
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
	
	Turno tu = new Turno();
	if (session.getAttribute("Turno")!=null){
	   tu = (Turno)session.getAttribute("Turno");
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
					<h1 class="h2">Dashboard - Template</h1>
				</div>

				<div class="registro">
					<form method="post" action="ServletTurno">
						<h1>Registro de Turnos</h1>
						<table class="formulario">

							<tr>
								<td><label>Medico:</label></td>
								<td><input class="inputForm" name="medicos" id="medicoReal"
									readonly value="${Turno.medico.apellido}"></input>
							</tr>
							<tr>
								<td><label>Especialidad:</label></td>
								<td><input class="inputForm" name="especialidad"
									id="especialidad" readonly
									value="${Turno.medico.idEspecialidad.descripcion}"></input>
							</tr>
							<tr>
								<td><label>Fecha:</label></td>
								<td><input class="inputForm" name="fecha" id="fecha"
									readonly value="${Turno.fecha}"></input>
							</tr>
							<tr>
								<td><label>Horario de Atencion:</label></td>
								<td><input class="inputForm" name="atencion" id="atencion"
									readonly value="${Turno.hora}"></input>
							</tr>
							<tr>
								<td><label>DNI:</label></td>
								<td><input name="txtDni" type="text"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									placeholder="Ingrese un DNI" class="inputForm" size="20"
									required></input></td>
							</tr>
						</table>
						<br>
						<div>
							<p>
								<%
								String resultado = (String) request.getAttribute("mensaje");
								String mensaje = "";
								if (resultado != null) {
									mensaje = resultado;
								}
								%>
								<%=mensaje %>
							</p>
						</div>
						<div class="text-center"> 
							<input name="reservar" type="submit" value="Reservar Turno"
								class="btn btn-primary btn-block btn-large">
						</div>
					</form>
				</div>

			</main>
		</div>
	</div>
	
</body>
</html>