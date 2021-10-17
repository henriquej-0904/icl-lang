FROM henriquej0904/icl-tools

COPY ./src /app/src
COPY ./MathExpression.j /app/MathExpression.j

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