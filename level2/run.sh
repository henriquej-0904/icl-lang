#! /bin/bash
docker run --rm -it -v "$(pwd)/Expression.icl:/app/Expression.icl" --network host icl:level2