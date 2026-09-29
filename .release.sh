#!/bin/bash

# Script to update Maven project version across all modules
# Usage: ./.release.sh <new-version>

set -e

# Check if version argument is provided
if [ $# -eq 0 ]; then
    echo "Error: No version specified"
    echo "Usage: $0 <new-version>"
    echo "Example: $0 v2.5.0"
    exit 1
fi

NEW_VERSION="v$1"

update_app_version() {
    local version="$1"
    echo "Updating README.md version to: $version"
    sed -i.bak "s|<version>v[^<]*</version>|<version>$version</version>|g" README.md

    echo "Updating root pom.xml..."
    sed -i.bak "s|<revision>v[^<]*</revision>|<revision>$version</revision>|g" pom.xml
    rm -f README.md.bak
    rm -f pom.xml.bak
}

# Update root pom.xml version
echo "Updating Project Version to: $NEW_VERSION"
update_app_version "$NEW_VERSION"

echo "Version update completed successfully!"
echo "New version: $NEW_VERSION"

mvn -q -DperformRelease=true \
          -Dcentral.username="${CENTRAL_USERNAME}" \
          -Dcentral.password="${CENTRAL_TOKEN}" \
          -Dgpg.passphrase="${GPG_PASSPHRASE}" \
          dokka:javadocJar deploy

# Create release artifacts directory and collect ZIP files
echo "Collecting release artifacts..."
mkdir -p release-artifacts
find . -type f -path "*/target/*.zip" -exec cp {} release-artifacts/ \;
