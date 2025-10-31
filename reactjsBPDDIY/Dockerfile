# STAGE 1 : BUILD
FROM node:24.10-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install

COPY . .
RUN npm run build

# STAGE 2 : RUNTIME
FROM nginx:alpine AS runtime

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD [ "nginx","-g","daemon off;" ]
