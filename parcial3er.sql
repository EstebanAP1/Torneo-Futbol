-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-12-2022 a las 06:39:04
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `parcial3er`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `nombreEquipo` varchar(40) NOT NULL,
  `paisEquipo` varchar(40) NOT NULL,
  `codigoEquipo` int(5) NOT NULL,
  `puntosEquipo` int(5) NOT NULL,
  `partidosGanadosEquipo` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugadores`
--

CREATE TABLE `jugadores` (
  `nombreJugador` varchar(40) NOT NULL,
  `cedula` int(20) NOT NULL,
  `equipoJugador` varchar(40) NOT NULL,
  `numAmarillas` int(20) NOT NULL,
  `numRojas` int(20) NOT NULL,
  `numGoles` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidos`
--

CREATE TABLE `partidos` (
  `manejoEquipo1` varchar(40) NOT NULL,
  `manejoEquipo2` varchar(40) NOT NULL,
  `numGolesEquipo1` int(20) NOT NULL,
  `numGolesEquipo2` int(20) NOT NULL,
  `numAmarillasEquipo1` int(20) NOT NULL,
  `numAmarillasEquipo2` int(20) NOT NULL,
  `numRojasEquipo1` int(20) NOT NULL,
  `numRojasEquipo2` int(20) NOT NULL,
  `cierre` int(1) NOT NULL,
  `codigoPartido` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD PRIMARY KEY (`codigoEquipo`);

--
-- Indices de la tabla `jugadores`
--
ALTER TABLE `jugadores`
  ADD PRIMARY KEY (`cedula`);

--
-- Indices de la tabla `partidos`
--
ALTER TABLE `partidos`
  ADD PRIMARY KEY (`codigoPartido`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `partidos`
--
ALTER TABLE `partidos`
  MODIFY `codigoPartido` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=147;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
