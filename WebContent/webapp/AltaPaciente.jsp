<%@page import="dominio.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="negocioImpl.*"%>
<%@page import="negocio.*"%>
<%@ page buffer="64kb" %>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
<style type="text/css">
	<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>

<title>Alta Paciente</title>

<!-- Custom styles for this template -->
<link href="bootstrap/dashboard/dashboard.css" rel="stylesheet">
<script type="text/javascript" charset="utf8" src="bootstrap/dashboard/dashboard.js"></script>

</head>
<body onLoad="myOnLoad()">	

	<%
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
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
		/* List<Localidad> listaLocalidad = new ArrayList<Localidad>();
		if (request.getAttribute("listaLocalidad") != null) {
			listaLocalidad = (List<Localidad>) request.getAttribute("listaLocalidad");
		} */
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
				<div class="registro">
					<form method="post" action="ServletGestionPacientes">
						<h1>Paciente Nuevo</h1>
						<table class="formulario">
							<tr>
								<td><label>DNI:</label></td>
								<td><input name="txtDni" type="text"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									placeholder="Ingrese un DNI" class="inputForm" size="20"
									required></td>
							</tr>
							<tr>
								<td><label>Nombres:</label></td>
								<td><input name="txtNombre" type="text"
									oninput="this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]/,'')"
									placeholder="Ingrese un Nombre" class="inputForm" size="20"
									required></td>
							</tr>
							<tr>
								<td><label>Apellidos:</label></td>
								<td><input name="txtApellido" type="text"
									oninput="this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]/,'')"
									placeholder="Ingrese un Apellido" class="inputForm" size="20"
									required></td>
							</tr>
							<tr>
								<td><label>Fecha Nacimiento:</label></td>
								<td><input id="datefield" name="txtFechaNac" type="date"
									class="inputForm" size="20" required></td>
							</tr>
							<tr>
								<td><label>Sexo</label></td>
								<td><select class="select" name="slcSexo">

										<option value="M">Masculino</option>
										<option value="F">Femenino</option>
										<option value="O">Otro</option>

								</select>
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
							</tr>
							<tr>
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
							</tr>
							<tr>
								<td><label>Localidad:</label></td>
								<td><select class="textbox" required id="localidadReal"
									name="slcLocalidad"></select>
							</tr>
							<tr>
								<td><label>Direcci&oacuten:</label></td>
								<td><textarea name="txtDireccion"
										oninput="this.value = this.value.replace(/[^a-zA-Z0-9_á_é_í_ó_ú_Á_É_Í_Ó_ÚñÑ ]/,'')"
										placeholder="Ingrese una Dirección" style="resize: none;"
										class="inputForm" cols="21" rows="3" required></textarea></td>
							</tr>
							<tr>
								<td><label>E-mail:</label></td>
								<td><input name="txtEmail" type="email"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9áéíóúÁÉÍÓÚ@._-]/,'')"
									class="inputForm" placeholder="Ingrese un Email" size="20"
									required></td>
							</tr>
							<tr>
								<td><label>Tel&eacutefono:</label></td>
								<td><input name="txtTelefono1" type="text"
									oninput="this.value = this.value.replace(/[^0-9]/,'')"
									placeholder="Ingrese un Telefono" class="inputForm" size="20"
									required></td>
							</tr>							
						</table>
						<br>

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
						<div class="text-center"> 
							<input name="insert" type="submit" value="Aceptar"
								class="btn btn-secondary btn-block btn-large">
						</div>

					</form>
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
</body>
</html>