FROM maven:3.9.0-eclipse-temurin-19

RUN apt-get update && apt-get install -y python3 python3-pip
RUN pip3 install asammdf

COPY server /home/app/server
COPY pythonjava /home/app/pythonjava

WORKDIR /home/app/server
RUN mvn clean package

ENTRYPOINT ["java", "-jar", "/home/app/server/target/canvas-server-0.1.1.jar"]
