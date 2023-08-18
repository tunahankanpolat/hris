FROM node:20-alpine
WORKDIR /hris
COPY package.json .
COPY public public
COPY src src
COPY .env .
COPY tailwind.config.js .
RUN npm install
CMD ["npm", "start"]