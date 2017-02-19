#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/../"
SITE_DIR="$TARGET_DIR""site/"
MSG="$1"
PUSH="$2"
BRANCH="gh-pages"

cd "$TARGET_DIR"

rm -rv "$SITE_DIR""*"

cp -rv target/site/* "$SITE_DIR"

cd "$SITE_DIR"
mv -v surefire-report.html index.html

git show-ref --verify --quiet refs/heads/"$BRANCH"
if [[ $? -ne 0 ]]; then
	git checkout -b "$BRANCH"
else
	git checkout "$BRANCH"
fi

git add -A
git commit -m "$MSG"

if [[ $PUSH =~ ^[Yy]$ ]]
then
	git push origin "$BRANCH"
fi