FROM openjdk:17
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/* ./osm.core.jar
EXPOSE 8090
CMD ["java","-jar", "-Dspring.profiles.active=docker","osm.core.jar"]