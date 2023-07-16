<%@page import="dominio.Provincia"%>
<%@page import="dominio.Localidad"%>
<%@page import="dominio.Nacionalidad"%>
<%@ page import="dominio.Especialidad"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page buffer="64kb"%>
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
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<style type="text/css">
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8"
	src="bootstrap/dashboard/dashboard.js"></script>
</head>

<body onLoad="myOnLoad()">

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

	<%
	List<Nacionalidad> listaNacionalidad = new ArrayList<Nacionalidad>();
	if (request.getAttribute("listaNacionalidad") != null) {
		listaNacionalidad = (List<Nacionalidad>) request.getAttribute("listaNacionalidad");
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
	List<Provincia> listaProvincia = new ArrayList<Provincia>();
	if (request.getAttribute("listaProvincia") != null) {
		listaProvincia = (List<Provincia>) request.getAttribute("listaProvincia");
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
					<h1 class="h2">Dashboard - Alta Medicos Nuevos</h1>
				</div>
				<div class="registro3">
					<h2>ALTA MEDICO</h2>
					<form action="AltaMedico" method="post">
						<table class="formulario">
							<tr>
								<td><a>Dni </a></td>
								<td><input class="inputForm" type="text"
									name="txtDniMedico"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									placeholder="DNI DEL MEDICO" required style="width: 100%;"
									size="20"></td>
							</tr>
							<tr>
								<td><a>Nombre </a></td>
								<td><input type="text" name="txtNombreMedico"
									placeholder="NOMBRE DEL MEDICO" required style="width: 100%;"></td>
							</tr>
							<tr>
								<td><a>Apellido </a></td>
								<td><input type="text" name="txtApellidoMedico"
									placeholder="APELLIDO DEL MEDICO" required style="width: 100%;"></td>
							</tr>
							<tr>
								<td><a>Fecha de nacimiento</a></td>
								<td><input type="date" name="txtFechaNacimiento"
									placeholder="FECHA NACIMIENTO MEDICO" required
									style="width: 100%;"></td>
							</tr>
							<tr>
								<td><a>Sexo </a></td>
								<td class="tdSexo"><input type="radio" name="sexo"
									value="Masculino" checked> Masculino <input
									type="radio" name="sexo" value="Femenino">Femenino <input
									type="radio" name="sexo" value="Otro"> Otro</td>
							</tr>
							<tr>
								<td><label>Nacionalidad:</label></td>
								<td><select class="select" name="slcNacionalidad">

										<%
										if (request.getAttribute("listaNacionalidad") != null) {

											listaNacionalidad = (ArrayList<Nacionalidad>) request.getAttribute("listaNacionalidad");
										}

										if (listaNacionalidad != null)
											for (Nacionalidad es : listaNacionalidad) {
										%>
										<option value="<%=es.getIdNacionalidad()%>">
											<%=es.getDescripcion()%>
										</option>

										<%
										}
										%>

								</select>
								<td><label>Provincia:</label></td>
								<td><select class="textbox" onchange="cargar_localidades()"
									required id="provincia1" name="slcProvincia">

										<%
										if (request.getAttribute("listaProvincia") != null) {

											listaProvincia = (ArrayList<Provincia>) request.getAttribute("listaProvincia");
										}

										if (listaProvincia != null)
											for (Provincia es : listaProvincia) {
										%>
										<option value="<%=es.getIdProvincia()%>">
											<%=es.getDescripcion()%>
										</option>

										<%
										}
										%>

								</select>
								<td><label>Localidad:</label></td>
								<td><select class="textbox" required id="localidadReal"
									name="slcLocalidad"></select>
							</tr>
							<tr>
								<td><a>Direccion </a></td>
								<td><input type="text" name="txtDireccionMedico"
									placeholder="DIRECCION MEDICO" style="width: 100%;"></td>
							</tr>
							<tr>
								<td><a>Telefono </a></td>
								<td><input type="text" name="txtTelefonoMedico"
									placeholder="TELEFONO MEDICO" required style="width: 100%;"></td>
							</tr>
							<tr>
								<td><a>E-Mail </a></td>
								<td><input type="text" name="txtEmail"
									placeholder="E-MAIL MEDICO" required style="width: 100%;"></td>
							</tr>
							<tr>

								<td><a>Especialidad </a></td>
								<td><select name="especialidad" style="width: 100%;">
										<%
										for (Especialidad esp : especialidades) {
										%>
										<option value="<%=esp.getIdEspecialidad()%>"><%=esp.getDescripcion()%></option>
										<%
										}
										%>
								</select></td>

								<td><a>Horarios Disponibles </a></td>
								<td>
								<input type="radio" name="horarios" value="1" checked>9	a 12 <br> 
								<input type="radio" name="horarios" value="2">12 a 18 <br> 
								<input type="radio" name="horarios" value="3">9 a 18 <br> 
								<input type="radio" name="horarios" value="4">12 a 15 <br> 
								<input type="radio" name="horarios" value="5">15 a 18 <br>
								</td>
								<td><a>Dias Disponibles</a></td>
								<td><input type="checkbox" name="dias" value="1">Domingo<br>
									<input type="checkbox" name="dias" value="2">Lunes<br>
									<input type="checkbox" name="dias" value="3">Martes<br>
									<input type="checkbox" name="dias" value="4">Miércoles<br>
									<input type="checkbox" name="dias" value="5">Jueves<br>
									<input type="checkbox" name="dias" value="6">Viernes<br>
									<input type="checkbox" name="dias" value="7">Sábado<br>
								</td>
							</tr>
							<tr>


							</tr>
						</table>

						<div class="linea"></div>
						</br>
						</br>
						<div class="text-center">
							<input class="btn btn-success" name="btnAgregarMed" type="submit" value="Agregar"> 
							<input class="btn btn-secondary" type="reset" value="Limpiar Datos">

						</div>
						<script
							src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"
							integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE"
							crossorigin="anonymous"></script>
						<script
							src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"
							integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha"
							crossorigin="anonymous"></script>


					</form>
					<%@ include file="footer.jsp"%>
				</div>
				<select name="localidades" id="localidad2">

					<%
					List<Localidad> listaLocalidad2 = new ArrayList<Localidad>();

					if (request.getAttribute("listaLocalidad") != null) {
						listaLocalidad2 = (ArrayList<Localidad>) request.getAttribute("listaLocalidad");
					}

					if (listaLocalidad2 != null) {
						for (Localidad localidad2 : listaLocalidad2) {
					%>
					<option value="<%=localidad2.getProvincia().getIdProvincia()%>"
						data-uid="<%=localidad2.getIdLocalidad()%>"><%=localidad2.getDescripcion()%></option>
					<%-- <option value="<%=1%>"
						data-uid="<%=1%>"><%=localidad2.getDescripcion()%></option> --%>

					<%
					}
					}
					%>

				</select>
				<!-- 
	<script>
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();

		if (dd < 10) {
			dd = '0' + dd;
		}

		if (mm < 10) {
			mm = '0' + mm;
		}

		today = yyyy + '-' + mm + '-' + dd;
		document.getElementById("datefield").setAttribute("max", today);
	</script>
 -->

				<script>
					function myOnLoad() {
						var earrings = document.getElementById('localidad2');
						earrings.style.visibility = 'hidden';
						cargar_localidades();

					}
				</script>


				<script>
					function cargar_localidades() {
						document.getElementById("localidadReal").options.length = 0;

						var x = document.getElementById("localidad2");
						var array = new Array();
						var a = new Array();
						var b = new Array();
						for (i = 0; i < x.length; i++) {

							array.push(x.options[i].text);
							a.push(x.options[i].value);
							b.push(x.options[i].getAttribute('data-uid'));

						}

						addOptions("slcLocalidad", array, a, b);
					}
				</script>

				<script>
					function addOptions(domElement, array, a, b) {
						var select = document.getElementsByName(domElement)[0];
						var inde = document.getElementById('provincia1').value;

						for (value in array) {
							if (a[value] === inde) {
								var option = document.createElement("option");
								option.text = array[value];
								option.value = b[value];
								select.add(option);
							}
						}
					}
				</script>
			</main>
		</div>
	</div>

	<!-- <script src="dist/js/bootstrap.bundle.min.js"></script> -->


</body>
</html>