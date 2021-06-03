# Meli

<p align="center">
  <img src="https://github.com/jdpinedahernandez/android-meli/blob/master/gif_app.gif?raw=true" width="200" />
</p>

Meli is an application that allow the user to search for products by predictive category or by product name. It allows the user to see the detail of each product and saves those that the user has seen in detail. And provides the ability to add them to the list of favorite products and viewed products.

The project has been done under clean architecture, MVVM pattern, dependency injection using dagger and koin, fragment navigation with NavGraph, internal storage with Room, consumption of web services with Retrofit and coroutines with flow, unit test with JUnit and ui test with Espresso.

On the master branch is the implementation with dagger, unit test and ui test.
Above the koin branch is the implementation with koin and unit test.

## Presentation
On the presentation layer is shown a model based on one module with a single activity that contains 3 features in fragments: search module, products module and detail module disposed in: view, viewModel, adapters, bindingAdapters and di module by model in master branch. For the koin branch there is a single di module for the whole project

## Data
On the data layer is presented the remote (Retrofit) and local (Room) database connection model, the mapping of the data layer objects to the domain layer entities, also is located the ropository for categories with a remote data source, and a repository for products with a remote and local data source.

## Domain
On the domain layer is presented the entities worked in the project: Product and Category; also is the handler of the responses to web services and the error handling model

## UseCases
On the usecases layer are presented the four use cases used in the development of this project: FindProductById, GetProductByName, GetProducts, GetFavoriteProducts, ToggleProductFavorite, GetCategories, GetPredictiveCategory, GetProductsByCategory
