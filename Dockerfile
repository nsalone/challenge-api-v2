FROM maven:3.6.0 AS build

COPY src /home/farmu-challenge/farmu/src
COPY pom.xml /home/farmu-challenge/farmu
RUN mvn -f /home/farmu-challenge/farmu/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:11
COPY --from=build /home/farmu-challenge/farmu/target/challenge-0.0.1-SNAPSHOT.jar /usr/local/lib/farmu.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/farmu.jar"]
