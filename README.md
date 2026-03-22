# fast-retire

docker build -t kamilkazmierczak/fast-retire:latest .

docker run --network=host \
  -e SPRING_PROFILES_ACTIVE=devlocal \
  kamilkazmierczak/fast-retire:latest
