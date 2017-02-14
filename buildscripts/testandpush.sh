#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MSG_PROMPT="Commit message? "
BRANCH_PROMPT="Branch? "

cd "$TARGET_DIR"

mvn test

read -p "$MSG_PROMPT" msg
read -p "$BRANCH_PROMPT" branch

git show-ref --verify --quiet refs/heads/"$branch"
if [[ $? -ne 0 ]]; then
	git checkout -b "$branch"
fi

git add -A
git commit -m "$msg"
git push origin "$branch"