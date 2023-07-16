DROP SCHEMA clinicautn;
CREATE SCHEMA clinicautn;
use clinicautn;

CREATE TABLE Nacionalidad (
  idNacionalidad int not null primary key auto_increment,
  Descripcion varchar(50) not null
);

CREATE TABLE Provincia (
  idProvincia int not null primary key auto_increment,
  Descripcion varchar(20) not null
);

CREATE TABLE Localidad (
  idLocalidad int not null primary key auto_increment,
  idProvincia int not null,
  Descripcion varchar(50) not null,  
  FOREIGN KEY (idProvincia) REFERENCES Provincia(idProvincia)
);

CREATE TABLE Persona(
  DNI varchar(8) not null primary key,
  Nombre varchar(50) not null,
  Apellido varchar(50) not null,
  Sexo enum('M', 'F', 'O') not null,
  idNacionalidad int not null,
  FechaNacimiento date not null,
  Direccion varchar(50),
  idLocalidad int not null,
  Email varchar(50) not null,
  Telefono varchar(50) not null,
  FOREIGN KEY (idNacionalidad) REFERENCES Nacionalidad(idNacionalidad),
  FOREIGN KEY (idLocalidad) REFERENCES Localidad(idLocalidad)
);

CREATE TABLE Paciente(
  idPaciente int not null primary key auto_increment,
  DNI varchar(8) not null unique,
  Estado bool,
  FOREIGN KEY (DNI) REFERENCES Persona(DNI)
);

CREATE TABLE Usuarios(
  idUsuario int not null primary key auto_increment,
  NombreUsuario varchar(25) not null unique,
  Clave varchar(100) not null,
  Tipo enum('Admin', 'Medico') not null,
   Estado bool
);

CREATE TABLE Especialidad(
  idEspecialidad  int not null primary key auto_increment,
  Descripcion varchar(50) not null
);

CREATE TABLE Medico (
  DNI varchar(8) not null unique,
  idMedico int not null primary key,
  idEspecialidad int not null,
  Estado bool,
  FOREIGN KEY (idEspecialidad) REFERENCES Especialidad(idEspecialidad),
  FOREIGN KEY (idMedico) REFERENCES Usuarios(idUsuario)
);

CREATE TABLE Dia(
	idDia int not null primary key,
    Descripcion varchar(15) not null,
    
    CONSTRAINT idDia CHECK (idDia>=1 AND idDia<=7)
);

CREATE TABLE Medico_x_Dia(
  idMedico int not null,
  idDia int not null,
  HoraIngreso time not null,
  HoraEgreso time not null,
  Estado boolean not null,
  FOREIGN KEY (idDia) REFERENCES Dia(idDia),
  FOREIGN KEY (idMedico) REFERENCES Medico(idMedico),
  primary key (idDia, idMedico)
);

CREATE TABLE Estados_Turno(
  idEstado int not null primary key auto_increment,
  Descripcion varchar(20) not null
);

CREATE TABLE Turnos (
  idTurno int not null primary key auto_increment,
  idMedico int not null,  
  idPaciente int,
  Fecha date not null,
  idEstado int not null,
  Hora time not null,
  Observacion varchar(300),
  
  FOREIGN KEY (idMedico) REFERENCES Medico(idMedico),
  FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente),
  FOREIGN KEY (idEstado) REFERENCES Estados_Turno(idEstado)
);
