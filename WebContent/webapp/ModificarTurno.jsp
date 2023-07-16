<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modificar Turno</title>
<link rel="Stylesheet" href="css/Login.css" />
</head>
<header class="navbar">
	<img class="imagen" src="css/utn.png" alt="Logo Utn"> <a
		class="titulo">CLÍNICA UTN</a> <a class="textGestion">Gestion
		Turnos </a> <a class="textUsuario">Usuario Administrador </a>
	<div style="font-size: xx-small;">
		<a href="#">perfil</a> / <a href="#">editar perfil</a> / <a href="#">logout</a>
		/ <a href="#">Gestion Turnos</a> / <a href="#">Gestion Pacientes</a> /
		<a href="#">Gestion Medicos</a>
	</div>
</header>
<body class="cuerpo">
<br>
<br>
<br>
	<div>
		<p>Modificar Turno</p>
	</div>
	<div>
		<table>
			<tr>
				<td><a>Especialidad: </a></td>
				<td><input type="text" placeholder="Traumatologia"></td>
			</tr>
			<tr>
				<td><a>Medico: </a></td>
				<td><input type="text" placeholder="Apellido del Medico">
				</td>
			</tr>
			<tr>
				<td><a>Dia: </a></td>
				<td><input type="text" placeholder="Dia del turno"></td>
			</tr>
			<tr>
				<td><a>Hora: </a></td>
				<td><input type="text" placeholder="Horario del turno"></td>
			</tr>
			<tr>
				<td><a>Dni Paciente: </a></td>
				<td><input type="text" placeholder="DNI del paciente">
				</td>
			</tr>
			<tr>
				<td><a>Nombre: </a></td>
				<td><input type="text" placeholder="NOMBRE del paciente"></td>
			</tr>
			<tr>
				<td><a>Apellido: </a></td>
				<td><input type="text" placeholder="APELLIDO del paciente"></td>
			</tr>
		</table>
	</div>
	<br>
	<div>
		<input class="btnAceptar" type="submit" value="Buscar Turno">
	</div>
	<div>
		<br> <input class="btnCancelar" type="button" value="Cancelar">
		<input class="btnAceptar" type="submit" value="Modificar/Aceptar">
	</div>
</body>
</html>