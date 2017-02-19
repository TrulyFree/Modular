#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MSG_PROMPT="Commit message? "
GH_PAGES_PROMPT="Update gh-pages? "

echo -n "$MSG_PROMPT"
read msg

cd "$TARGET_DIR"

buildscripts/mvnandpush.sh "$msg"

echo -n "$GH_PAGES_PROMPT"
read reply
if [[ $reply =~ ^[Yy]$ ]]
then
	buildscripts/update-ghpages.sh "$msg"
fi