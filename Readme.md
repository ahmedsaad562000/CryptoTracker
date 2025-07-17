
<h1><span style="color:teal">CryptoTracker</span></h1>
<img src="app/src/main/res/drawable/icon2.png" width="100" height=100 alt="CryptoTracker Logo" style="margin-right: 10px; margin-bottom: 10px;">



## A simple Android app to track cryptocurrency prices using Jetpack Compose, Ktor, and Room.

*This project is a demonstration of modern Android development practices, showcasing the use of MVI architecture, dependency injection with Koin, and a clean project structure. It is designed to be a simple yet effective example of how to build an Android application that tracks cryptocurrency prices, providing a solid foundation for further development and learning.*


## 1- Video Demo



---

## 2. 🏗️ Project Structure

```
app/
├── core/               # Core utilities and base classes
│   ├── data/           # Data layer
|   |   ├── local/      # Local data sources
|   |   ├── networing/  # Networking utilities
|   |   ├── util/       # Utility classes like Error interface , Result wrapper and helper functions
│   ├── navigation/      # Navigation components
│   ├── presentation/    # Presentation layer core classes
|
|
├── crypto/                 # Feature module for Crypto tracking
│   ├── data/               # Data layer for Crypto
|   |   ├── local 
|   |   |   ├── dao         # Data Access Objects
|   |   |   ├── data_source # Local data sources
|   |   |   ├── db          # AppDatabase class
|   |   |   ├── entity      # Database entities
|   |   ├── mapper          # Data mappers
|   |   ├── remote          
|   |   |   ├── data_source # Remote data sources
|   |   |   ├── dto         # Data Transfer Objects
|   |   ├── repo            # Repositories Implentation
|   |
|   |
|   ├── domain/             # Domain layer for Crypto
|   |   ├── model           # Domain models
|   |   ├── repo            # Repositories interfaces
|   |   ├── usecase        
|
|
├── presentation/
│   ├── coin_detail/        # Feature module for Coin Detail Screen
│   ├── coin_list/          # Feature module for Coin List Screen    
|   │   ├── action/         # Actions for UI events (Intent in MVI)
|   │   ├── component/      
|   │   ├── view/           
|   │   ├── viewmodel/       
|   │   ├── view_state/     # UI state
│   ├── model/             
├── di/                     # dependency injection modules using koin
├── theme/                  # App theme and styles
```

---

## 3. 🔗 Dependencies

List of major dependencies:

* **Room** – Local storage
* **Ktor** – Network calls
* **koin** – Dependency Injection
* **Navigation Component** – Navigation between screens
* **Coroutines / Flow** – Asynchronous programming
* **Jetpack Compose / XML** – UI components (XML used for splash screen)


---

## 4. 📁 Build Variants (if any)

| Variant       | Description         |
| ------------- | ------------------- |
| `freedebug`   | Development version |
| `freeRelease` | Release version     |

---

## 5. Notes
- The app uses **MVI (Model-View-Intent)** architecture for better state management.
- **Koin** is used for dependency injection to manage dependencies in a clean way.
- The app is structured to separate concerns clearly, making it easier to maintain and extend.
- Prices Chart is implemented using **Canvas** for custom drawing.
- The app supports both **Dark Mode** and **Light Mode** themes.
- The app is designed to be responsive and works well on different screen sizes.
- The app includes a **splash screen** implemented using XML for compatibility with older Android versions.


