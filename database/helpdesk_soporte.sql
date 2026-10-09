-- ============================================
-- HELPDESK SOPORTE - SCRIPT DE BASE DE DATOS
-- ============================================

CREATE DATABASE IF NOT EXISTS helpdesk_soporte;

USE helpdesk_soporte;

-- ============================================
-- TABLA ROL
-- ============================================

CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================
-- TABLA CATEGORIA
-- ============================================

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(80) NOT NULL UNIQUE
);

-- ============================================
-- TABLA PRIORIDAD
-- ============================================

CREATE TABLE prioridad (
    id_prioridad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_prioridad VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================
-- TABLA ESTADO TICKET
-- ============================================

CREATE TABLE estado_ticket (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================
-- TABLA USUARIO
-- ============================================

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- ============================================
-- TABLA TICKET
-- ============================================

CREATE TABLE ticket (
    id_ticket INT AUTO_INCREMENT PRIMARY KEY,
    codigo_ticket VARCHAR(20) NOT NULL UNIQUE,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    id_prioridad INT NOT NULL,
    id_estado INT NOT NULL,

    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    FOREIGN KEY (id_prioridad) REFERENCES prioridad(id_prioridad),
    FOREIGN KEY (id_estado) REFERENCES estado_ticket(id_estado)
);

-- ============================================
-- TABLA REQUERIMIENTO (Solicitud de Nuevo Requerimiento)
-- ============================================

CREATE TABLE requerimiento (
    id_requerimiento INT AUTO_INCREMENT PRIMARY KEY,
    codigo_requerimiento VARCHAR(20) NOT NULL UNIQUE,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    id_prioridad INT NOT NULL,
    id_estado INT NOT NULL,

    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    FOREIGN KEY (id_prioridad) REFERENCES prioridad(id_prioridad),
    FOREIGN KEY (id_estado) REFERENCES estado_ticket(id_estado)
);

-- ============================================
-- TABLA PREGUNTA FRECUENTE (Consulta / FAQ)
-- ============================================

CREATE TABLE pregunta_frecuente (
    id_pregunta INT AUTO_INCREMENT PRIMARY KEY,
    pregunta VARCHAR(255) NOT NULL,
    respuesta TEXT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA ARTICULO DE CONOCIMIENTO (Base de Conocimiento)
-- ============================================

CREATE TABLE articulo_conocimiento (
    id_articulo INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL,
    id_categoria INT,
    id_autor INT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME,

    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    FOREIGN KEY (id_autor) REFERENCES usuario(id_usuario)
);

-- ============================================
-- INSERCIÓN DE DATOS DE PRUEBA
-- ============================================

INSERT INTO rol(nombre_rol)
VALUES
('Administrador'),
('Tecnico'),
('Usuario');

INSERT INTO categoria(nombre_categoria)
VALUES
('Hardware'),
('Software'),
('Redes'),
('Accesos');

INSERT INTO prioridad(nombre_prioridad)
VALUES
('Baja'),
('Media'),
('Alta'),
('Urgente');

INSERT INTO estado_ticket(nombre_estado)
VALUES
('Pendiente'),
('En Proceso'),
('Atendido'),
('Cerrado');

-- Contraseñas cifradas con BCrypt.
-- admin -> admin123 | tecnico -> tecnico123 | usuario -> usuario123
INSERT INTO usuario (nombres, apellidos, correo, password, id_rol)
VALUES
('Jennyfer', 'Mesta', 'jennyfer@test.com', '$2a$10$2/lcGm0THdqJXibj0ie75OcuZDXvNCZgjHSJy46Ilr3sx4oZPA1D.', 1),
('Carlos', 'Soporte', 'soporte@test.com', '$2a$10$QwTP75K7cUZHeOSGi56dv.RinHw.FIU/Hp19w6NyURTB/OBsjixZq', 2),
('Luis', 'Usuario', 'usuario@test.com', '$2a$10$BmtjPew60Pal3dqpLVSjGO5tcUCk6xQXhmRghw6N/A54ALKie9ZFa', 3);

INSERT INTO ticket
(codigo_ticket, titulo, descripcion, id_usuario, id_categoria, id_prioridad, id_estado)
VALUES
('TK-0001','Error de acceso al sistema','El usuario no puede ingresar al portal',3,4,3,1),
('TK-0002','Computadora no enciende','El equipo no responde al presionar el botón de encendido',3,1,4,1),
('TK-0003','Error al imprimir','La impresora muestra error de conexión',3,1,2,2),
('TK-0004','Instalación de Office','Se requiere instalación de Microsoft Office',3,2,1,3),
('TK-0005','Sin acceso a Internet','La computadora no navega en internet',3,3,4,2),
('TK-0006','Restablecer contraseña','El usuario olvidó su contraseña institucional',3,4,2,3),
('TK-0007','VPN no conecta','No se puede establecer conexión remota',2,3,3,2),
('TK-0008','Pantalla azul','El sistema presenta BSOD al iniciar',3,1,4,1),
('TK-0009','Correo corporativo bloqueado','No se puede acceder a Outlook',1,4,3,2),
('TK-0010','Actualización de software','Actualizar versión del sistema ERP',2,2,2,3),
('TK-0011','Configuración de impresora','Agregar impresora de red al equipo',3,1,1,4),
('TK-0012','Lentitud del sistema','La computadora tarda demasiado en responder',1,2,3,1),
('TK-0013','Cable de red dañado','Se detectó daño físico en el cableado',2,3,2,2),
('TK-0014','Solicitud de acceso','Nuevo colaborador requiere acceso al sistema',3,4,1,3),
('TK-0015','Error en aplicación web','La aplicación muestra error 500 al guardar',1,2,4,1),
('TK-0016','Servidor no disponible','No se puede acceder al servidor principal',2,3,4,2);

-- ============================================
-- SOLICITUDES DE REQUERIMIENTO (seed)
-- ============================================

INSERT INTO requerimiento
(codigo_requerimiento, titulo, descripcion, id_usuario, id_categoria, id_prioridad, id_estado)
VALUES
('REQ-0001','Nuevo equipo para colaborador','Se solicita laptop para nuevo ingreso del área de ventas',3,1,2,1),
('REQ-0002','Instalación de software de diseño','Se requiere licencia e instalación de Adobe Photoshop',2,2,1,2),
('REQ-0003','Acceso a carpeta compartida','Solicito acceso de lectura/escritura a la carpeta de Finanzas',3,4,2,1),
('REQ-0004','Ampliación de almacenamiento','Se requiere aumentar el espacio en disco del servidor de archivos',1,3,3,3),
('REQ-0005','Nueva cuenta de correo','Solicito la creación de una cuenta de correo para el área de soporte',2,4,1,4);

-- ============================================
-- PREGUNTAS FRECUENTES (seed)
-- ============================================

INSERT INTO pregunta_frecuente (pregunta, respuesta, fecha_creacion)
VALUES
('¿Cómo creo un nuevo ticket de incidencia?',
 'Ve a "Nuevo Ticket" en el menú lateral, completa el título, descripción, categoría y prioridad, y presiona "Guardar Ticket". Podrás seguir su estado desde "Mis Tickets".',
 NOW()),
('¿Cuál es la diferencia entre un ticket y un requerimiento?',
 'Un ticket reporta una incidencia o problema (algo que dejó de funcionar). Un requerimiento es una solicitud de algo nuevo, como un equipo, acceso o instalación de software.',
 NOW()),
('¿Cómo recupero mi contraseña si la olvidé?',
 'Si ya iniciaste sesión, puedes cambiarla desde "Perfil". Si no recuerdas tu contraseña actual, contacta a un Administrador para que la restablezca.',
 NOW()),
('¿Cuánto tiempo tarda en atenderse un ticket o requerimiento?',
 'Depende de la prioridad asignada: los casos Urgentes y de Alta prioridad se atienden primero. Puedes consultar el estado en todo momento desde "Mis Tickets" o "Mis Requerimientos".',
 NOW());

-- ============================================
-- ARTÍCULOS DE LA BASE DE CONOCIMIENTO (seed)
-- ============================================

INSERT INTO articulo_conocimiento (titulo, contenido, id_categoria, id_autor, fecha_creacion, fecha_actualizacion)
VALUES
('Cómo restablecer tu contraseña institucional',
 'Ingresa a la pantalla de login y contacta al administrador para generar una nueva contraseña temporal. Al iniciar sesión por primera vez se recomienda cambiarla desde tu perfil.',
 4, 1, NOW(), NOW()),
('Pasos ante una pantalla azul (BSOD)',
 '1) Anota el código de error mostrado. 2) Reinicia el equipo. 3) Si el error persiste, ejecuta el diagnóstico de memoria de Windows. 4) Registra un ticket de categoría Hardware con el código de error.',
 1, 2, NOW(), NOW()),
('Solución rápida: sin acceso a Internet',
 'Verifica el cable de red o la conexión Wi-Fi. Reinicia el router/switch. Si el problema continúa, ejecuta "ipconfig /release" y "ipconfig /renew" desde la consola y registra un ticket de categoría Redes.',
 3, 2, NOW(), NOW()),
('Instalación estándar de Microsoft Office',
 'Descarga el instalador desde el portal institucional, inicia sesión con tu correo corporativo y sigue el asistente. Si la licencia no activa automáticamente, registra un ticket de categoría Software.',
 2, 1, NOW(), NOW());

-- ============================================
-- CONSULTAS DE VALIDACIÓN (opcional)
-- ============================================

-- SELECT * FROM usuario;
-- SELECT * FROM ticket;
-- SELECT * FROM articulo_conocimiento;
