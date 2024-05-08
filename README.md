### COMO LANZAR LA APP EN MODO DEV 

### VERSIONES
Las diferentes tecnologías utilizadas para esta fase son:

- SpringBoot 2.7.14
- Maven: 4.0.0
- Java: 19.0.2
- h2: 2.2.224
- Vue3: 5.0.8

### CONFIGURACIÓN

#### BACKEND

1. Clonar repositorio desde [https://github.com/ruky00/EnterpriseEventSolutions.git](https://github.com/ruky00/EnterpriseEventSolutions.git)

2. Cargar proyecto como Maven Project. (En caso de no hacer esto, no funcionará la aplicación) y ejecutar `mvnw install` si es la primera vez que se ejecuta.

3. El documento `application.properties` está configurado para lanzar la aplicación en modo dev. Se está comprobando algunos fallos que surgían de eliminar este elemento por defecto e intentar ejecutarlo con la configuración del launcher.

4. Abrir la terminal. `cd backend` y ejecutar `mvn package`. Este comando compila y empaqueta el proyecto en un archivo JAR, que puede ejecutarse o implementarse en un servidor. Incluye la clase compilada.

5. `cd target`

6. `java -jar EnterPriseEventSolutions-0.0.1-SNAPSHOT.jar` (La aplicación empezará a lanzarse y se instanciará la base de datos h2).

#### FRONTEND

1. Clonar repositorio (Si no se ha clonado previamente) en otro directorio de nuestro IDE (Para el front es recomendable usar VS Code).

2. En una terminal `cd frontend/EnterpriseEventSolution`

3. `npm i` (Instalaremos todas las dependencias de nuestro frontal VUE3).

4. Una vez instaladas todas las dependencias: `npm run build`.

5. Abrir el navegador y buscar [http://localhost:8080](http://localhost:8080).

### USO DE LA APLICACIÓN

1. Para ingresar en la app hay usuarios creados en la BD con los permisos necesarios para acceder a la aplicación web.
   
   - email: admin@admin.com, contraseña: pass    tipo: ADMIN
   - email: patxi@example.com contraseña: pass    tipo: ORGANIZATION
   - email: michel@example.com, contraseña: pass   tipo:  CLIENT

¡Ahora puedes navegar libremente por la página!
