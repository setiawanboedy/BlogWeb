#-----------------------------------------------------------------------------
# Create an intermediate stage that builds and exports the site.
FROM openjdk:17-jdk-slim as export

# Set up environment and copy project files
WORKDIR /project
COPY . .

# Install dependencies (e.g., Node.js for Kobweb export)
RUN apt-get update && apt-get install -y curl gnupg unzip wget nodejs

# Fetch and install Kobweb CLI
RUN wget https://github.com/varabyte/kobweb-cli/releases/download/v0.9.12/kobweb-0.9.12.zip \
    && unzip kobweb-0.9.12.zip && rm kobweb-0.9.12.zip

ENV PATH="/kobweb-0.9.12/bin:${PATH}"

# Decrease Gradle memory usage
RUN mkdir ~/.gradle && echo "org.gradle.jvmargs=-Xmx256m" >> ~/.gradle/gradle.properties

# Export the Kobweb site
WORKDIR /project/site
RUN kobweb export --notty

#-----------------------------------------------------------------------------
# Final stage: run the Kobweb server with the exported files.
FROM openjdk:17-jre-slim

# Copy the exported site from the previous stage
COPY --from=export /project/site/.kobweb .kobweb

# Expose port and run server
EXPOSE 8080
CMD ["/bin/bash", ".kobweb/server/start.sh"]
