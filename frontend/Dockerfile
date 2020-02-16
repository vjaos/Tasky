FROM node:alpine
WORKDIR /usr/app/front
EXPOSE 3000
COPY ./frontend ./
RUN npm install
CMD ["npm", "run", "serve"]