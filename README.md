# Meli

<p align="center">
  <img src="https://i.imgur.com/EajLTeN.gif" width="200" />
</p>

Meli is an application that allow the user to search for products by predictive category or by product name. It allows the user to see the detail of each product and saves those that the user has seen in detail. And provides the ability to add them to the list of favorite products.

The project has been done under clean architecture, MVVM pattern, dependency injection using dagger and koin, internal storage with Room, consumption of web services with Retrofit and coroutines, unit test with JUnit and ui test with Espresso.

On the master branch is the implementation with dagger, unit test and ui test.
Above the koin branch is the implementation with koin and unit test.

## Presentation
On the presentation layer is shown a model based on 4 modules for the main view and for the detail view disposed in: view, viewModel, adapters, bindingAdapters and di module by model in master branch. For the koin branch there is a single di module for the whole project

## Data
On the data layer is presented the remote (Retrofit) and local (Room) database connection model, the mapping of the data layer objects to the domain layer entities

## Domain
On the domain layer is presented the entities worked in the project: Product and Category; also is the handler of the responses to web services and the error handling model

## UseCases
On the usecases layer are presented the four use cases used in the development of this project: FindProductById, GetPredictiveCategory, GetProducts, ToggleProductFavorite
