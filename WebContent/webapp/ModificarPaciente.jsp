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

<title>Modificar Paciente</title>

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
		Paciente pa = new Paciente();
		if (session.getAttribute("paciente")!=null){
			pa = (Paciente) session.getAttribute("paciente");
			
		}
		String id = String.valueOf(pa.getLocalidad().getProvincia().getIdProvincia());
		String id2 = String.valueOf(pa.getLocalidad().getIdLocalidad());
%>
		<!-- MISMO FORMULARIO QUE AGREGAR PERO CON DATOS PRECARGADOS -->
		
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
						<h1>Modificar Paciente</h1>
						<table class="formulario">
							<tr>
								<td><label>DNI</label></td>
								<td><input name="txtDni" type="text" class="inputForm"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9]/,'')"
									size="20" readonly value="${paciente.dni}"></td>
							</tr>
							<tr>
								<td><label>Nombres</label></td>
								<td><input name="txtNombre" type="text" class="inputForm"
									oninput="this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]/,'')"
									size="20" required value="${paciente.nombre}"></td>
							</tr>
							<tr>
								<td><label>Apellidos</label></td>
								<td><input name="txtApellido" type="text" class="inputForm"
									oninput="this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]/,'')"
									size="20" required value="${paciente.apellido}"></td>
							</tr>
							<tr>
								<td><label>Fecha Nacimiento</label></td>
								<td><input id="datefield" name="txtFechaNac" type="date"
									class="inputForm" size="20" required
									value="${paciente.fechaNacimiento}"></td>
							</tr>
							<tr>
								<td><label>Sexo</label></td>
								<td><select class="select" name="slcSexo">
										<%
										if (pa.getSexo() == 'M') {
										%>

										<option selected value="M">Masculino</option>
										<option value="F">Femenino</option>
										<option value="O">Otro</option>
										<%
										} else if (pa.getSexo() == 'F') {
										%>

										<option value="M">Masculino</option>
										<option selected value="F">Femenino</option>
										<option value="O">Otro</option>
										<%
										} else {
										%>
										<option value="M">Masculino</option>
										<option value="F">Femenino</option>
										<option selected value="O">Otro</option>
										<%
										}
										%>
								</select></td>
							</tr>
							<tr>
								<td><label>Nacionalidad</label></td>
								<td><select class="select" name="slcNacionalidad">
										<%
										int value = pa.getNacionalidad().getIdNacionalidad();	
										String descripcionNac = pa.getNacionalidad().getDescripcion();
										%>
										<option selected="selected" value=<%=value%>><%=descripcionNac%></option>

										<%
										ArrayList<Nacionalidad> listaNacionalidad = null;

										if (request.getAttribute("listaNacionalidad") != null) {

											listaNacionalidad = (ArrayList<Nacionalidad>) request.getAttribute("listaNacionalidad");
										}

										if (listaNacionalidad != null)
											for (Nacionalidad es : listaNacionalidad) {

												if (es.getIdNacionalidad() != value) {
										%>
										<option value="<%=es.getIdNacionalidad()%>">
											<%=es.getDescripcion()%>
										</option>
										<%
										}
										}
										%>
								</select></td>
							</tr>
							<tr>
								<td><label>Provincia:</label></td>
								<td><select class="textbox" onchange="cargar_localidades()"
									required id="provincia1" name="slcProvincia" value=<%=id%>>
									<%
										int valueProv = pa.getLocalidad().getProvincia().getIdProvincia();
										String descripcionProv = pa.getLocalidad().getProvincia().getDescripcion();
									%>
										<option selected="selected" value=<%=valueProv%>><%=descripcionProv%></option>
										
										<%
										ArrayList<Provincia> listaProvincia = null;

										if (request.getAttribute("listaProvincia") != null) {

											listaProvincia = (ArrayList<Provincia>) request.getAttribute("listaProvincia");
										}

										if (listaProvincia != null) {
											for (Provincia es : listaProvincia) {
												//if (es.getIdProvincia() != Integer.parseInt(id)){
										%>
										<option value="<%=es.getIdProvincia()%>">
											<%=es.getDescripcion()%>
										</option>

										<%
										}
										}
										%>

								</select></td>
							</tr>
							<tr>
								<td><label>Localidad:</label></td>
								<td><select class="textbox" required id="localidadReal" 
									name="slcLocalidad" value=<%=id2%>>
									<% String descripcionLocalidad = pa.getLocalidad().getDescripcion(); %>
										<option selected="selected" value=<%=id2%>><%=descripcionLocalidad%></option>
										<%
										ArrayList<Localidad> listaLocalidad = null;

										if (request.getAttribute("listaLocalidad2") != null) {

											listaLocalidad = (ArrayList<Localidad>) request.getAttribute("listaLocalidad2");
										}

										if (listaLocalidad != null) {
											for (Localidad lo : listaLocalidad) {
												if (lo.getIdLocalidad() != Integer.parseInt(id2)) {
										%>
										<option value="<%=lo.getIdLocalidad()%>">
											<%=lo.getDescripcion()%>
										</option>

										<%
										}
										}
										}
										%>

								</select>
							</tr>
							<tr>
								<td><label>Direcci&oacuten</label></td>
								<td><textarea name="txtDireccion" style="resize: none;"
										oninput="this.value = this.value.replace(/[^a-zA-Z0-9_á_é_í_ó_ú_Á_É_Í_Ó_ÚñÑ ]/,'')"
										class="inputForm" cols="21" rows="3" required>${paciente.direccion}</textarea></td>
							</tr>
							<tr>
								<td><label>E-mail</label></td>
								<td><input name="txtEmail" type="email" class="inputForm"
									oninput="this.value = this.value.replace(/[^a-zA-Z0-9áéíóúÁÉÍÓÚ@._-]/,'')"
									size="20" required value="${paciente.email}"></td>
							</tr>
							<tr>
								<td><label>Tel&eacutefono</label></td>
								<td><input name="txtTelefono1" type="text"
									class="inputForm"
									oninput="this.value = this.value.replace(/[^0-9]/,'')"
									size="20" required value="${paciente.telefono}"></td>
							</tr>							
						</table>
						<br>
						<div class="text-center"> 
							<input name="btnModificarPaciente" type="submit"
								value="Aceptar" class="btn btn-secondary btn-block btn-large">
						</div>

						<script>
							var temp =
						<%=id%>
							;
							var mySelect = document
									.getElementById('provincia1');

							for (var i, j = 0; i = mySelect.options[j]; j++) {
								if (i.value == temp) {
									mySelect.selectedIndex = j;
									break;
								}
							}
						</script>




						<script>
							var temp =
						<%=id2%>
							;
							var mySelect = document
									.getElementById('localidadReal');

							for (var i, j = 0; i = mySelect.options[j]; j++) {
								if (i.value == temp) {
									mySelect.selectedIndex = j;
									break;
								}
							}
						</script>

					</form>
				</div>


						<select name="localidades" id="localidad2">
							<option selected="selected" value=<%=id2%>>${paciente.localidad}</option>
							<%
							ArrayList<Localidad> listaLocalidad2 = null;

							if (request.getAttribute("listaLocalidad") != null) {
								listaLocalidad2 = (ArrayList<Localidad>) request.getAttribute("listaLocalidad");
							}

							if (listaLocalidad2 != null) {
								for (Localidad lo : listaLocalidad2) {
									if (lo.getIdLocalidad() != Integer.parseInt(id2)) {
							%>

							<option value="<%=lo.getProvincia().getIdProvincia()%>"
								data-uid="<%=lo.getIdLocalidad()%>"><%=lo.getDescripcion()%></option>

							<%
							}
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
							document.getElementById("datefield").setAttribute(
									"max", today);
						</script>


						<script>
							function myOnLoad() {
								var earrings = document
										.getElementById('localidad2');
								earrings.style.visibility = 'hidden';
								//cargar_localidades();
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
									b.push(x.options[i]
											.getAttribute('data-uid'));

								}

								addOptions("slcLocalidad", array, a, b);
							}
						</script>

						<script>
	function addOptions(domElement, array, a,b) {
		 var select = document.getElementsByName(domElement)[0];
		 var inde = document.getElementById('provincia1').value;

		 for (value in array) {
			if(a[value] === inde){
		  var option = document.createElement("option");
		  option.text = array[value];
		  option.value = b[value];
		  select.add(option);
			}
		 }
		}
	</script>


				<!-- 	</form>
				</div> -->

			</main>			
		</div>
	</div>
</body>
</html>