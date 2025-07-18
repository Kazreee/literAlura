#literAlura
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)

**literAlura**: aplicación de consola desarrollada para consultar y almacenar información de libros utilizando la API [Gutendex](https://gutendex.com/). Los datos obtenidos se almacenan en una base de datos PostgreSQL, aparte de utilizar JPA para facilitar la implementación y acceso a los datos reduciendo el esfuerzo con lo requerido para el desafio.

## Implementaciones

1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. listar autores vivos en un determinado año
5. listar libros por idioma
9. Salir

## Implementaciones

:heavy_check_mark: `Funcionalidad 1:` Al momento que el usuario busca un libro el programa verifica la existencia del titulo introducido.

:heavy_check_mark: `Funcionalidad 2:` El programa procede a Introducir la información del libro a la base de datos sí existe en la API Gutendex y no se encuentra registrado de antes en la base de datos.

:heavy_check_mark: `Funcionalidad 3:` Listar variables según la opción seleccionada por el usuario.

## Tecnologias aplicadas

- **Java**
- **PostgreSQL** para persistencia de datos
- **JPA** para facilitar el acceso de datos
- **Gutendex API** como fuente externa de datos
- **Maven** 

## Autores

Made by [Leo Vega](https://github.com/Kazreee)
