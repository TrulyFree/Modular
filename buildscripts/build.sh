#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MSG_PROMPT="Commit message? "
GH_PAGES_PROMPT="Update gh-pages? [Y/n]"
PUSH_PROMPT="Push to remote? [Y/n] "

echo -n "$MSG_PROMPT"
read msg

echo -n "$GH_PAGES_PROMPT"
read ghpages

echo -n "$PUSH_PROMPT"
read push

cd "$TARGET_DIR"

buildscripts/mvnandpush.sh "$msg" "$push"

if [[ $ghpages =~ ^[Yy]$ ]]
then
	buildscripts/update-ghpages.sh "$msg" "$push"
fi