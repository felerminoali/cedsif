# Deployment Script on Jenkins


 docker run -p 3306:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cedsif -d mysql:latest
mvn clean
mvn install

docker image rm --force cedsif


docker build . -t cedsif
docker container rm --force cedsif
docker run -p 8086:8086 --name deploy --link mysql-standalone:mysql -d cedsif
