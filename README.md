# jav-eas-patterns-provider-mngr

### Pontificia Universidad Javeriana. Bogotá.

Manager para la administración de proveedores que interactuan con la aplicación para al trabajo "socialización II" de la asignatura patrones 
de diseño, en la Especialización de Arquitectura de Software Empresarial 2020 I.

### Integrantes:

* Andres Martinez Cobos
* Fabian Acero
* Robinson Torres

* * *

### Recursos:

<table>
    <tr>
        <td>PATH</td>
        <td>DESCRIPCIÓN</td>
        <td>VERBO</td>
        <td>HTTP CODE OK</td>
        <td>HTTP CODES FAILED</td>
    </tr>
    <tr>
        <td>/advisor</td>
        <td>Permite la consulta de todos los proveedores</td>
        <td>GET</td>
        <td>200 - CREATED -</td>
        <td>404 - NOT_FOUND - No se encuentran proovederes registrados</td>
    </tr>
    <tr>
        <td>/advisor</td>
        <td>Permite la creación de nuevos proveedores</td>
        <td>POST</td>
        <td>201 - CREATED -</td>
        <td>406 - NOT_ACCEPTABLE - Creación invalida</td>
    </tr>
    <tr>
        <td>/advisor/{filter}</td>
        <td>Permite la consulta de un proveedor en particular por un filtro dado. Validos: <br>
            CATEGORY,
            ID,
            IDENTIFICATION
        </td>
        <td>GET</td>
        <td>200 - OK -</td>
        <td>404 - NOT_FOUND- No se encuentra información relacionada al filtro</td>
    </tr>
</table>

