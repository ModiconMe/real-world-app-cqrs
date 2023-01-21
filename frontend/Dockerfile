#STAGE 1
FROM node:19-alpine3.16 AS build
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
RUN npm install -g @angular/cli
ENTRYPOINT ["ng", "serve"]
#RUN ng serve

#STAGE 2
#FROM nginx:1.17.1-alpine
#COPY nginx.conf /etc/nginx/nginx.conf
#COPY --from=build /usr/src/app/dist/ /usr/share/nginx/html
