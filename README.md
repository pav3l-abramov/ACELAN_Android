# Acelan Application for Android
This is a native application for Android. In this application you can view materials, tasks and their details, build 3D models and graphs, tables. This application has customization of colors depending on the selected theme and device orientation.
## Libraries and technologies used in this application
This application uses MVVM architecture.

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.8.10-blue)](https://kotlinlang.org/) [![Kotlin coroutines Version](https://img.shields.io/badge/Kotlin%20Coroutines-gray)](https://kotlinlang.org/docs/coroutines-overview.html) [![Jetpack Compose Version](https://img.shields.io/badge/Jetpack%20Compose-gray)](https://developer.android.com/develop/ui/compose) [![retrofit Version](https://img.shields.io/badge/Retrofit-2.9.0-blue)](https://square.github.io/retrofit/) [![Okhttp3 Version](https://img.shields.io/badge/Okhttp-4.12.0-blue)](https://github.com/square/okhttp) [![Hilt Version](https://img.shields.io/badge/Hilt-2.48-blue)](https://dagger.dev/hilt/) [![Coil Version](https://img.shields.io/badge/Coil-2.4.0-blue)](https://coil-kt.github.io/coil/compose/) [![rxjava Version](https://img.shields.io/badge/RxJava3-3.1.8-blue)](https://github.com/ReactiveX/RxKotlin) [![Room Version](https://img.shields.io/badge/Room-2.6.1-blue)](https://developer.android.com/training/data-storage/room)

## Requirements
This app can be installed on Android with version  8.0 or newer.

## Key Features

- Tasks list
- Materials list
- 3D model viewer
- 2D graph viewer
- Table viewer
- Customise graph
- Custom color theme
- Landscape orientation render

## About App
  
### Before authorization

When you first launch the application you are greeted with a Splash screen. Next comes checking the existence of the user in the local database on the device. If it is not there, then we ask the user to authorize on the profile screen. If any error occurs on the server or client side, then we indicate it in Toast. Upon successful authorization, we enter the user's token into the database.

<p align="center">
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/short%20start.gif" alt="login screen" width="170" />
  $~~~~~~~~~~~~$
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/full%20start.gif" alt="login screen" width="600" />
</p>

### After authorization

We provide the user with full access to the data received from the server after authorization (the user receives data only from his account). Material and task data are entered into different tables and the application constantly looks at the values using Flow.
<p align="center">
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/short%20after%20start.gif" alt="login screen" width="170" />
 $~~~~~~~~~~~~$
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/full%20after%20start.gif" alt="login screen" width="600" />
</p>

### Material screens

On the materials screen, the user can see the entire list of materials with the ability to filter the lists and search for materials through the backend. The material data on the materials screen and the material details screen are entered into the same database table, although the data comes in different formats on these screens. In the material details screen, we can see different data depending on the type of material, and we can also add that material to be displayed in graphs.
<p align="center">
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/short%20material%20screen2.gif" alt="login screen" width="170" />
  $~~~~~~~~~~~~$
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/full%20material%20screen2.gif" alt="login screen" width="600" />
</p>

### Graph screens

To display graphs, you need to add at least two materials of the same type. You can build graphs for isotropic and anisotropic materials. The specificity of the construction for anisotropic materials is that there are many different parameters that do not need to be displayed all, so the user is given the opportunity to select the desired parameters in the table. The user on the graph settings screen can change the color of the line or point, the divide factor for all different parameters with validation, display the names of materials on the graphs and the line.

<p align="center">
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/short%20graph.gif" alt="login screen" width="170" />
  $~~~~~~~~~~~~$
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/full%20graph.gif" alt="login screen" width="600" />
</p>

### Task screens

On the tasks screen, the user can see the entire list of tasks that the user has added in his personal account. On the task card you can see the status, name, start and end of the task. The user can sort and filter the task list. On the task details screen, the user can see a list of master data for all tasks. Depending on the task, the user can display pictures of different formats, graphics; if the task contains a link to a 3D model (.ply, .stl, .obj), then the user can display it on his screen by clicking on the button.

<p align="center">
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/short%20task.gif" alt="login screen" width="170" />
  $~~~~~~~~~~~~$
    <img src="https://github.com/pav3l-abramov/ACELAN_Android/blob/main/gif/full%20task%20(1).gif" alt="login screen" width="600" />
</p>
