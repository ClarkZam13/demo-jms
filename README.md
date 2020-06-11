Compilar: mvn clean package<br>
Ejecutar: java -jar target/demo-0.0.1-SNAPSHOT.jar<br>
Ruta de acceso : http://localhost:8080/demo<br>

Usuario: user<br>
Clave: Al iniciar el app la deja en la consola luego del texto "Using generated security password"<br>

Se utilizó la librería Swagger 2 para evitar el uso de postman, una vez logeado<br>
se debe ir a la ruta http://localhost:8080/demo/swagger-ui.html<br>

NOTA: Incluso ingresando primero a la ruta swagger, redirecciona al login y luego de logearse redirecciona
automáticamente a la ruta inicial (swagger)<br>

Al generar ventas irá creciendo el tiempo de respuesta hasta colapsar y dar error<br>

En swagger se pueden ver los dos endpoints en el controlador VentaController<br>

Una vez logeado:<br>
GET -> http://localhost:8080/demo/venta <br>
POST -> http://localhost:8080/demo/venta/generar -> body: {"monto":1010101}<br>