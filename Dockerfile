FROM isuper/java-oracle:jdk_8

ENV MAVEN_VERSION 3.3.9

COPY target/simulationservice.jar simulationservice.jar

RUN which java

ENTRYPOINT ["java"] 
CMD ["-jar","simulationservice.jar"]