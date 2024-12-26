# EP Practica 3

## _Diseño y Pruebas Unitarias · Sistema de Micromobilidad Compartida_

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/projects/jdk/17/)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-%23FE5196?logo=conventionalcommits&logoColor=white)](https://conventionalcommits.org)
[![Contributors](https://img.shields.io/badge/contributors-3-g?style=plastic)](https://github.com/Computer-Engineering-UdL/EP-Practica3)

### Description

This project is the third iteration of a micromobilty system. This iteration is centered to implement some
functionalities of the system in Java.
To ensure the compliance of the requirements, the project is developed using double testing via JUnit.

The package structure diagram is the following:
```mermaid
graph TD
    A["EP-Practica3 (root)"]
    A1[src]
    A2[test]
    A1_1[data]
    A1_2[micromobility]
    A1_3[services]
    A1_4[utils]
    A2_1[data]
    A2_2[micromobility]
        A2_2_1[mock]
    A2_3[services]
        A2_3_1[mock]
    
    A --> A1
    A --> A2
    A1 --> A1_1
    A1 --> A1_2
    A1 --> A1_3
    A1 --> A1_4
    A2 --> A2_1
    A2 --> A2_2 --> A2_2_1
    A2 --> A2_3 --> A2_3_1
```

### Authors

- [Serrano Ortega, Aniol](https://github.com/Aniol0012)
- [Boulhani Zanzan, Hamza](https://github.com/Jamshaa)
- [Sànchez Hidalgo, Carles](https://github.com/carless7)
