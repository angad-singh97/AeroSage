# AeroSage
Backend system for AeroSage flight search engine, powered by Java, Spring Boot and MongoDB.

**Overview: **
This standalone project serves as the back-end module of the AeroSage web-application, that is a part of the two-part Full-Stack project in Java and React.

Built using Java, Spring Boot, and MongoDB, this component provides the model, and controller for the flight search, airport repository, blogpost and user review features. It makes use of Spring Boot's repository annotations to minimize boilerplate code for DB connectivity and basic CRUD calls to the database.

**How It Works:**

The goal of this project was to understand how a Spring Boot-based Java project is structured and makes use of annotations components to deliver a minimal and easily configurable application development experience. The aim was to logically structure the model, rest controller, repository connector and UI-facing data classes for each functional entity to ensure modularity and ease of refactoring even as the project grows in potential complexity.

A key goal was also to understand how to implement the business logic of flight search and route finding, given only a open-source dataset ([OpenFlights](https://openflights.org/)). Integrating this dataset by understanding the schema, modelling the project's classes after it, and then importing the data into MongoDB after using Apache Camel to convert the .dat file to a JSON format was a key step in getting the base set for the routes, airports, and airlines.

Once the dataset was integrated, exposing endpoints that allowed parameterized searches on specific routes meant understanding how to represent the routes in-memory in a efficient fashion. I began with a graph-based DFS implementation that aimed to find all possible routes in real-time with satisfactory performance. I faced a performance degradation when it came to finding more than 3-stop routes and had to retool the algorithm to implement a maximum distance bound for any potential route being checked.

This model now works satisfactorily for multi-stop routes as well. I am working to understand how I can pre-index these routes at regular intervals to deliver even faster results in real-time, given the stable route data in this context.

This project is a work-in-progress.
