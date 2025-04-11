#!/bin/bash

echo "GitHub Push Helper Script"
echo "=========================="

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "Git is not installed. Please install it first."
    exit 1
fi

# Check if repository URL is provided
if [ -z "$1" ]; then
    echo "Usage: ./push-to-github.sh <your-github-repository-url>"
    echo "Example: ./push-to-github.sh https://github.com/username/healthmate-app.git"
    exit 1
fi

REPO_URL=$1

# Initialize git repository if not already initialized
if [ ! -d .git ]; then
    echo "Initializing git repository..."
    git init
fi

# Set user configuration if not already set
if [ -z "$(git config user.name)" ]; then
    echo "Setting up git user configuration..."
    echo "Enter your GitHub username:"
    read username
    git config user.name "$username"
    
    echo "Enter your GitHub email:"
    read email
    git config user.email "$email"
fi

# Add all files
echo "Adding files to git..."
git add .

# Commit changes
echo "Committing changes..."
git commit -m "Initial commit of Healthmate Android App"

# Set main branch
echo "Setting main branch..."
git branch -M main

# Add remote repository
echo "Adding remote repository..."
git remote add origin $REPO_URL

# Push to GitHub
echo "Pushing to GitHub..."
git push -u origin main

echo "=========================="
echo "Push completed! GitHub Actions should now build your APK."
echo "Check your GitHub repository's Actions tab to monitor the build process."
echo "=========================="