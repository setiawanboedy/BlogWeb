#-----------------------------------------------------------------------------
# Variables shared across multiple stages
ARG KOBWEB_APP_ROOT="site"

#-----------------------------------------------------------------------------
# Create an intermediate stage that builds and exports the site.
FROM openjdk:17-jdk-slim as export

ENV KOBWEB_CLI_VERSION=0.9.12
ARG KOBWEB_APP_ROOT

# Copy the project code to an arbitrary subdir so we can install stuff in the
# Docker container root without worrying about clobbering project files.
COPY . /project

# Update and install required OS packages to continue
# We install node.js and Playwright dependencies, but these can be removed if not needed
RUN apt-get update \
    && apt-get install -y curl gnupg unzip wget \
    && curl -sL https://deb.nodesource.com/setup_19.x | bash - \
    && apt-get install -y nodejs \
    && npm init -y \
    && npx playwright install --with-deps chromium

# Fetch the latest version of the Kobweb CLI
RUN wget https://github.com/varabyte/kobweb-cli/releases/download/v${KOBWEB_CLI_VERSION}/kobweb-${KOBWEB_CLI_VERSION}.zip \
    && unzip kobweb-${KOBWEB_CLI_VERSION}.zip \
    && rm kobweb-${KOBWEB_CLI_VERSION}.zip

ENV PATH="/kobweb-${KOBWEB_CLI_VERSION}/bin:${PATH}"

WORKDIR /project/${KOBWEB_APP_ROOT}

# Decrease Gradle memory usage to avoid OOM situations
RUN mkdir ~/.gradle && \
    echo "org.gradle.jvmargs=-Xmx256m" >> ~/.gradle/gradle.properties

# Export the Kobweb project (this builds the project and prepares it for deployment)
RUN kobweb export --notty

#-----------------------------------------------------------------------------
# Create the final stage, which contains just enough bits to run the Kobweb server.
FROM openjdk:17-jre-slim as run

ARG KOBWEB_APP_ROOT

# Copy the exported project from the build stage
COPY --from=export /project/${KOBWEB_APP_ROOT}/.kobweb .kobweb

# Expose the port (adjust if necessary)
EXPOSE 8080

# Start the Kobweb server using the exported files
ENTRYPOINT ["/bin/bash", ".kobweb/server/start.sh"]
