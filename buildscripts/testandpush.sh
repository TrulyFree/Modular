#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MSG_PROMPT="Commit message? "
BRANCH_PROMPT="Branch? "

cd "$TARGET_DIR"

mvn test

git add -A

read -p "$MSG_PROMPT" msg
read -p "$BRANCH_PROMPT" branch

git commit -m "$msg"
git push origin "$branch"