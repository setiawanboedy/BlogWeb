# Menggunakan base image dengan JDK
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy semua file dari proyek lokal ke dalam container
COPY . .

RUN chmod +x ./gradlew
# Membuat aplikasi Kobweb
RUN ./gradlew build

# Menjalankan aplikasi Kobweb
CMD ["java", "-jar", "site/build/libs/kobweb.jar"]