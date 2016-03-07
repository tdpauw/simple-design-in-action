FROM java:openjdk-8-jdk

ADD ./target/simple-design-in-action-*-jar-with-dependencies.jar /usr/share/simple-design-in-action/simple-design-in-action.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/share/simple-design-in-action/simple-design-in-action.jar"]
