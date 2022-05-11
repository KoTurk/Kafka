docker-compose exec kafka kafka-topics \\n  --create \\n  --bootstrap-server localhost:9092 \\n  --replication-factor 1 \\n  --partitions 1 \\n  --topic payments
docker-compose exec kafka kafka-topics \\n  --create \\n  --bootstrap-server localhost:9092 \\n  --replication-factor 1 \\n  --partitions 1 \\n  --topic fraud
docker-compose exec kafka kafka-topics \\n  --create \\n  --bootstrap-server localhost:9092 \\n  --replication-factor 1 \\n  --partitions 1 \\n  --topic rewards
docker-compose exec kafka kafka-topics \\n  --create \\n  --bootstrap-server localhost:9092 \\n  --replication-factor 1 \\n  --partitions 1 \\n  --topic blacklist
docker-compose exec kafka kafka-topics \\n  --create \\n  --bootstrap-server localhost:9092 \\n  --replication-factor 1 \\n  --partitions 1 \\n  --topic balance 

