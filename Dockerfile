FROM maven:3.9.9-eclipse-temurin-23-alpine AS maven-build

COPY pom.xml /build/
COPY src /build/src/
COPY client /build/client/

WORKDIR /build/
RUN mvn clean package

FROM eclipse-temurin:21

WORKDIR /app
# TODO! Do not hardcode the jar name!
COPY --from=maven-build /build/target/rows-backend-0.0.2-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "rows-backend-0.0.2-SNAPSHOT.jar"]
