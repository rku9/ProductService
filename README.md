A ProductService class responsible for retrieving and updating product data from multiple sources, including a local database (localhost), AWS RDS, and the Fake Store API. It follows the MVC design pattern, where:
* The client interacts with the controller.
* The controller forwards requests to the service layer.
* The service layer communicates with the repository to access the database.

Enhanced the ProductService by:
* Integrating Redis caching, significantly reducing response time.
* The controller now communicates with the service using DTOs.
* Added search, sorting, and pagination logic for efficient data retrieval.
* Exception handling is managed through a controller advice mechanism for cleaner error handling.
