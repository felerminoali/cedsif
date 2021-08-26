# Deployment Script on Jenkins

mvn clean
mvn install
docker image rm --force cedsif
docker build . -t cedsif
docker container rm --force cedsif
docker run -p 8086:8086 --name cedsif-mysql --link mysql-standalone:mysql -d cedsif
