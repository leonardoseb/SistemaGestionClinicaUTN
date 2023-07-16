<%@page import="dominio.Provincia"%>
<%@page import="dominio.Paciente"%>
<%@page import="dominio.Medico"%>
<%@page import="dominio.Localidad"%>
<%@page import="dominio.Nacionalidad"%>
<%@ page import="dominio.Especialidad"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page buffer="64kb" %>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta Medico</title>
<!-- <link rel="Stylesheet" href="css/Login.css" />
 -->
<%@ include file="header.jsp"%>
<style type="text/css">
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">

</head>
<style>
.linea {
	border-top: 1px solid black;
	height: 2px;
	max-width: 100%;
	padding: 0;
	margin: 20px auto 0 auto;
}
</style>
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8" src="bootstrap/dashboard/dashboard.js"></script>

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
				<div class="registro3">
				<form action="ModificarMedico" method="get">
					<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
						<h1 class="h2">Dashboard - Modifacion de Medicos</h1>
					</div>
					<div>
						<h6>Ingrese el Documento del medico a modificar</h6>
					</div>							
							<div class="form-check form-check-inline">
									<input type="text" placeholder="&#128269 Dni" name="textDni" requiered > 
									<input class="btn btn-primary mb-2 btn-sm" type="submit" value="Buscar" name="btnBuscar">
							</div>				

					<div class="linea"></div>
					<br>
					<%
					List<Nacionalidad> listaNacionalidad = new ArrayList<Nacionalidad>();
					if (request.getAttribute("listaNacionalidad") != null) {
						listaNacionalidad = (List<Nacionalidad>) request.getAttribute("listaNacionalidad");
					}
					%>
					<%
						List<Provincia> listaProvincia = new ArrayList<Provincia>();
						if (request.getAttribute("listaProvincia") != null) {
							listaProvincia = (List<Provincia>) request.getAttribute("listaProvincia");
						}
					%>
					<%
					List<Localidad> listaLocalidad = new ArrayList<Localidad>();
					if (request.getAttribute("listaLocalidad") != null) {
						listaLocalidad = (List<Localidad>) request.getAttribute("listaLocalidad");
					}
					%>
					<%
					List<Especialidad> especialidades = new ArrayList<Especialidad>();
					if (request.getAttribute("especialidades") != null) {
						especialidades = (List<Especialidad>) request.getAttribute("especialidades");
					}
					%>
					<%
					List<Medico> ListaMedicos = new ArrayList<Medico>();
					if (request.getAttribute("ListMedicos") != null) {
						ListaMedicos = (List<Medico>) request.getAttribute("ListMedicos");
					}
					%>
					<%
					Object blTablaObj = request.getAttribute("blTabla");
					if (blTablaObj != null) {
						boolean blTabla = (boolean) blTablaObj;
						if (blTabla == true) {
					%>
					<table class="formulario2">
						<%
						if (ListaMedicos != null)
							for (Medico md : ListaMedicos) {
						%>
						<tr>
							<td><label>Dni Medico </label></td>
							<td><input class="inputForm" size="20" type="text" name="txtDniMedico"
								value="<%=md.getDni()%>" style="width: 100%;"></td>
						</tr>
						<tr>		
							<td><label>Nombre </label></td>
							<td><input type="text" class="inputForm" name="txtNombreMedico"
								value="<%=md.getNombre()%>" style="width: 100%;"></td>
						</tr>
						<tr>
							<td><label>Apellido </label></td>
							<td><input type="text" class="inputForm" name="txtApellidoMedico"
								value="<%=md.getApellido()%>" style="width: 100%;"></td>

						</tr>
						<tr>
							<td><label>Direccion </label></td>
							<td><input type="text" class="inputForm"  name="txtDireccionMedico"
								value="<%=md.getDireccion()%>" style="width: 100%;"></td>
							<td></td>
						</tr>
						<tr>
							<td><label>Nacionalidad </label></td>
							<td><select name="comboNacionalidad"  class="form-control form-control-sm" style="width: 100%;"
								id="comboNacionalidad"">
									<%
									for (Nacionalidad nacionalidad : listaNacionalidad) {
									%>
									<option value="<%=nacionalidad.getIdNacionalidad()%>"
										<%=nacionalidad.getIdNacionalidad() == md.getNacionalidad().getIdNacionalidad() ? "selected" : ""%>>
										<%=nacionalidad.getDescripcion()%>
									</option>
									<%
									}
									%>
							</select></td>
							<tr>
							<td><label>Localidad </label></td>
							<td><select name="comboLocalidad"  class="form-control form-control-sm" style="width: 100%;"
								id="comboLocalidad" onchange="actualizarLocalidades()">
									<%
									for (Localidad localidad : listaLocalidad) {
									%>
									<option value="<%=localidad.getIdLocalidad()%>"
										<%=localidad.getIdLocalidad() == md.getLocalidad().getIdLocalidad() ? "selected" : ""%>>
										<%=localidad.getDescripcion()%>
									</option>
									<%
									}
									%>
							</select></td>
							</tr>
							
						<tr>
							<td><label>Provincia </label></td>
							<td><select name="comboProvincia"  class="form-control form-control-sm" style="width: 100%;"
								onchange="actualizarProvincias()">
									<%
									for (Provincia provincia : listaProvincia) {
									%>
									<option value="<%=provincia.getIdProvincia()%>"
										<%=provincia.getIdProvincia() == md.getLocalidad().getProvincia().getIdProvincia() ? "selected" : ""%>>
										<%=provincia.getDescripcion()%>
									</option>
									<%
									}
									%>
							</select></td>
						</tr>
						<tr>
							<td><label>Sexo </label></td>
							<td><input type="radio" name="sexo" value="Masculino"
								checked> Masculino <input type="radio" name="sexo"
								value="Femenino"> Femenino <input type="radio"
								name="sexo" value="Otro"> Otro</td>
							<td></td>
						</tr>
						<tr>	
							<td><label class="label label-info" >Telefono </label></td>
							<td><input type="text" class="inputForm" name="txtTelefonoMedico"
								value="<%=md.getTelefono()%> " style="width: 100%;"></td>
						</tr>
						<tr>
							<td><label>Especialidad</label></td>
							<td><select name="comboEspecialidad" class="form-control form-control-sm" style="width: 100%;">
									<%
									for (Especialidad especialidad : especialidades) {
									%>
									<option value="<%=especialidad.getIdEspecialidad()%>"
										<%=especialidad.getIdEspecialidad() == md.getIdEspecialidad().getIdEspecialidad() ? "selected" : ""%>>
										<%=especialidad.getDescripcion()%>
									</option>
									<%
									}
									%>
							</select></td>
						</tr>
						<tr>
							<td><label>Fecha de nacimiento</label></td>
							<td><input type="date" class="inputForm" name="txtFechaNacimiento"
								value="<%=md.getFechaNacimiento()%>" required
								style="width: 100%; text-align: center;"></td>
						</tr>
						<tr>
							<td><label>E-Mail </label></td>
							<td><input type="text" class="inputForm" name="txtEmail"
								value="<%=md.getEmail()%>" style="width: 100%;"></td>
						</tr>
						<tr>
						</tr>
						<%
						}
						%>
					</table>
					<div class="linea">
					</div>
						<br> 
						<input type="submit" class="btn btn-outline-warning" value="Modificar" name="btnModificar"> 
<!-- 						<input type="submit" class="btn btn-outline-danger" value="Eliminar" name="btnEliminar"> 
 -->						<input type="reset"  class="btn btn-outline-dark" value="Cancelar">
					<%
					} else {
					%>
					<script>
						alert("Dni Incorrecto");
					</script>
					<%
					}
					}
					%>
					<script>
					  var localidadesPorProvincia = {
							    <% for (Provincia provincia : listaProvincia) { %>
							      "<%= provincia.getIdProvincia() %>": [
							        <% for (Localidad localidad : listaLocalidad) { %>
							          <% if (localidad.getProvincia().getIdProvincia() == provincia.getIdProvincia()) { %>
							            {
							              "idLocalidad": "<%= localidad.getIdLocalidad() %>",
							              "descripcion": "<%= localidad.getDescripcion() %>"
							            },
							          <% } %>
							        <% } %>
							      ],
							    <% } %>
							  };

							  function actualizarLocalidades() {
							    var comboProvincia = document.getElementById("comboProvincia");
							    var comboLocalidad = document.getElementById("comboLocalidad");
							    var provinciaSeleccionada = comboProvincia.value;
							    var localidades = localidadesPorProvincia[provinciaSeleccionada];

							    // Limpia el combo de localidades antes de actualizarlo
							    comboLocalidad.innerHTML = "";

							    // Agrega las nuevas opciones al combo de localidades
							    for (var i = 0; i < localidades.length; i++) {
							      var localidad = localidades[i];
							      var option = document.createElement("option");
							      option.value = localidad.idLocalidad;
							      option.textContent = localidad.descripcion;

							      if (localidad.idLocalidad == md.localidad.idLocalidad) {
							        option.selected = true;
							      }

							      comboLocalidad.appendChild(option);
							    }
							  }

							  // Inicializa los valores de los selects al cargar la página
							  window.onload = function() {
							    actualizarLocalidades();
							  };
							  </script>


				</form>
				<script
					src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"
					integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE"
					crossorigin="anonymous"></script>
				<script
					src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"
					integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha"
					crossorigin="anonymous"></script>

				<%@ include file="footer.jsp"%>
				</div>
			</main>
		</div>
	</div>

</body>
</html>