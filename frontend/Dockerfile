#STAGE 1
#FROM node:19-alpine3.16 AS build
#WORKDIR /usr/src/app
#COPY package.json package-lock.json ./
#RUN npm install
#COPY . ./
#RUN npm run build
#RUN npm install -g @angular/cli
#RUN ng build

#STAGE 2
#FROM nginx:alpine
#COPY nginx.conf /etc/nginx/nginx.conf
#COPY --from=build /usr/src/app/dist /usr/share/nginx/html
#EXPOSE 80

#STAGE 1
FROM node:18.13-alpine3.16
RUN mkdir app
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package*.json ./
RUN npm install
RUN npm install -g @angular/cli
COPY . ./
RUN npm run build
#CMD ng serve
CMD ng serve --host 0.0.0.0
