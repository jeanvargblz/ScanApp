-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-08-2020 a las 20:36:49
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `qr`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nivel` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`id`, `nombre`, `email`, `password`, `nivel`) VALUES
(1, 'Jean', 'jean@gmail.com', 'b71985397688d6f1820685dde534981b', 1),
(2, 'Carlos', 'carlos@gmail.com', 'dc599a9972fde3045dab59dbd1ae170b', 1),
(3, 'Frank', 'frank@gmail.com', '26253c50741faa9c2e2b836773c69fe6', 1),
(4, 'Héctor', 'hector@gmail.com', '3ab9071536d62f29aa8b3fd39141f6ad', 1),
(5, 'Mario', 'mario@gmail.com', 'de2f15d014d40b93578d255e6221fd60', 1),
(6, 'Juan Pérez', 'jperez@gmail.com', 'ebde0645e6d2a650fb009be4dae6694e', 1),
(7, 'Sofia', 'sofia@gmail.com', '17da1ae431f965d839ec8eb93087fb2b', 1),
(8, 'Luis', 'luis@gmail.com', '502ff82f7f1f8218dd41201fe4353687', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `foto` varchar(100) NOT NULL,
  `qr` varchar(100) NOT NULL,
  `precio` decimal(7,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `descripcion`, `foto`, `qr`, `precio`, `stock`, `estado`) VALUES
(1, 'CEMENTO - YURA', 'PORTLAND TIPO IP 50 KG.', 'cementoYura.jpg', 'qr_1.png', '45.00', 130, 1),
(2, 'PALA', 'TIPO CUCHARA', 'pala.jpg', 'qr_2.png', '20.00', 5, 1),
(3, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', 'pintura.png', 'qr_3.png', '25.00', 5, 1),
(4, 'YESO', 'YESO DE 28 KG', 'yeso.jpg', 'qr_4.png', '11.50', 7, 1),
(5, 'LLAVE', 'INGLESA 9\"', 'llaveinglesa18pul.jpg', 'qr_5.png', '1.00', 8, 1),
(6, 'LIJAR', 'ASA 40\"', 'lijar.jpg', 'qr_6.png', '15.00', 20, 1),
(7, 'CARRETILLA', 'BUGUI CON LLANTA Y CAMARA', 'carretilla.jpg', 'qr_7.png', '99.00', 10, 1),
(8, 'CEMENTO - SOL', 'PORTLAND TIPO IP 45 KG.', 'cementoSol.jpg', 'qr_8.png', '45.00', 5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promociones`
--

CREATE TABLE `promociones` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `productos_id` int(11) NOT NULL,
  `foto` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `precio` decimal(7,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `tipo_movimiento` varchar(10) NOT NULL,
  `hora` text NOT NULL,
  `fecha` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `registro`
--

INSERT INTO `registro` (`id`, `nombre`, `descripcion`, `precio`, `stock`, `tipo_movimiento`, `hora`, `fecha`) VALUES
(1, 'PALA', 'TIPO CUCHARA', '20.00', 5, 'ENTRADA', '11:35:48', '17/4/2020 '),
(2, 'PALA', 'TIPO CUCHARA', '20.00', 5, 'ENTRADA', '17:44:48', '19/4/2020 '),
(3, 'CEMENTO', 'PORTLAND TIPO IP 42.5 KG.', '25.50', 10, 'ENTRADA', '18:13:43', '19/4/2020'),
(4, 'YESO', 'YESO DE 28 KG', '11.50', 7, 'ENTRADA', '18:24:38', '19/4/2020'),
(5, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'ENTRADA', '12:24:27', '20/4/2020 '),
(6, 'CEMENTO', 'PORTLAND TIPO IP 42.5 KG.', '25.50', 10, 'ENTRADA', '12:35:29', '20/4/2020 '),
(7, 'CEMENTO', 'PORTLAND TIPO IP 42.5 KG.', '25.50', 10, 'ENTRADA', '13:19:54', '20/4/2020'),
(8, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'ENTRADA', '12:11:49', '20/5/2020'),
(9, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'ENTRADA', '7:25:42', '16/6/2020'),
(10, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'SALIDA', '14:29:8', '4/7/2020'),
(11, 'CEMENTO', 'PORTLAND TIPO IP 42.5 KG.', '25.50', 10, 'SALIDA', '17:7:47', '4/7/2020 '),
(12, 'CARRETILLA', 'BUGUE CON LLANTAS Y CAMARAS', '195.00', 10, 'SALIDA', '11:15:26', '5/7/2020 '),
(13, 'CEMENTO', 'PORTLAND TIPO IP 42.5 KG.', '25.50', 10, 'SALIDA', '15:34:8', '7/7/2020'),
(14, 'LIJAR', 'ASA 40\"', '9.00', 17, 'ENTRADA', '10:55:29', '8/7/2020'),
(15, 'CARRETILLA', 'BUGUE CON LLANTAS Y CAMARAS', '195.00', 10, 'ENTRADA', '11:5:26', '8/7/2020'),
(16, 'CEMENTO - YURA', 'PORTLAND TIPO IP 50 KG.', '45.00', 130, 'ENTRADA', '12:36:49', '10/7/2020'),
(17, 'CEMENTO - SOL', 'PORTLAND TIPO IP 42.5 KG.', '60.00', 5, 'ENTRADA', '12:43:15', '10/7/2020'),
(18, 'YESO', 'YESO DE 28 KG', '11.50', 7, 'SALIDA', '16:55:41', '12/7/2020'),
(19, 'YESO', 'YESO DE 28 KG', '11.50', 7, 'ENTRADA', '11:11:31', '14/7/2020'),
(20, 'CEMENTO - YURA', 'PORTLAND TIPO IP 50 KG.', '45.00', 130, 'ENTRADA', '21:56:40', '19/7/2020'),
(21, 'CEMENTO - SOL', 'PORTLAND TIPO IP 45 KG.', '45.00', 5, 'ENTRADA', '15:17:24', '30/7/2020'),
(22, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'SALIDA', '15:18:57', '30/7/2020'),
(23, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'ENTRADA', '15:35:38', '30/7/2020'),
(24, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, 'ENTRADA', '13:22:16', '8/8/2020');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro1`
--

CREATE TABLE `registro1` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `precio` decimal(7,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `fechahora` text NOT NULL,
  `localizacion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `registro1`
--

INSERT INTO `registro1` (`id`, `nombre`, `descripcion`, `precio`, `stock`, `fechahora`, `localizacion`) VALUES
(1, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, '19/7/2020 22:1:42', 'Arias Araguez 836, Arequipa 04006, Perú'),
(2, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, '21/7/2020 7:45:57', 'Arias Araguez 830, Arequipa 04006, Perú'),
(3, 'PINTURA', 'PINTURA DE TRAFICO COLOR AMARILLO', '35.00', 15, '21/7/2020 9:12:26', 'Arias Araguez 836, Arequipa 04006, Perú');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `promociones`
--
ALTER TABLE `promociones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `productos_id` (`productos_id`);

--
-- Indices de la tabla `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `registro1`
--
ALTER TABLE `registro1`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `promociones`
--
ALTER TABLE `promociones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `registro`
--
ALTER TABLE `registro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `registro1`
--
ALTER TABLE `registro1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `promociones`
--
ALTER TABLE `promociones`
  ADD CONSTRAINT `promociones_ibfk_1` FOREIGN KEY (`productos_id`) REFERENCES `productos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
