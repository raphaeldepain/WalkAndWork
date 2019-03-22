# WalkAndWork
Dépôt du projet Walk&amp;Work

[![Build Status](https://travis-ci.org/raphaeldepain/WalkAndWork.svg?branch=master)](https://travis-ci.org/raphaeldepain/WalkAndWork)
[![License](https://img.shields.io/github/license/raphaeldepain/WalkAndWork.svg?style=flat-square)](LICENSE)
[![Version](https://img.shields.io/github/tag/raphaeldepain/WalkAndWork.svg?label=version&style=flat-square)](build.gradle)
[![Waffle.io - Columns and their card count](https://badge.waffle.io/raphaeldepain/WalkAndWork.svg?columns=all)](https://waffle.io/raphaeldepain/WalkAndWork)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=raphaeldepain_WalkAndWork&metric=code_smells)](https://sonarcloud.io/dashboard?id=raphaeldepain_WalkAndWork)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=raphaeldepain_WalkAndWork&metric=coverage)](https://sonarcloud.io/dashboard?id=raphaeldepain_WalkAndWork)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=raphaeldepain_WalkAndWork&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=raphaeldepain_WalkAndWork)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=raphaeldepain_WalkAndWork&metric=security_rating)](https://sonarcloud.io/dashboard?id=raphaeldepain_WalkAndWork)




# WalkAndWork

WalkAndWork est une application qui permet de simplifier les intéractions entre recruteurs et chercheurs d'emplois.

## Qu'est ce que c'est ?

Imaginez pouvoir rencontrer un jour le travail de vos rêves simplement en traverssant la rue ! Il s'agit du concept de WalkAndWork qui permet à travers la géolocalisation et l'identification des compétences de lier candidats et recruteurs qui se cherchent.

## Fonctionnalités 


## Système de Build
* [Gradle](https://gradle.org/)

## Téléchargement

L'application peut-être téléchargée ici :  link

## Installation

### Prérequis

Avant de pouvoir utiliser notre projet, il faut procéder aux installations suivantes.

#### Installer Android Studio et le SDK

- Télécharger Android studio et l'installer : [Télécharger Android Studio](https://developer.android.com/studio/index.html)

- Télécharger le SDK : 

Depuis la fenêtres de bienvenue : 
  Configure > SDK Manager > SDK TOOLS > Cocher Android SDK Tools > OK
  
#### Installer Git

- Télécharger et installer Git : [Télécharger Git] (https://gitforwindows.org/)

### Cloner WalkAndWork

Une fois Git installé, il suffit de suivre les instructions ci-dessous.

#### Lier Git à Android Studio 

Depuis Android studio : 
  File > Settings > Version Control > Git > Dans " path to git Excecutable ", coller le chemin du git.exe > OK
  
#### Cloner le répertoire WalkAndWork

Depuis Android studio : 
  VCS > Checkout from version control > Git > Dans " Git Repository URL ", coller https://github.com/raphaeldepain/WalkAndWork.git > OK


#### Builder l'apk
cd ./WalkAndWork
./gradlew
ou sur windows :
./gradlew.bat

et enfin :

adb install -r WalkAndWork.apk


 Et voila ! le projet est prêt pour utilisation.

## Auteurs
* **Raphaël DEPAIN** - [Github](https://github.com/raphaeldepain)
* **Saïd AMRANI** - [Github](https://github.com/amrani-s)
* **Soukaina ASLIMI** - [Github](https://github.com/soukainaaslimi)
