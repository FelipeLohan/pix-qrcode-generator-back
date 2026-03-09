#!/bin/sh
set -e

echo "Iniciando aplicação Spring Boot..."
java -jar /app/app.jar &
APP_PID=$!

echo "Aguardando aplicação ficar pronta na porta 8080..."
until nc -z 127.0.0.1 8080; do
    sleep 1
done

echo "Aplicação pronta. Iniciando nginx na porta 8081..."
exec nginx -g 'daemon off;'
