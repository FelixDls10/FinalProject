Sakila ORM Management System
Proyecto final para la asignatura Lenguaje de Programación II (Java).

Este proyecto implementa un sistema de gestión de consola basado en un ORM (Object-Relational Mapping) artesanal. La aplicación permite administrar la base de datos de muestra sakila de MySQL, utilizando patrones de diseño avanzados como MVC (Modelo-Vista-Controlador) y DAO (Data Access Object) sin depender de frameworks externos como Hibernate o JPA.

Características del Proyecto

Arquitectura MVC: Separación clara entre la lógica de negocio, la capa de datos y la interfaz de usuario.


ORM Personalizado:

Implementación de una clase padre abstracta DataContext para la gestión de conexiones.

Uso de Generics e Interfaces (iDatapost) para estandarizar el CRUD.

Mapeo manual de ResultSet a Objetos Java (Entidades).

Gestión de Entidades:

Películas (Films): CRUD completo, validación de ratings y manejo de nulos.

Actores: Gestión de nombres y actualizaciones automáticas de timestamp.

Categorías: Administración del catálogo de géneros.

Configuración Externa: Credenciales de base de datos desacopladas mediante properties file.

🛠️ Tecnologías Utilizadas
Lenguaje: Java (JDK 17+)

Base de Datos: MySQL (Esquema sakila)

IDE: IntelliJ IDEA

Librerías: mysql-connector-j (JDBC Driver)

Instalación y Configuración
1. Pre-requisitos
Asegúrate de tener instalada la base de datos de ejemplo Sakila en tu servidor MySQL. El driver JDBC debe estar añadido a las librerías del proyecto (lib/mysql-connector-j-9.5.0.jar).

2. Configuración de Base de Datos
El sistema utiliza un archivo de propiedades para la conexión. Crea un archivo llamado dbconfig.properties dentro de la carpeta src/ con el siguiente contenido:

Properties

db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://127.0.0.1:3306/sakila?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
db.user=TU_USUARIO_ROOT
db.password=TU_CONTRASEÑA
3. Ejecución
Ejecuta la clase principal desde tu IDE: src/com/sakila/ui/Main.java

Estructura del Proyecto
El código está organizado siguiendo los lineamientos de empaquetado:

Plaintext

src/com/sakila
├── controller   # Lógica de interacción (Controller)
│   ├── ActorController.java
│   ├── CategoryController.java
│   ├── FilmController.java
│   └── BaseController.java
├── data         # Entidades del dominio (Model - POJOs)
│   ├── Actor.java
│   ├── Category.java
│   ├── Film.java
│   └── Entity.java (Padre abstracto)
├── model        # Lógica de acceso a datos (DAO/Repository)
│   ├── ActorModel.java
│   ├── CategoryModel.java
│   └── FilmModel.java
├── orm          # Núcleo del ORM artesanal
│   ├── DataContext.java (Gestor de Conexión)
│   └── iDatapost.java (Interfaz CRUD Genérica)
├── ui           # Punto de entrada
│   └── Main.java
└── util         # Utilidades
    └── PropertyFile.java (Lector de config)
Autor
Felix De los Santos

Matrícula: 100658633

Universidad Autónoma de Santo Domingo (UASD)

Facultad de Ciencias - Escuela de Informática.