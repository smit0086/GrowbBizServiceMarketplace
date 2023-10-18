#!/bin/bash

# Setup environment variables for frontend
echo NEXT_PUBLIC_SERVER_ADDRESS=$FRONTEND_SERVER_ADDRESS >> ./frontend/.env
echo NEXTAUTH_SECRET=$FRONTEND_NEXTAUTH_SECRET >> ./frontend/.env
echo NEXTAUTH_URL=$FRONTEND_NEXTAUTH_URL >> ./frontend/.env

# Setup environment variables for docker compose deployment
echo JDBC_USERNAME=${JDBC_USERNAME} >> ./.env
echo JDBC_URL=${JDBC_URL} >> ./.env
echo JDBC_PASSWORD=${JDBC_PASSWORD} >> ./.env
echo GROWBIZ_SECRET_KEY=${GROWBIZ_SECRET_KEY} >> ./.env
