# Stage 1: Build the Vue application
FROM node:16.14.2-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Production image
FROM nginx:alpine

# Copiar los archivos de la aplicación Vue construida al directorio de NGINX
COPY --from=builder /app/dist /usr/share/nginx/html

# Copiar la configuración personalizada de NGINX con el proxy inverso
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]