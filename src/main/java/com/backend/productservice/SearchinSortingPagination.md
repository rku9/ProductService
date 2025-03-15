** SEARCHING **
* A search request can be a GET or a POST.
* If someone is using the **GET** request then it will be visible to the public via the URL. This can be shared.
* When it's a post request then it could become difficult because of the request body.
* **GET** queries are easier to **optimize**. For e.g. when a new iPhone is launched we can assume that when that
  keyword is used, the accepted response is the same. In this scenario, we can cache the results.

** PAGINATION **
* We cannot return all the results of a query in one go as it could result in the 1000s. Huge latency and frontend
  might crash. Also, not all the products are relevant.
* We can divide those products in multiple segments.
* For segment 1 the products will be from 1 to 20(say). 2nd will have 21-40 and so on and so forth.
* This 20 is called the **limit** and the starting point(21, 41, etc.) is called the **offset**.
* Other way to solve this issue is to use infinite scrolling.
* Spring JPA can be used for pagination. In the function where we are returning all the products(findAll()), we can
  return with the data type Page<Product> instead of List<Product> and then in the method parameter pass something
  that is "Pageable".
* This is an interface so we have to pass in the object of the class that implements this interface. This is the 
  class PageRequest. Create an object of this class using .of(int pageNumber, int pageSize) method.
* Product Controller will pass these parameters to the product service via @RequestParam.
* Either we can return the Page<Product> or we change it to List<Product> from the controller.

** SORTING**
* By default, the product is not sorted, and we might want to sort the data not based on the id but based on some
  other attribute(price for e.g.).
* In this scenario we define the custom Sort in the service fn and pass it to the PageRequest method in the service.