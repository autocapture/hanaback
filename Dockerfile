FROM openjdk:17-ea-11-jdk-slim
ARG JAR_FILE=build/libs/kakao.jar
COPY ${JAR_FILE} ./kakao.jar
ENV TZ=Asia/Seoul
EXPOSE 44303 8083
ENTRYPOINT ["java","-jar", "./kakao.jar"]