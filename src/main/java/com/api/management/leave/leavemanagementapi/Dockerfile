FROM eclipse-temurin:17

LABEL maintainer="johnchristopherilacad27@gmail.com"

WORKDIR /app

COPY target/leave-management-api-0.0.1-SNAPSHOT.jar /app/leave-management-api.jar

ENTRYPOINT ["java", "-jar", "leave-management-api.jar"]