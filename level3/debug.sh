#! /bin/bash

docker build -t icl:level3-debug --build-arg JAVA_DEBUG_OPTIONS="-g" . && \
docker run --rm -it -v "$(pwd)/Expression.icl:/app/Expression.icl" -v "$(pwd)/jFiles:/app/MathExpressionJfiles" --network host -e DEBUG=1 icl:level3-debug