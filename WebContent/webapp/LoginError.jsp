<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	

<style type="text/css">
		<jsp:include page="css/ErrorPage.css"></jsp:include>
	</style>

</head>
<body>
<div class="container p-5 my-5 bg-dark text-white">
	<div class="container bootstrap snippets bootdey">
		<div class="row">
			<div class="col-md-12">
				<div class="pull-center" style="margin-top: 10px;">
					<div class="col-md-10 col-md-offset-1 pull-center">
						<img class="img-error" src="css/pngegg.png">
						<h2>Usuario o Permisos Incorrectos</h2>				
						<div class="error-actions">
							<form action="login" method="post">
							<button class="w-30 btn btn-lg btn-primary" type="submit"
							value="Aceptar" name="btnAceptar">Volver</button>
							</form>
							<!-- <a href="Login.jsp" class="btn btn-primary btn-lg"> <span
								class="glyphicon glyphicon-arrow-left"></span> Volver
							</a> -->
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>