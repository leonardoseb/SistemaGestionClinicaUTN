CREATE DEFINER=`root`@`localhost` PROCEDURE `registrarPaciente`(
IN dni varchar(8), 
IN nombre varchar(50),
IN apellido varchar(50), 
IN sexo enum('M','F','O'), 
IN idnacionalidad int,
IN fechanacimiento date,
IN direccion varchar(50),
IN idlocalidad int, 
IN email varchar(50),
IN estado tinyint,
IN telefono varchar(50))
BEGIN
	INSERT INTO Persona (DNI, Nombre, Apellido, Sexo, idNacionalidad, FechaNacimiento, Direccion, idLocalidad, Email, Telefono) VALUES (dni,nombre,apellido,sexo,idnacionalidad,fechanacimiento,direccion,idlocalidad,email,telefono);
	INSERT INTO Paciente(DNI, Estado) VALUES (dni,1);
END;