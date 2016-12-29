# pricing_service
simple pricing service

Product resource:
http://localhost:8080/api/products

- add product:
POST /api/products/ HTTP/1.1
{
	"name": "product_name"
}

- delete product:
DELETE /api/products/1 HTTP/1.1

- get products:
GET /api/products HTTP/1.1

- get product:
GET /api/products/1 HTTP/1.1

- add price for product (date should be ISO 8601):
POST /api/products/1/prices HTTP/1.1
{
	"date": "2016-08-16 08:25:15 -0500",
	"price": 9.99
}

- read prices for product:
GET /api/products/1/prices HTTP/1.1

Price resource:
- get actual prices for current date (in ISO 8601):
GET /api/prices/2016-12-29%2004:49:14%20-0500 HTTP/1.1
