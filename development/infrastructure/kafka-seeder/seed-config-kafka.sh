#!/bin/sh
set -e

KAFKA_BROKER=${KAFKA_BROKER}
SEED_DIR=/usr/local/bin/csv-messages

echo "Waiting for Kafka ($KAFKA_BROKER) to be ready..."
until kafka-topics --bootstrap-server "$KAFKA_BROKER" --list >/dev/null 2>&1; do
  echo "Kafka is not available yet - sleeping"
  sleep 2
done

echo "Kafka is ready. Seeding messages from CSV files in $SEED_DIR..."

# Enable nullglob so unmatched *.csv expands to nothing
shopt -s nullglob 2>/dev/null || true

CSV_FILES=("$SEED_DIR"/*.csv)

if [ ${#CSV_FILES[@]} -eq 0 ]; then
  echo "No CSV files found in $SEED_DIR. Exiting."
  exit 1
fi

for csvfile in "${CSV_FILES[@]}"; do
  # Extract topic name from filename
  topic=$(basename "$csvfile" .csv)

  # Replace invalid characters in topic name with _
  topic=$(echo "$topic" | tr '[:upper:]' '[:lower:]' | tr -c 'a-z0-9._-' '_')

  # Remove any trailing underscores
  topic=$(echo "$topic" | sed 's/_*$//')

  echo "Ensuring topic '$topic' exists..."
  kafka-topics --bootstrap-server "$KAFKA_BROKER" --create --if-not-exists \
    --topic "$topic" --partitions 1 --replication-factor 1

  while IFS= read -r message || [ -n "$message" ]; do
    # Trim whitespace and remove carriage returns
    message=$(echo "$message" | sed 's/^[[:space:]]*//;s/[[:space:]]*$//' | tr -d '\r')
    [ -z "$message" ] && continue

    echo "Producing message to $topic: $message"
    echo "$message" | kafka-console-producer \
      --bootstrap-server "$KAFKA_BROKER" \
      --topic "$topic"
  done < "$csvfile"
done

echo "Seeding complete."
