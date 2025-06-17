import requests

url = "http://localhost:8080/api/findAllProducts"
headers = {
    "Accept": "application/json",
    "Content-Type": "application/json"
}

response = requests.get(url, headers=headers)

if response.status_code == 200:
    productos = response.json()
    print(productos)
else:
    print(f"Error: {response.status_code} - {response.text}")