FROM openjdk:11-jdk-slim
COPY ./build/libs/*.jar /usr/src/myapp/
WORKDIR /usr/src/myapp
EXPOSE 8080
CMD ["sh", "-c", "java -jar -Dspring.profiles.active=dev,dev-secret -Duser.timezone=Asia/Seoul $(ls *SNAPSHOT.jar)"]
