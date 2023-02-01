# challenge farmu

## API's (se adjunta Postman Collection)

### Implementacion docker:
Para levantar use el comando: ````docker-compose up -d````
***

### shortener-api: funcionalidad que permite crear una URL corta para redireccionar a la URL original.i. ej:

-Crea una url corta: (se guarda en una base redis, para su recuperación)
````
curl --location --request POST 'http://localhost:8282/api/v1/shortener-url' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url":"https://www.google.com/search?q=url+shortener&sxsrf=AJOql"
````

-Recupera la url mediante su id: se recupera desde redis
````
curl --location --request GET 'http://localhost:8282/api/v1/shortener-url/26a9e2f04b4841eaee2c2612467cd053'
````
***

### image-resize: funcionalidad que permite dada una imagen como input obtener una copia con las nuevas dimensiones indicadas.

-Asincrónico: 
ejecuta el guardado en base
el servicio responde los detalles de la imagen, el ID de la imagen q se utiliza para recuperarla
procesa el redimensionado en un evento
````
curl --location --request POST 'http://localhost:8282/api/v1/image-resizer/upload' \
--form 'file=@"/C:/Users/nsalone/Pictures/hollow.png"' \
--form 'targetHeight="100"' \
--form 'targetWidth="100"'
````

-Sincrónico: solo se realiza el redimensionado de la imagen (no guarda en db)
````
curl --location --request POST 'http://localhost:8282/api/v1/image-resizer/resize' \
--form 'file=@"/C:/Users/nsalone/Pictures/hollow.png"' \
--form 'targetHeight="100"' \
--form 'targetWidth="100"'
````

-Devuelve la imagen con las nuevas dimensiones (desde db)
````
curl --location --request GET 'http://localhost:8282/api/v1/image-resizer/7a1b2eea-9dd9-4ea1-9781-8532d0cf15d5'
````

-Devuelve la imagen original que fue subida al server (desde db)
````
curl --location --request GET 'http://localhost:8282/api/v1/image-resizer/original/7a1b2eea-9dd9-4ea1-9781-8532d0cf15d5'
````
***

### Posibles ISSUES:
En algún caso no se realizaba la conexión entre los containers (posgres / challenge).
Implementar una custom network:  ````docker network create custom_network````

***
##### By Nico Salone.
***