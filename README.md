GET /api/exchange-rate


**Description:**

Retrieve exchange rate records based on the base currency.

**Parameters:**

- `baseCurrency` (required): The base currency code (e.g., `ALL`, `AFN`).
- `targetCurrency` (optional): The target currency code (e.g., BND, BOB).
- `date` (optional): The date of the exchange rate in dd-MM-yyyy format (e.g., 09-08-2024).

**Example Request:**

```bash
curl "http://localhost:8080/api/exchange-rate?baseCurrency=ALL"

curl "http://localhost:8080/api/exchange-rate?baseCurrency=ALL&date=09-08-2024"

curl "http://localhost:8080/api/exchange-rate?baseCurrency=ALL&targetCurrency=BND&date=09-08-2024"

