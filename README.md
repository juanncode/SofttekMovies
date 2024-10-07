# Aplicación de Películas

Construye una aplicación de películas con las siguientes funcionalidades:

## Puesta en marcha
1. Crearse un archivo keys.properties en la raiz del proyecto
2. Copiar estos valores y colocar tu token de theMovieDb
  - BASE_URL="https://api.themoviedb.org/3/"
  - TOKEN_MOVIES="TOKEN"
3. Credenciales para entrar:
  - email: example@test.es
  - password: 123

## Funcionalidades

### 1. Pantalla de Login
- Crear una pantalla para realizar login con **usuario** y **contraseña**.
- Validar los inputs correctamente.
- **No es necesario consumir un Web Service**; la validación será interna:
    - **Usuario**: `Admin`
    - **Contraseña**: `Password*123`

### 2. Listado de Películas con Paginación
- Una vez validado el login, mostrar un listado de películas con **paginación**.
- Mostrar **20 ítems por página**.

### 3. Detalle de la Película
- Al seleccionar un ítem de la búsqueda, mostrar una pantalla de **detalle** con la siguiente información:
    - **Poster** de la película: `120x200` (campo: `poster_path`).
    - **Nombre** de la película (campo: `title`).
    - **Nota** de la película (campo: `vote_average`).
    - **Fecha de lanzamiento** (campo: `release_date`).
    - **Resumen** de la película (campo: `overview`).

### 4. Almacenamiento Offline
- Almacenar las películas buscadas para cargarlas nuevamente cuando **no haya conexión a internet**.

### 5. Manejo de Errores
- Implementar manejo de errores en caso de **problemas de conexión** con la API.

### 6. Animaciones y Transiciones
- Usar **animaciones** y **transiciones** entre las vistas para mejorar la experiencia del usuario.
