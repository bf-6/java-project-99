#FROM gradle:8.9-jdk21
#
#WORKDIR .
#
#COPY . .
#
#RUN gradle installDist
#
#CMD ./build/install/app/bin/app
#
##

FROM gradle:8.9-jdk21 AS build
WORKDIR /app
COPY src ./src
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN gradle installDist

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/install/app /app
ENV SPRING_PROFILES_ACTIVE=development
EXPOSE 8080
CMD ["./bin/app"]