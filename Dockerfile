# Menggunakan base image dengan JDK 17
FROM openjdk:17-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy seluruh proyek ke dalam container
COPY . .

RUN chmod +x ./gradlew
# Bangun proyek Kobweb
RUN ./gradlew build

# Stage kedua: menggunakan image web server sederhana untuk menyajikan hasil build
FROM node:16-slim

# Set working directory
WORKDIR /usr/src/app

# Copy file hasil build dari tahap build sebelumnya
COPY --from=build /app/build/distributions /usr/src/app

# Install http-server untuk menjalankan aplikasi web
RUN npm install -g http-server

# Set environment port, Cloud Run atau environment lain akan menyesuaikan port ini
ENV PORT 8080

# Expose port 8080
EXPOSE 8080

# Jalankan server untuk menyajikan hasil build
CMD ["http-server", "-p", "8080"]