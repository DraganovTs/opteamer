# Use the official Node.js image as a builder
FROM node:18 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json (or yarn.lock)
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the application
RUN npm run build

# Use nginx to serve the application
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 4200
EXPOSE 4200

CMD ["nginx", "-g", "daemon off;"]
