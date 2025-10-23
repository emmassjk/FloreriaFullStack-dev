# Florería Full Stack: Plataforma de Comercio Electrónico

## Descripción del Proyecto

Este repositorio contiene el código fuente completo para una plataforma de comercio electrónico (e-commerce) dedicada a la venta de arreglos florales y productos relacionados. El proyecto sigue una arquitectura **Full Stack**, separando claramente la interfaz de usuario (Frontend) de la lógica del servidor y la gestión de datos (Backend).

El objetivo principal de esta plataforma es proporcionar una solución digital completa para una floristería, permitiendo a los usuarios:

* Explorar y buscar un catálogo de productos florales.
* Gestionar un carrito de compras.
* Completar el proceso de pago y realizar pedidos.
* Acceder a un panel de administración para gestionar inventario y pedidos.

---

## Tecnologías Utilizadas

La aplicación está construida utilizando un conjunto de tecnologías modernas, dividido entre el lado del cliente y el servidor.

### Frontend
| Tecnología | Descripción |
| :--- | :--- |
| **React** | Biblioteca de JavaScript para construir interfaces de usuario dinámicas. |
| **JavaScript** | Lenguaje principal del lado del cliente. |
| **CSS** | Estilos visuales de la aplicación. |


### Backend
| Tecnología | Descripción |
| :--- | :--- |
| **Java** | Lenguaje principal del lado del servidor. |
| **Spring Boot** | Framework de Java utilizado para construir la API RESTful, la lógica de negocio y la configuración de manera eficiente. |
| **Base de Datos:  MySQL** | Sistema de gestión de bases de datos persistente. |

---

## Estructura del Repositorio

El proyecto se organiza en dos directorios principales que reflejan su arquitectura Full Stack:

| Directorio | Contenido |
| :--- | :--- |
| `floreriaReact/` | Contiene el código fuente completo del **Frontend** (interfaz de usuario de React). |
| `projectbackend/` | Contiene el código fuente completo del **Backend** (API REST, lógica de negocio y configuración de la base de datos con Java y Spring Boot). |


## Ejecucion de Proyecto

### Backend
* Inicia MySQL desde el panel de control de XAMPP.
* Abre MySQL Workbench y asegúrate de que la base de datos llamada base exista.
* Ejecutar el proyecto desde el dashboard
* ejecutar script del llenado de la base de datos

### Frontend
* Navega al directorio del frontend: FloreriaFullStack-dev-main\FloreriaFullStack-dev-main\floreriaReact\floreriaReact
*  Instala los modulos con: npm install modules
* Ejecutar con: npm run dev


