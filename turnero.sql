-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-02-2025 a las 06:32:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `turnero`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudadano`
--

CREATE TABLE `ciudadano` (
  `id_ciudadano` bigint(20) NOT NULL,
  `APELLIDO` varchar(255) DEFAULT NULL,
  `DNI` varchar(255) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `ciudadano`
--

INSERT INTO `ciudadano` (`id_ciudadano`, `APELLIDO`, `DNI`, `NOMBRE`, `EMAIL`) VALUES
(1, 'Cano Alarcon', 'lll', 'Jose Eduardo', ''),
(5, 'Cano Alarcon', '6663', 'Jose Eduardo', ''),
(8, 'Cano Alarcon', '55589', 'Jose Eduardo', ''),
(9, 'Cano Alarcon', '66666666', 'Jose Eduardo', ''),
(10, 'Cano Alarcon', '1000', 'Jose Eduardo', ''),
(18, 'guzman', '12345633', 'Jose Eduardo', ''),
(29, 'HERNANDEZ VILLEGAS', '12233', 'ROSARIO', 'ROSARIO@gmail.com'),
(34, 'Cano Alarcon', '1223654', 'Jose Eduardo', 'eduardocanoalarcon@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tramite`
--

CREATE TABLE `tramite` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `NOMBRE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `tramite`
--

INSERT INTO `tramite` (`ID`, `DESCRIPCION`, `NOMBRE`) VALUES
(1, 'RENOVACION DNI', 'Jose Eduardo'),
(4, 'RENOVACION DN', ''),
(5, 'RENOVACION DN', ''),
(6, 'RENOVACION DNI', ''),
(7, 'RENOVACION DN', ''),
(9, 'RENOVACION DN', ''),
(10, 'RENOVACION DNI 2', ''),
(11, 'RENOVACION DNI 2', ''),
(12, 'RENOVACION DNI', ''),
(13, 'RENOVACION DNI 2', ''),
(43, 'RENOVACION 2', 'omar'),
(45, 'DNI nulo', 'omar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turno`
--

CREATE TABLE `turno` (
  `IDTURNO` bigint(20) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `ESTADO` varchar(255) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `NUMEROTURNO` int(255) DEFAULT NULL,
  `id_ciudadano` bigint(20) NOT NULL,
  `tramite_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `turno`
--

INSERT INTO `turno` (`IDTURNO`, `DESCRIPCION`, `ESTADO`, `FECHA`, `NUMEROTURNO`, `id_ciudadano`, `tramite_id`) VALUES
(3, 'renovacion dni', 'En espera', '2025-03-05', 3, 1, 1),
(6, 'nuevo dni', 'En espera', '2025-01-25', 4, 1, 1),
(10, 'renovacion dni', 'PENDIENTE', '2025-02-21', 162, 1, 1),
(13, '', 'PENDIENTE', '2025-02-21', 667, 1, 1),
(14, 'renovacion dni', 'PENDIENTE', '2025-03-01', NULL, 0, 0),
(15, 'RENOVACION DNI', 'ACTIVA', '2025-03-01', 10, 10, 12),
(16, 'RENOVACION DNI', 'En espera', '2025-03-01', 15, 29, 11),
(17, 'RENOVACION DNI', 'En espera', '2025-02-27', 15, 29, 11),
(18, 'RENOVACION DNI', 'En espera', '2025-03-01', 15, 29, 11),
(21, '                                    dni nulo', 'PENDIENTE', '2025-03-08', 15, 10, 12),
(23, '                                    g', 'PENDIENTE', '2025-03-07', 15, 10, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID` bigint(20) NOT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROL` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ciudadano`
--
ALTER TABLE `ciudadano`
  ADD PRIMARY KEY (`id_ciudadano`);

--
-- Indices de la tabla `tramite`
--
ALTER TABLE `tramite`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `turno`
--
ALTER TABLE `turno`
  ADD PRIMARY KEY (`IDTURNO`),
  ADD KEY `FK_TURNO_ciudadano_id` (`id_ciudadano`),
  ADD KEY `FK_TURNO_tramite_id` (`tramite_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ciudadano`
--
ALTER TABLE `ciudadano`
  MODIFY `id_ciudadano` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `tramite`
--
ALTER TABLE `tramite`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `turno`
--
ALTER TABLE `turno`
  MODIFY `IDTURNO` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `turno`
--
ALTER TABLE `turno`
  ADD CONSTRAINT `FK_TURNO_ciudadano_id` FOREIGN KEY (`id_ciudadano`) REFERENCES `ciudadano` (`id_ciudadano`),
  ADD CONSTRAINT `FK_TURNO_tramite_id` FOREIGN KEY (`tramite_id`) REFERENCES `tramite` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
