# spring-boot-ai-assistant-system

## postgresql is running in the docker to check tables or any query run

``
  docker exec -it ai-postgres psql -U postgres -d ai_assistant
``

### ai-postgres data base name
### ai-assistant table name

## To test the alert use below command

``
while true; do curl -s http://localhost:8080/actuator/health > /dev/null; sleep 0.1; done
``
