### COMO LANZAR LA APP EN MODO DEV 

### VERSIONES
Las diferentes tecnologías utilizadas para esta fase son:

- SpringBoot 2.7.14
- Maven: 4.0.0
- Java: 19.0.2
- MySQL:8.0.28
- Vue3: 5.0.8

### CONFIGURACIÓN

#### BACKEND

1. Clonar repositorio desde [https://github.com/ruky00/EnterpriseEventSolutions.git](https://github.com/ruky00/EnterpriseEventSolutions.git)

2. Cargar proyecto como Maven Project. (En caso de no hacer esto, no funcionará la aplicación) y ejecutar `mvnw install` si es la primera vez que se ejecuta.

3. El documento `application.properties`  teine por defecto algunas configuraciones pero según el modo de lanzamiento de la app, se cargarán otros archivos de configuración.

4. Abrir la terminal. `cd backend` y ejecutar `mvn package`. Este comando compila y empaqueta el proyecto en un archivo JAR, que puede ejecutarse o implementarse en un servidor. Incluye la clase compilada.

5. `cd target`

6. Para poder utilizar la app en producción  hay que crearse una Base de Datos MySQL, lo más sencillo es rapido es usar Docker.

   6.1 `docker pull mysql:8.0.28` Descargará la imagen de docker.io (Obviamente tenemos que disponer de docker en nuestro sistema pero esta parte no entra en el scope del TFG).
   
   6.2 ` docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password mysql:8.0.28` Lanzará la imagen en un contenedor Docker que será expuesto en el puerto 3306 para su uso.

   6.3 Para el Servicio de Email hay que crearse un correo y generar una contraseña para aplicaciones [https://support.google.com/mail/answer/185833?hl=es#:~:text=En%20%22C%C3%B3mo%20inicias%20sesi%C3%B3n%20en,Selecciona%20Generar.](https://support.google.com/mail/answer/185833?hl=es#:~:text=En%20%22C%C3%B3mo%20inicias%20sesi%C3%B3n%20en,Selecciona%20Generar.)
   Aqui se puede ver los pasos a seguir para realizar esto. Y de igual forma crearse las variables de entorno en el sistema para hacer uso de ellas y no exponerlas en la aplicación.
   Si no dispones de esto, no podrás crear usuarios y utilizarlos con normalidad ya que no podrán ser activados debido a la confirmación por Correo Electrónico.
   
7. `java -jar "-Dspring.profiles.active=<dev o prod>" EnterPriseEventSolutions-0.0.1-SNAPSHOT.jar` Lanzará nuestra apliación.
   
   7.1 Para poder hacer uso del perfil en producción es necesario configurar un perfil de AWS y crear un bucket de S3.
   
   7.2 Crearse en las variables de entorno del sistema todas aquellas properties que se encuentren embedidas en ${}.

   7.3 Cabe destacar que solo será necesario para el perfil prod, el perfil dev esta configurado para no instanciar estos servicios. Se usaran Mocks y servicios auxiliares para no tener que hacerse AWS ni Gmail.

#### FRONTEND

1. Clonar repositorio (Si no se ha clonado previamente) en otro directorio de nuestro IDE (Para el front es recomendable usar VS Code).

2. En una terminal `cd frontend/EnterpriseEventSolution`

3. `npm i` (Instalaremos todas las dependencias de nuestro frontal VUE3).

4. Una vez instaladas todas las dependencias: `npm run build`.

5. Instalar serve para lanzar el entorno en producción `npm install -g serve`

6. Abrir el navegador y buscar [http://localhost:8080](http://localhost:8080).

### A dia 10/5/2024 Hay problemas con la configuración del front para su `build`. En caso de que no funcionara correctamente, Iniciar con `npm run serve` (Se subirá una correccíon con la mayor brevedad posible)


### USO DE LA APLICACIÓN

1. Para ingresar en la app hay usuarios creados en la BD con los permisos necesarios para acceder a la aplicación web.
   
   - email: admin@admin.com, contraseña: pass    tipo: ADMIN
   - email: patxi@example.com contraseña: pass    tipo: ORGANIZATION
   - email: michel@example.com, contraseña: pass   tipo:  CLIENT

¡Ahora puedes navegar libremente por la página!
