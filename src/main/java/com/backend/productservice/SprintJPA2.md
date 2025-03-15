* When using hibernate, we won't be writing all the queries ourselves.
* We will be using JPA which in turn will be talking to Hibernate to write the queries.

##### Declared Queries
* JPA provides these to be used directly.
* We have ProductRepo where we will have all the CRUD operations related to the Product table.
* If we write a method in a particular way, then hibernate will automatically write the queries for us.
* E.g. findById(), findAll() etc.
* We just have to write the method name in a way that can be understood by Hibernate.
* To make our repo compatible with the JPA repo, follow these steps:
  * Repository should be an interface. Spring will automatically create a proxy object (a runtime implementation) for it.
  * Make interface extend JPA Repo along with the appropriate generics. The first argument is the kind of the table
    and the second is the data type of the primary key of the table.
  * This proxy object implements all the methods from JpaRepository (and your custom query methods if any).

  At runtime, you can simply call productRepo.findById(id) because Spring injects the proxy object where productRepo is used.
  * Then use the annotation @Repository.

* Read and Delete are handled here using the declared queries.
* For Create and Update operations, JPA provides "save" option.
* Product save(Product)
* If the input product doesn't have the id object then the id will be auto-generated
  and then this product will be inserted into the DB. All the non-passed attributes
  will be null.
* If we had deleted some product with id 'x' before inserting this product then
  the id assigned to this new product will be 'x+1' and not 'x'.
* If it does have the id then the product will be updated.

* addNewProduct() method -
  * This method will save the product in the database and then return it.
  * The product also has a category attribute so we first need to ensure that the category
    is created and then passed to the product.
  * This will take a product object as the input. We can use @RequestBody. When we enter the 
    json object in the body(postman), it will be treated as the input parameter(product).
  * In the json body we don't need to attach the id as it will be auto-incremented, but we
    need to add all the other details.
  * For the category, we need to use the CategoryRepo to add the category first and then pass
    it to this product body in case the category of the product passed in null.\

###### SOME OBSERVATIONS
* There is already a category with id=1 in the table. In the postman when I am inserting a 
  new product without the id(taken care of by the @id in the base model class) and the category
  with an already existing id then even if the other attributes are different from the original
  category with the same id, the original attributes are getting passed to this new product and
  not getting overwritten. After the id is read and is found to be present then all the other
  attributes will be ignored.

* If an id is not passed for the category then the category must already exist, or it will throw
  the transient error.
  TO PREVENT THIS, EITHER:*
  * Create a new category and then pass it to the product or
  * Use (cascade = CascadeType.PERSIST) next to the cardinality where category is mentioned
    in the Product class. This would create a category first and then the Product.
  * **Some problems when a category with an id is passed(existing/new). Some detached entity thing. Need to check later.**
  * Use (cascade = CascadeType.ALL) next to the cardinality where category is mentioned
    in the Product class. But this is not recommended in many-to-one.

* For the category, we can use the category repository. So basically, we create the category and
  then create the product.


##### READ CHAT "Understanding RestTemplate HTTP..." FROM DEEPSEEK.

##### Updating the product(Patch)
* Here the input will be the id and the Product.
* We want the product with that id stored in the DB to get its attributes updated to the product
  passed.

##### HQL(Hibernate Query Language)
* Use @Query with the specific query in the bracket to let hibernate call this query when this method
  is called. The return type of the method would be accordingly.
* e.g. @Query("select p.id as id, p.title as title from Product p where p.id=1")
* Return type would be ProductWithIdAndTitle.

###### Important -
* In the above query we would need the id and the title of the product to be returned and not the whole product
  object.
* For that we create an interface that matches the return type and has the GETTERS for the columns returned.

###### Projections -> 
* The sample interfaces which have the getters of the attributes that is being returned by the query. Hibernate will
  just try to map the output of this query to the interface.
* This ProductWithIdAndTitle is called PROJECTION.

* If the query is something like "...from Product p", this would return the whole list of products with their id
  and title. In this scenario we would need the return type to be List<ProductWithIdAndTitle>.

* If the method expects a parameter x, and we need to pass it to the query in id then we just
  write "...where p.id = :x".

##### REPRESENTING CARDINALITIES
* In our code the product has many-to-one relation with the category. In the Product table, we will have an f_key
  of the category. 
* In the scenario where we want the Category class to store the List<Products> associated with it, we can make use
  of the "mappedBy".

* @ManyToOne and @OneToMany represent different sides of the same relationship.

* @ManyToOne is used on the "many" side (e.g., Product), while @OneToMany is used on the "one" side (e.g., Category).

