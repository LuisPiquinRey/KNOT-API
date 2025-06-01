
## Endpoints

| Method | Endpoint                        | Description                                 |
|--------|---------------------------------|---------------------------------------------|
| POST   | `/createProduct`                | Create a new product                        |
| PUT    | `/updateProduct`                | Update an existing product                  |
| DELETE | `/deleteProduct/{id}`           | Delete a product by ID                      |
| GET    | `/getProductById/{id}`          | Get product details by ID                   |
| GET    | `/availableProducts`            | List all available products                 |
| GET    | `/productsByCategory/{category}`| List products by category name              |
| GET    | `/productsByPriceRange`         | List products by price range (params)       |
| DELETE | `/deleteByCategory`             | Delete all products in a category           |
| PUT    | `/updateStock/{id}`             | Update stock for a product                  |
| GET    | `/allProducts`                  | List all products                           |
