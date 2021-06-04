#base image
FROM openjdk:8
COPY . /src
WORKDIR /src
RUN ["javac","Main.java"]
ENTRYPOINT ["java","Main"]