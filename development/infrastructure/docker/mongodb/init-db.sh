#!/bin/bash
mongosh --host mongodb:27017 --eval -u root-dev -p SZYLUVJEvyUxEEE <<EOF
var config = {
      "_id" : "qualicharge",
      "members" : [
        {
          "_id" : 0,
          "host" : "mongodb:27017"
        },
      ]
      };
rs.initiate(config, { force: true });
EOF
