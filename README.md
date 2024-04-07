**Project Overview**

This project is developed using Spring Boot and includes entities for Product, Customer, Cart, and Order, along with a BaseEntity. The relationship between Customer, Cart, and Order is established, allowing a customer to have one cart and multiple orders. The total price of the cart and order items is calculated and saved with each operation (addition, removal, quantity change) in the cart. 

Additionally, a customer can see the historical price of products in their orders, even if the price has been updated since the purchase. Stock tracking is implemented to prevent orders exceeding available stock. 

**Services**

- **AddCustomer**: Adds a new customer.
- **GetProduct**: Retrieves product information.
- **CreateProduct**: Creates a new product.
- **UpdateProduct**: Updates an existing product.
- **DeleteProduct**: Deletes a product.
- **GetCart**: Retrieves cart information for a customer.
- **EmptyCart**: Empties the cart.
- **PlaceOrder**: Places an order for the items in the cart.
- **GetOrderForCode**: Retrieves an order by order code.
- **GetAllOrdersForCustomer**: Retrieves all orders for a customer.
- **AddProductToCart**: Adds a product to the cart.
- **RemoveProductFromCart**: Removes a product from the cart.

**Documentation**

Swagger documentation for the API endpoints can be found [Swagger Documentation](http://localhost:8080/swagger-ui.html).

**Docker Compose**

To run the application using Docker Compose, navigate to the project directory and run the following command:

```bash
docker-compose up
