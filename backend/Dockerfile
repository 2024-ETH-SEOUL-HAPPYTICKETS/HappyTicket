#jdk17 Image Start
FROM openjdk:17

#인자 설정 - JAR_File
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar

#jar파일 복제
COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar

# 컨테이너의 포트 지정
EXPOSE 8081

# 실행 명령어
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]
