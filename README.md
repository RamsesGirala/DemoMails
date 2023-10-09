# Microservicio de Firma Electrónica en Spring Boot

Este microservicio desarrollado en Spring Boot 3.1.4, con Java 17, proporciona la funcionalidad de persistir informacion en una base de datos y enviar correos electronicos con un archivo pdf adjunto.
Se conecta a una base de datos PostgreSQL 15. Antes de usarlo, debes configurar la conexión a la base de datos y a la API de Google para enviar correos electrónicos.

## Configuración

Antes de ejecutar el microservicio, asegúrate de configurar las siguientes propiedades en el archivo `src/main/resources/application.properties`:

### Configuración de la Base de Datos PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_basededatos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```
### Configuración del smtp(Gmail en este caso)
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_contraseña
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
## Uso
Puedes usar el endpoint POST /civising/serviciofirma para enviar una solicitud JSON.

Ejemplo de Solicitud JSON
```
{
    "docname": "Documento de Prueba 1.pdf",
    "documento": "Documento en Base64",
    "metadata": {
        "cargo": "tester",
        "razon": "prueba de desarrollo",
        "localidad": "Mendoza"
    },
    "fecha": "19/06/2023 10.30.12",
    "Lista_emails": ["ramsesgiralald@gmail.com", "joaconavio10@gmail.com"],
    "url_callback": "https://www.jus.mendoza.gob.ar/test_firma"
}
```
