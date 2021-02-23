# algamoney-api
## Docker

```
docker network create algamoneyapi_network

docker build -t algamoneyapi .

docker run -d -it -p 8082:8080 --network algamoneyapi_network --name algamoneyapi algamoneyapi

docker run -d -it --network algamoneyapi_network -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=algamoneyapi --name mysql_algamoneyapi mysql:5.7
```