<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page buffer="64kb" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Login</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

    <!-- Bootstrap core CSS -->
<!-- <link href="dist/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
    <!-- Custom styles for this template -->
    <link href="bootstrap/sign-in/signin.css" rel="stylesheet">
  </head>
<body class="text-center">

<% 
HttpServletResponse res = (HttpServletResponse) response;
HttpSession sesion = ((HttpServletRequest) request).getSession();

	if(sesion.getAttribute("tipo")!=null){
		if(sesion.getAttribute("tipo").toString().equals("Admin")){		
			RequestDispatcher dispatcher = request.getRequestDispatcher("AdminInicio?Param=list");
			dispatcher.forward(request, response); 
			//res.sendRedirect("AdminInicio?Param=list");
			return; 
		}
		else{
			//res.sendRedirect("ServletMedicoInicio?list=1");
			RequestDispatcher dispatcher = request.getRequestDispatcher("ServletMedicoInicio?list=1");
			dispatcher.forward(request, response); 
			//res.sendRedirect("ServletMedicoInicio?list=1");
			return;
		}
	}
%>  

	<main class="form-signin">
		<form action="login" method="post">
			<img class="mb-4" src="css/utn.png" alt="Logo-UTN" width="72"
				height="57">
			<h1 class="h3 mb-3 fw-normal">Clinica UTN</h1>
			<h2 class="h3 mb-3 fw-normal">
				Log-In
				</h1>

				<div class="form-floating">
					<input type="text" class="form-control" id="floatingInput"
						name="txtUsuario" placeholder="usuario"> <label
						for="floatingInput" required>Usuario</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="floatingPassword"
						name="txtPassword" placeholder="clave"> <label
						for="floatingPassword" required>Clave</label>
				</div>

				<button class="w-100 btn btn-lg btn-primary" type="submit"
					value="Aceptar" name="btnAceptar">Login</button>
				<p class="mt-5 mb-3 text-muted">&copy; Laboratorio-4 2023</p>
				
				<div>
			<p>
				<%
					String resultado = (String)request.getAttribute("mensaje");
					String mensaje = "";
					if (resultado != null) {
						mensaje = resultado;
					}
				%>
				<%=mensaje %>
			</p>
		</div>
		</form>
	</main>
</body>
</html>
