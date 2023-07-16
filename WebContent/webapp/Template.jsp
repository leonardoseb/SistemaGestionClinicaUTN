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
					<h1 class="h2">Dashboard - Template</h1>
				</div>
				
				<!-- ACA DEBERIA IR LO QUE SE QUIERE MOSTRAR -->
				
			</main>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>