# Phucxfoodshop
## Introduction
An e-commerce platform specializes in selling food products to users, leveraging a robust monolithic architecture for simplicity and streamlined development. The application is containerized using Docker, ensuring consistent environments for development and deployment.
## Functionalities
 • **User Authentication:** Allows users to register, log in, and log out.
 
 • **Shipping Integration:** Uses GHN API to calculate shipping costs.
 
 • **Payment Processing:** Supports Zalopay and PayPal for payments.
 
 • **Noti cations:** Sends real-time updates to users via WebSocket.
 
 • **Order Management:** Admin can approve or reject orders.

 • **API Documentation:** Managed with Swagger/OpenAPI for RESTful API.
 
 • **Email Verification:** Required for registration, order placement, and password resets.

## Installation
1. Open Terminal or Command prompt
2. Navigate to ./Phucxfoodshop/dockercompose/hub/ (where the docker-compose.yml file is located)
3. Execute the following command
```bash
docker compose up
```
User interface: http://localhost:5173

Shop's document: http://localhost:8081/document
