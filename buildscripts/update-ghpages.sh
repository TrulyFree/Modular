#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
SITE_DIR="site"
MSG_PROMPT="Commit message? "
BRANCH="gh-pages"
PUSH_PROMPT="Push to remote? [Y/n] "

cd "$TARGET_DIR"

cp -rv target/site/* site/
mv -v site/surefire-report.html site/index.html

echo -n "$MSG_PROMPT"
read msg

cd "$SITE_DIR"

git show-ref --verify --quiet refs/heads/"$BRANCH"
if [[ $? -ne 0 ]]; then
	git checkout -b "$BRANCH"
else
	git checkout "$BRANCH"
fi

git add -A
git commit -m "$msg"

echo -n "$PUSH_PROMPT"
read reply
if [[ $reply =~ ^[Yy]$ ]]
then
	git push origin "$BRANCH"
fi