#! /bin/bash

docker build -t icl:level2-debug --build-arg JAVA_DEBUG_OPTIONS="-g" . && \
docker run --rm -it -v "$(pwd)/Expression.icl:/app/Expression.icl" --network host -e DEBUG=1 icl:level2-debug