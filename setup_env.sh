#!/bin/bash

echo SERVER_ADDRESS=$FRONTEND_SERVER_ADDRESS >> ./frontend/.env
echo NEXTAUTH_SECRET=$FRONTEND_NEXTAUTH_SECRET >> ./frontend/.env
