# 使用官方 AdoptOpenJDK 17 映像作为基础镜像
FROM openjdk:17-oracle

# 设置工作目录
WORKDIR /app

# 复制编译好的 Spring Boot JAR 文件到容器中的工作目录
COPY target/AdProject-0.0.1-SNAPSHOT.jar /app/AdProject-0.0.1-SNAPSHOT.jar

# 暴露 Spring Boot 应用程序的端口
EXPOSE 8080

# 运行 Spring Boot 应用程序
CMD ["java", "-jar", "AdProject-0.0.1-SNAPSHOT.jar"]