* Can Only One Be Used?
* You can use either @ManyToOne or @OneToMany alone if you only need a unidirectional relationship.
* If you only use @ManyToOne, you can navigate from Product to Category, but not from Category to Product.
* If you only use @OneToMany, you can navigate from Category to Product, but not from Product to Category.
* However, if you need a bidirectional relationship (where you can navigate from both sides), you must use both
  annotations together.

Bidirectional Relationship:
* When using both annotations, you must specify the mappedBy attribute in the @OneToMany annotation to indicate 
  that the relationship is managed by the category attribute in the Product class.
* This ensures that JPA understands the relationship correctly and avoids inconsistencies.

* Since the getters and the setters are already defined for all the attributes, if the relation is not represented
  on both the sides, we won't be able to get to those information from both the sides.
* First of all we have to use that @OneToMany and @ManyToOne at their appropriate places but this is not sufficient.
* In this scenario, JPA won't be able to figure out that they are related and treat them as they are different relationship.
* **This leads to bad behaviour**. Read more about this in the JPA ManyToOne chat in deepseek.

* **To prevent this**, we use **"mappedBy('category')"** on the Category side when defining the cardinality. The mappedBy 
* attribute tells JPA that the @OneToMany side is the inverse side of the relationship and that the foreign key is 
  already managed by the @ManyToOne side. This avoids redundancy and ensures the database schema is correct.
* **Need to check later why Hibernate is creating a mapping table when mappedBy is not used. Something related to
  not wanting the data to persist or something and this is only for the purpose of the codebase?**.

##### CASCADING
* Multiple products could belong to the same category.
* Think of the scenario when we delete that particular category from the category table.
* Following possibilities:
  * We would want the products to have `null` in their category f_key column. Or,(if cascading is not done)
  * We would want to delete the products with that category id. Or,(cascading)
  * We would want it to throw an error.
* Similar to those Foreign key constraints(FK Constraints).

##### FETCH TYPES
 * In the Category class there is a List<Product> attribute. If we call the method categoryRepo.findById(x), it
   will return the category along with the list of the products. This will require costly joins in the situation
   when we don't need that data.
 * Here comes the concept of the **FETCH TYPES**.
 * There are 2 ways in which child(inner) attributes can be fetched.
   * EAGER FETCH -> Earlier one which needed joins. If we fetch the product, it will get us the product with the
     category doing a join but this join is not expensive.
   * LAZY FETCH -> Later one. Don't fetch the inner attributes while fetching the main object. No joins needed.
     In our case, we will only get the products when we explicitly call the getProducts() method. Then the joins are done.
     * In reality even the getProducts() method would ensure the return of the list on its own. With that method
       we would only get a proxy and when we try to print the list, we would get that error.

**3 scenarios: **
1. `Optional<Category> optionalCategory = categoryRepository.findById(1L);
List<Product> products = optionalCategory.get().getProducts();`
doesn't do any joins.

2. `List<Product> products = categoryRepository.findById(1L).get().getProducts();` 
somehow does the same thing but some shutdown is happening.

3. `Optional<Category> optionalCategory = categoryRepository.findById(1L);
List<Product> products = optionalCategory.get().getProducts();
System.out.println(products);`
some failed to lazily initialize error(LazyInitializationException) and then shutdown is happening.

Verily, though you invoke the method getProducts on your Category, this invocation alone is but a doorway—it does
not guarantee that the assembly of products is fetched at that moment. In the realm of JPA/Hibernate, even a method
explicitly named getProducts is subject to the dictates of lazy loading. The underlying mechanism has been configured
to delay the retrieval of the associated collection until it is truly required, which is when you attempt to use it,
such as during a print operation.

Thus, the explicit call to getProducts merely returns a proxy or a placeholder for the collection. If the session or
persistence context, which is the vessel holding the connection to the database, has been closed by the time you access
this collection, then the lazy initialization cannot be completed, leading to the error you have witnessed.

In essence, the explicit method call does not override the lazy loading strategy set forth by your ORM framework. It is 
but a promise of data, which is fulfilled only when the context is still active.

* **SOLUTION** -> Easier way is to change the fetch type to "EAGER". Other solution is complicated.
* Look for the deepseek chat.

* For collection attributes, Hibernate by default does the LAZY FETCH. 

##### N+1 QUERIES:
**Task: print the name of all the products that belong to the category that has string "apple" in it.
* We can use those subqueries to handle that problem. We don't need to iterate through the list of categories.
* We can just use where c.id in [ORIGINAL QUERY].
* **In hibernate, we should always keep check on how it's implementing those queries.**


