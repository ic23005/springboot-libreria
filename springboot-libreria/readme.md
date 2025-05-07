# 📖 DAW135 - GT1 - CORTO 2 - IC23005 - Proyecto Librería con Spring Boot

Proyecto de backend para un sistema de gestión de librería. Generación de base de datos en Spring Boot usando JPA y PostgreSQL

---

## 📋 Prerrequisitos

Asegúrate de tener estas herramientas instaladas y configuradas en tu entorno de desarrollo:

* ☕ **Java Development Kit (JDK)**: Versión `21` o superior.
    * *Verifica con:* `java -version`
* 🧱 **Apache Maven**: Para la gestión de dependencias y la construcción del proyecto.
    * *Verifica con:* `mvn -version`
* 🐘 **PostgreSQL**: Un servidor de base de datos PostgreSQL activo.
    * *Descarga desde:* [postgresql.org](https://www.postgresql.org/download/)
* 🌿 **Git**: Para clonar el repositorio y gestionar versiones (recomendado).
    * *Descarga desde:* [git-scm.com](https://git-scm.com/downloads)

---

## 🚀 Configuración y Puesta en Marcha

Sigue estos pasos para que el proyecto funcione localmente:

### 1. 🏛️ Crear la Base de Datos en PostgreSQL

La aplicación necesita una base de datos dedicada en tu instancia de PostgreSQL. ¡Vamos a crearla!

1.  Conéctate a tu servidor PostgreSQL. Puedes usar `psql` desde la terminal o una herramienta gráfica como pgAdmin.
2.  **Crear un Rol (Usuario) para la Aplicación:**
    Es una buena práctica tener un usuario específico para tu aplicación. Reemplaza `usuario_libreria` y `password_seguro` tus credenciales:
    ```sql
    CREATE ROLE usuario_libreria LOGIN PASSWORD 'password_seguro';
    ```
3.  **Crear la Base de Datos:**
    La aplicación, por defecto, buscará una base de datos llamada `libreria_db`. Si usas otro nombre, recuerda actualizarlo en `application.properties`.
    ```sql
    CREATE DATABASE libreria_db OWNER usuario_libreria;
    ```
    * Al especificar `OWNER usuario_libreria`, este usuario tendrá todos los permisos necesarios sobre la base de datos `libreria_db`.

### 2. ⚙️ Configurar las Propiedades de la Aplicación

El corazón de la configuración de la conexión se encuentra en `src/main/resources/application.properties`. Edita este archivo con los detalles de tu base de datos:

```properties
# 🔗 URL de conexión a tu base de datos PostgreSQL.
# Si tu base de datos se llama diferente a "libreria_db", actualiza el nombre aquí.
spring.datasource.url=jdbc:postgresql://localhost:5432/libreria_db

# 👤 Usuario para la conexión (el que creaste en el paso anterior).
spring.datasource.username=usuario_libreria # ✨ ¡Reemplaza con tu usuario!

# 🔑 Contraseña para la conexión.
spring.datasource.password=password_seguro # ✨ ¡Reemplaza con tu contraseña!

# ... (otras propiedades como spring.jpa.hibernate.ddl-auto ya están configuradas)