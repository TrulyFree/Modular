#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
SITE_DIR="site"
MSG="$1"
BRANCH="gh-pages"
PUSH_PROMPT="Push to remote? [Y/n] "

cd "$TARGET_DIR"

cp -rv target/site/* site/
mv -v site/surefire-report.html site/index.html

cd "$SITE_DIR"

git show-ref --verify --quiet refs/heads/"$BRANCH"
if [[ $? -ne 0 ]]; then
	git checkout -b "$BRANCH"
else
	git checkout "$BRANCH"
fi

git add -A
git commit -m "$MSG"

echo -n "$PUSH_PROMPT"
read reply
if [[ $reply =~ ^[Yy]$ ]]
then
	git push origin "$BRANCH"
fi