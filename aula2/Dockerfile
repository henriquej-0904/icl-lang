FROM henriquej0904/icl:tools-dev-env AS build

COPY ./src /app/src

# Compile
WORKDIR /app/src

RUN mkdir ../bin && \
javacc MathExpressionParser.jj && \
find | grep -e ".java" > ../files.txt && \
javac -d ../bin @../files.txt && \
rm ../files.txt

# Create jar
WORKDIR /app/bin

RUN find | grep -e ".class" > ../class-files.txt && \
jar --create --file ../MathExpression.jar -e MathExpressionParser @../class-files.txt && \
rm ../class-files.txt

WORKDIR /app

# ------------------------------------------------------------

FROM henriquej0904/icl:tools-run-env

WORKDIR /app

# Copy jar and build files
COPY --from=build /app/MathExpression.jar /app/MathExpression.jar
COPY ./MathExpression.j /app/MathExpression.j