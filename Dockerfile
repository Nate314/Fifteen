# Compiles the Java sources the same way run.bat does. The container then solves the puzzles
# in the mounted project folder (program_1_data.txt by default) and exits.
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY Fifteen/Fifteen/src ./src
RUN javac -d bin src/com/nathangawith/umkc/*.java

FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/bin /app/bin
WORKDIR /data
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-cp", "/app/bin", "com.nathangawith.umkc.Fifteen"]
