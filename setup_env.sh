#!/bin/bash

echo SERVER_ADDRESS=$FRONTEND_NEXTAUTH_SECRET >> ./frontend/.env
echo NEXTAUTH_SECRET=$FRONTEND_SERVER_ADDRESS >> ./frontend/.env
