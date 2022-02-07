#!/bin/bash

curl -X POST -H "Content-Type: application/json" \
    -d '{"name": "Belfast", "cinemaName": "ODEON Stillorgan"}' \
    http://localhost:8081/movie
