# Deployment Script on Jenkins


docker run -p 3306:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=cedsif -e MYSQL_USER=root -e MYSQL_PASSWORD=root -d mysql:5.7

mvn clean
mvn install
docker image rm --force cedsif
docker build . -t cedsif
docker container rm --force cedsif
docker run -p 8086:8086 --name cedsifdb --link mysql-standalone:mysql -d cedsif
