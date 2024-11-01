# Use the official Node.js image as a builder
FROM node:18 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the application and show output directory
RUN npm run build && ls -la /app/dist/opteamer-client/browser

# Use nginx to serve the application
FROM nginx:alpine
# Make sure to copy the correct path
COPY --from=build /app/dist/opteamer-client/browser /usr/share/nginx/html

# Expose port 80 for Nginx
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
