<%@page import="java.time.LocalDateTime"%>
<%@ page import="dominio.Especialidad"%>
<%@ page import="dominio.Medico"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page buffer="64kb"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>
<%@ include file="header.jsp"%>
<style type="text/css">
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8"
	src="bootstrap/dashboard/dashboard.js"></script>
</head>
<style>
.center {
	margin: auto;
	width: 25%;
	padding: 15px;
}

.tablaCalendario table {
	background-color: #f4ffe5;
}

.tablaCalendario tr, .tablaCalendario td {
	border: 1px solid black;
	border-collapse: collapse;
	text-align: center;
}

.tablaCalendario th {
	width: 1%;
	border: 1px solid black;
	border-collapse: collapse;
}
</style>
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
					<h1 class="h2">Dashboard - Gestion de Reportes</h1>
				</div>
				<div class="registro3">
					<form action="ServletReporte" method="GET" id="reporteForm">
						<br> <br>
						<h4>1. Obtener la cantidad de turnos por especialidad entre
							una determinada fecha.</h4>
						<div>
							<table class="formulario">
								<%
								ArrayList<Especialidad> especialidades = (ArrayList<Especialidad>) request.getAttribute("especialidades");
								%>
								<tr>
									<td><a style="font-family: Arial; font-size: 150%">Especialidad</a></td>
									<td><select name="especialidad" class="form-select"
										aria-label="Default select example">
											<%
											if (especialidades != null)
												for (Especialidad esp : especialidades) {
											%>
											<option><%=esp.getDescripcion()%></option>
											<%
											}
											%>
									</select></td>
								<tr>
								<td><a style="font-family: Arial; font-size: 150%">Fecha
										Inicio</a></td>
								<td><input type="date" class="inputForm" name="fechaInicio"
									style="width: 100%; text-align: center;"></td>
								</tr>
								<tr>
									<td><a style="font-family: Arial; font-size: 150%">Fecha
											Fin</a></td>
									<%
									LocalDateTime localDateTime = LocalDateTime.now();
									%>
									<td><input class="inputForm" type="date" name="fechaFin"
										value="<%=localDateTime.toLocalDate()%>"
										style="width: 100%; text-align: center;"></td>
								</tr>
								<tr>
								<td></td>
								<td>
							<div class="center">
								<input class="btn btn-dark" type="submit"
									value="Obtener Reporte" name="btnObtenerReporte">
							</div>
								</td>
							
								</tr>
							</table>
						</div>
						<%
						Integer estado1 = (Integer) request.getAttribute("estado1");
						Integer estado2 = (Integer) request.getAttribute("estado2");

						if (estado1 != null)
							if (estado1 == 1) {
						%>
						<table class="formulario">
							<tr>
								<td><b style="font-size: 160%;"># Resultado</b></td>
							</tr>
							<tr>
								<td><b style="font-size: 160%;">Especialidad: </b> <a
									style="font-family: courier; font-size: 180%;">
										${especialidad}</a></td>
							</tr>
							<tr>
								<td><b style="font-size: 160%;">Cantidad de pacientes:</b><a
									style="font-size: 180%;"> ${cantPacientes}</a></td>
							</tr>
						</table>
						<%
						} else if (estado1 == 2) {
						%>
						<b
							style="font-size: 150%; margin: auto; width: 25%; padding: 15px;">
							No se encontraron valores </b>
						<%
						} else if (estado1 == 0) {
						%>
						<a></a>
						<%
						}
						%>
						<div>
							<br />
							<h4>2. Obtener la cantidad de turnos por mes y año que tuvo
								un medico.</h4>
							<table class="formulario">
								<tr>
									<td><a style="font-family: Arial; font-size: 150%" >Medicos</a></td>
									<td><select name="slcmedico" class="form-select">
											<%
											ArrayList<Medico> medicos = (ArrayList<Medico>) request.getAttribute("medicos");
											if (medicos != null)
												for (Medico med : medicos) {
											%>
											<option value="<%=med.getIdMedico()%>"><%=med.getNombre()%>
												<%=med.getApellido()%></option>
											<%
											}
											%>
									</select></td>
								</tr>
								<tr>
									<td><a style="font-family: Arial; font-size: 150%">Mes</a></td>
									<td><select name="mes" class="form-select">
											<option value="1">Enero</option>
											<option value="2">Febrero</option>
											<option value="3">Marzo</option>
											<option value="4">Abril</option>
											<option value="5">Mayo</option>
											<option value="6">Junio</option>
											<option value="7">Julio</option>
											<option value="8">Agosto</option>
											<option value="9">Septiembre</option>
											<option value="10">Octubre</option>
											<option value="11">Noviembre</option>
											<option value="12">Diciembre</option>
									</select></td>
								</tr>
								<tr>
									<td><a style="font-family: Arial; font-size: 150%">Año</a></td>
									<td><select name="anio" class="form-select">
											<%
											int currentYear = Calendar.getInstance().get(Calendar.YEAR);
											int startYear = currentYear - 3;
											int endYear = currentYear + 0;
											for (int year = startYear; year <= endYear; year++) {
											%>
											<option value="<%=year%>"><%=year%></option>
											<%
											}
											%>
									</select></td>
								</tr>
								<tr>
									<td><a style="font-family: Arial; font-size: 150%">Estado</a></td>
									<td><select name="idEstado" class="form-select">
											<option value="1">Libre</option>
											<option value="2">Ocupado</option>
											<option value="3">Ausente</option>
											<option value="4">Presente</option>
									</select></td>
								</tr>
								<tr>
									<td></td>
									<td>
										<div class="center">
											<input class="btn btn-dark" type="submit"
												value="Obtener Reporte" name="btnReporte2">
										</div>
									</td>
								</tr>
							</table>
							<div>
								<%
								if(estado2 != null)
								if (estado2 == 1) {
								%>
								<table class="formulario">
									<tr>
										<td><b style="font-size: 160%;">Resultado</b></td>
									</tr>
									<tr>
										<td><b style="font-size: 160%;">Médico:</b> <a
											style="font-family: courier; font-size: 180%;">${nombreMedico}
												${apellidoMedico}</a></td>
									</tr>
									<tr>
										<td><b style="font-size: 160%;">Criterio de fecha:</b> <a
											style="font-family: courier; font-size: 180%;">${mes} /
												${anio}</a></td>
									</tr>
									<tr>
										<td><b style="font-size: 160%;">Cantidad de turnos:</b> <a
											style="font-size: 180%;"><b> ${cantTurnos} </b> </a></td>
								</table>

								<%
								} else if (estado2 == 2) {
								%>
								<b style="font-size: 150%; margin: auto; width: 25%; padding: 15px;">
									No se encontraron valores </b>

								<%
								} else if (estado2 == 0) {
								%>
								<a></a>
								<%
								}
								%>
							</div>
						</div>
						<script>
							function changeMonth(year, month) {
								var form = document
										.getElementById("reporteForm");
								var queryString = new URLSearchParams(
										new FormData(form)).toString();

								var url = "ServletReporte?year=" + year
										+ "&month=" + month + "&" + queryString;
								location.href = url;
							}

							function showHoverContent(element) {
								var hoverContent = element
										.querySelector('.hover-content');
								hoverContent.style.display = 'block';
							}

							function hideHoverContent(element) {
								var hoverContent = element
										.querySelector('.hover-content');
								hoverContent.style.display = 'none';
							}
						</script>
					</form>
					<%@ include file="footer.jsp"%>
				</div>

			</main>
		</div>
	</div>
</body>
</html>