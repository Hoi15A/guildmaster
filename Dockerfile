FROM eclipse-temurin:17-jre

COPY ./build/libs/Guildmaster*.jar /opt/app/Guildmaster.jar

CMD ["java", "-jar", "/opt/app/Guildmaster.jar"]
