# Literalura - Challenge Backend Alura

Buscardor de libros en [Gutendex](https://gutendex.com/) usando una API el cual obtendra la informacion que posteriormente estara almacenada en postgresql para su consulta

<br>

## Pre requisitos

### Variables de Entorno

Deberas agregar las siguientes variables de entorno para que la aplicacion pueda funcionar, a continuacion el nombre que cada variable de entorno debe tener

<table border="1">
    <tr style="text-align: center;">
        <td>VARIABLE</td>
        <td>DESCRIPCIÓN</td>
    </tr>
    <tr>
        <td>ALURA_HOST</td>
        <td>ruta a la base de datos con su puerto</td>
    </tr>
    <tr>
        <td>ALURA_DB</td>
        <td>nombre de la base de datos</td>
    </tr>
    <tr>
        <td>ALURA_DB_USR</td>
        <td>usuario de la base de datos</td>
    </tr>
    <tr>
        <td>ALURA_DB_PWD</td>
        <td>password de acceso a la base de datos</td>
    </tr>
</table>

<br>

## Aplicación

### *Menu*
Literalura consta de 5 opciones con las que podras interactuar, abajo imagen con las opciones:

<p width="100%">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-01.png">
</p>

### *Consulta de libros*
La aplicacion le pedira al usuario que ingrese un nombre de algun libro el cual consultara a la API, en caso de existir el libro este se guardara en la DB y mostrara informacion respecto a este

<p width="100%">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-02.png">
</p>

### *Listar libros y autores*
Estas opciones no merece descripcion, hacen lo que dice

<p width="100%">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-03.png">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-04.png">
</p>

### *Consulta de autores vivos*
Se le pedira al usuario que ingrese un año para que posteriormente se muestre una lista de autores vivos en ese año

<p width="100%">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-05.png">
</p>

### *Consultar libro por idioma*
Esta opcion muestra una lista de idiomas disponibles a partir de los que estan almacenados en la DB para que el usuario pueda elegir posteriormente la busqueda que desea realizar

<p width="100%">
    <img width="25%" src="https://storage.googleapis.com/media-github-readme/literalura-06.png">
</p>

## Alura Challenge Literalura Badge

<p align="center" width="100%">
    <img width="50%" src="https://storage.googleapis.com/media-github-readme/badge-literalura.png">
</p>