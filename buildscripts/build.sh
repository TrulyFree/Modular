#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
GH_PAGES_PROMPT="Update gh-pages? "

cd "$TARGET_DIR"

buildscripts/mvnandpush.sh

echo -n "$GH_PAGES_PROMPT"
read reply
if [[ $reply =~ ^[Yy]$ ]]
then
	buildscripts/update-ghpages.sh
fi