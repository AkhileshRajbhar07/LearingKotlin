#!/bin/bash
# https://aws.amazon.com/blogs/database/integrate-your-spring-boot-application-with-amazon-documentdb/
#
awk 'split_after == 1 {n++;split_after=0} /-----END CERTIFICATE-----/ {split_after=1}{print > "rds-ca-" n ".pem"}' </app/global-bundle.pem

for CERT in rds-ca-*; do
	echo "Importing $CERT"
	keytool -importcert -cacerts -file "${CERT}" -alias "${CERT}" -storepass changeit -noprompt
done

rm rds*.pem
