# Guía para Agentes de IA - AplicacionLogin

## Visión General
AplicacionLogin es una aplicación Android desarrollada en Kotlin que proporciona funcionalidades para un diario personal y gestión emocional. La aplicación utiliza Firebase para autenticación y almacenamiento de datos.

## Arquitectura

### Componentes Principales
- **Activities**: Cada pantalla es una Activity que hereda de `AppCompatActivity`
- **Firebase Integration**:
  - `FirebaseAuth` para autenticación de usuarios
  - `FirebaseFirestore` para almacenamiento de datos
  - Los datos del usuario se almacenan en la colección `usuarios/{uid}/`

### Estructura de Datos
- **Diario Personal**:
  - Ubicación: `usuarios/{uid}/diario/`
  - Campos: `fecha`, `contenido`, `timestamp`
  - Se mantiene un contador `totalEntradas` en el documento del usuario

## Patrones y Convenciones

### Manejo de UI
```kotlin
// Patrón común para inicialización de vistas
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_name)
    // Inicializar Firebase
    // Inicializar vistas con findViewById
    // Configurar listeners
}
```

### Operaciones Firebase
```kotlin
// Patrón para operaciones de escritura
db.collection("colección")
    .document(id)
    .collection("subcolección")
    .add(datos)
    .addOnSuccessListener { /* Manejar éxito */ }
    .addOnFailureListener { /* Manejar error */ }
```

## Flujos de Trabajo

### Desarrollo
1. Las activities se encuentran en `app/src/main/java/com/example/aplicacionlogin/`
2. Los layouts XML correspondientes están en `app/src/main/res/layout/`
3. Los recursos (strings, colores, etc.) se definen en `app/src/main/res/values/`

### Firebase
- Requiere archivo `google-services.json` en el directorio `app/`
- Las reglas de seguridad deben configurarse en la consola de Firebase

## Consejos para el Desarrollo
1. Verifica siempre el estado de autenticación antes de operaciones con datos
2. Usa transacciones para operaciones que requieran consistencia
3. Implementa manejo de errores en todas las operaciones de Firebase
4. Mantén la coherencia en el nombrado: activities con sufijo "Activity"

## Archivos Clave
- `EscribirDiarioActivity.kt`: Ejemplo de interacción con Firebase y UI
- `activity_login.xml`: Layout para autenticación de usuarios
- `AndroidManifest.xml`: Configuración de la aplicación y activities